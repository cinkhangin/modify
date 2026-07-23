package com.ckgin.modify

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorModel
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.colorspace.Illuminant
import androidx.compose.ui.graphics.colorspace.adapt
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A color in the perceptually uniform OKLCH color space.
 *
 * [lightness] and [alpha] are in `0..1`, [chroma] is non-negative, and [hue]
 * is expressed in degrees. Hue values are normalized when used or serialized.
 */
data class Oklch(
    val lightness: Float,
    val chroma: Float,
    val hue: Float,
    val alpha: Float = 1f,
) {
    init {
        require(lightness.isFinite() && lightness in 0f..1f) {
            "Lightness must be finite and in 0..1"
        }
        require(chroma.isFinite() && chroma >= 0f) {
            "Chroma must be finite and non-negative"
        }
        require(hue.isFinite()) { "Hue must be finite" }
        require(alpha.isFinite() && alpha in 0f..1f) {
            "Alpha must be finite and in 0..1"
        }
    }

    /** The hue normalized to `0..<360` degrees. */
    val normalizedHue: Float
        get() = normalizeHue(hue)

    /** Returns whether this color can be represented in sRGB without mapping. */
    fun isInSrgbGamut(): Boolean = toLinearSrgb().isInGamut()

    /**
     * Maps this color into sRGB.
     *
     * [GamutMapping.Css] uses the CSS Color 4 binary-search algorithm with local MINDE.
     */
    fun mapToSrgbGamut(gamutMapping: GamutMapping = GamutMapping.Css): Oklch =
        when (gamutMapping) {
            GamutMapping.Clip -> clipToSrgb()
            GamutMapping.ReduceChroma -> fitChromaToSrgb()
            GamutMapping.Css -> cssMapToSrgb()
        }

    /** Converts this color to a Compose sRGB [Color]. */
    fun toColor(gamutMapping: GamutMapping = GamutMapping.Css): Color {
        val rgb = if (gamutMapping == GamutMapping.Clip) {
            toLinearSrgb()
        } else {
            mapToSrgbGamut(gamutMapping).toLinearSrgb()
        }

        return Color(
            red = linearToSrgb(rgb.red).coerceIn(0f, 1f),
            green = linearToSrgb(rgb.green).coerceIn(0f, 1f),
            blue = linearToSrgb(rgb.blue).coerceIn(0f, 1f),
            alpha = alpha,
            colorSpace = ColorSpaces.Srgb,
        )
    }

    /**
     * Converts this color to a Compose [Color] in [colorSpace].
     *
     * sRGB uses CSS gamut mapping. Other destinations are converted through Compose Oklab and use
     * Compose's destination-range clipping.
     */
    fun toColor(colorSpace: ColorSpace): Color =
        when {
            colorSpace.isSrgb -> toColor()
            colorSpace == ColorSpaces.Oklab -> toOklabColor()
            else -> toOklabColor().convert(colorSpace)
        }

    /**
     * Converts this color to a Compose Oklab [Color] without reducing it to an RGB gamut.
     *
     * Keeping the color in Oklab allows Compose to convert it to the eventual display color space.
     */
    fun toOklabColor(): Color {
        val oklab = toOklab()
        require(
            oklab.a in COMPOSE_OKLAB_MIN..COMPOSE_OKLAB_MAX &&
                oklab.b in COMPOSE_OKLAB_MIN..COMPOSE_OKLAB_MAX
        ) {
            "This OKLCH color is outside Compose's storable Oklab component range."
        }
        return Color(
            red = oklab.lightness,
            green = oklab.a,
            blue = oklab.b,
            alpha = alpha,
            colorSpace = ColorSpaces.Oklab,
        )
    }

    /** Returns the Euclidean OKLab color difference, excluding alpha. */
    fun deltaE(other: Oklch): Float {
        val first = toOklab()
        val second = other.toOklab()
        val deltaLightness = first.lightness - second.lightness
        val deltaA = first.a - second.a
        val deltaB = first.b - second.b
        return sqrt(
            deltaLightness * deltaLightness +
                deltaA * deltaA +
                deltaB * deltaB
        )
    }

    /**
     * Interpolates in OKLCH, including hue direction and alpha.
     * [fraction] is clamped to `0..1`.
     */
    fun interpolate(
        other: Oklch,
        fraction: Float,
        hueInterpolation: HueInterpolation = HueInterpolation.Shorter,
    ): Oklch {
        require(fraction.isFinite()) { "Fraction must be finite" }
        val amount = fraction.coerceIn(0f, 1f)
        val firstHueMissing = chroma <= ACHROMATIC_EPSILON
        val secondHueMissing = other.chroma <= ACHROMATIC_EPSILON
        val startHue = if (firstHueMissing && !secondHueMissing) other.normalizedHue else normalizedHue
        val endHue = if (secondHueMissing && !firstHueMissing) startHue else other.normalizedHue
        val hueDelta = hueDelta(startHue, endHue, hueInterpolation)
        val interpolatedAlpha = lerp(alpha, other.alpha, amount)
        val premultipliedLightness = lerp(
            lightness * alpha,
            other.lightness * other.alpha,
            amount
        )
        val premultipliedChroma = lerp(
            chroma * alpha,
            other.chroma * other.alpha,
            amount
        )
        val interpolatedLightness =
            if (interpolatedAlpha == 0f) premultipliedLightness
            else premultipliedLightness / interpolatedAlpha
        val interpolatedChroma =
            if (interpolatedAlpha == 0f) premultipliedChroma
            else premultipliedChroma / interpolatedAlpha

        return Oklch(
            lightness = interpolatedLightness.coerceIn(0f, 1f),
            chroma = interpolatedChroma.coerceAtLeast(0f),
            hue = normalizeHue(startHue + hueDelta * amount),
            alpha = interpolatedAlpha,
        )
    }

    /** Serializes the color using CSS Color 4 `oklch()` syntax. */
    fun toCssString(precision: Int = 5): String {
        require(precision in 0..8) { "Precision must be in 0..8" }
        val lightnessText = formatNumber(lightness * 100f, precision)
        val chromaText = formatNumber(chroma, precision)
        val hueText = formatNumber(normalizedHue, precision)
        val alphaText = if (alpha < 1f) " / ${formatNumber(alpha * 100f, precision)}%" else ""
        return "oklch($lightnessText% $chromaText $hueText$alphaText)"
    }

    private fun fitChromaToSrgb(): Oklch {
        if (isInSrgbGamut() || chroma == 0f) return this

        var low = 0f
        var high = chroma
        repeat(GAMUT_SEARCH_STEPS) {
            val candidate = (low + high) / 2f
            if (copy(chroma = candidate).toLinearSrgb().isInGamut()) {
                low = candidate
            } else {
                high = candidate
            }
        }
        return copy(chroma = low)
    }

    private fun cssMapToSrgb(): Oklch {
        if (lightness >= 1f) return Oklch(1f, 0f, 0f, alpha)
        if (lightness <= 0f) return Oklch(0f, 0f, 0f, alpha)
        if (isInSrgbGamut()) return this

        var minimum = 0f
        var maximum = chroma
        var minimumIsInGamut = true
        var clipped = clipToSrgb()

        if (deltaE(clipped) < CSS_JUST_NOTICEABLE_DIFFERENCE) {
            return clipped
        }

        while (maximum - minimum > CSS_GAMUT_EPSILON) {
            val candidateChroma = (minimum + maximum) / 2f
            val candidate = copy(chroma = candidateChroma)

            if (minimumIsInGamut && candidate.isInSrgbGamut()) {
                minimum = candidateChroma
                continue
            }

            clipped = candidate.clipToSrgb()
            val difference = candidate.deltaE(clipped)
            if (difference < CSS_JUST_NOTICEABLE_DIFFERENCE) {
                if (CSS_JUST_NOTICEABLE_DIFFERENCE - difference < CSS_GAMUT_EPSILON) {
                    return clipped
                }
                minimumIsInGamut = false
                minimum = candidateChroma
            } else {
                maximum = candidateChroma
            }
        }

        return clipped
    }

    private fun clipToSrgb(): Oklch {
        val rgb = toLinearSrgb()
        return LinearRgb(
            red = rgb.red.coerceIn(0f, 1f),
            green = rgb.green.coerceIn(0f, 1f),
            blue = rgb.blue.coerceIn(0f, 1f),
        ).toOklch(alpha)
    }

    private fun toOklab(): Oklab {
        val radians = normalizedHue * (PI.toFloat() / 180f)
        return Oklab(
            lightness = lightness,
            a = chroma * cos(radians),
            b = chroma * sin(radians),
        )
    }

    private fun toLinearSrgb(): LinearRgb {
        val radians = normalizedHue * (PI.toFloat() / 180f)
        val a = chroma * cos(radians)
        val b = chroma * sin(radians)

        val lRoot = lightness + 0.39633778f * a + 0.21580376f * b
        val mRoot = lightness - 0.10556135f * a - 0.06385417f * b
        val sRoot = lightness - 0.08948418f * a - 1.2914855f * b
        val l = lRoot * lRoot * lRoot
        val m = mRoot * mRoot * mRoot
        val s = sRoot * sRoot * sRoot

        return LinearRgb(
            red = 4.0767417f * l - 3.3077116f * m + 0.23096994f * s,
            green = -1.268438f * l + 2.6097574f * m - 0.3413194f * s,
            blue = -0.0041960863f * l - 0.7034186f * m + 1.7076147f * s,
        )
    }

    companion object {
        /**
         * Parses numeric CSS Color 4 `oklch()` syntax, or returns null for invalid input.
         *
         * Numbers, percentages, angle units, and alpha are supported. CSS calculations and the
         * `none` keyword require a full CSS parser and are intentionally rejected.
         */
        fun parseOrNull(css: String): Oklch? {
            val match = OKLCH_PATTERN.matchEntire(css.trim()) ?: return null
            val components = match.groupValues[1].trim()
            val slashIndex = components.indexOf('/')
            if (slashIndex != components.lastIndexOf('/')) return null

            val colorPart = if (slashIndex >= 0) components.substring(0, slashIndex) else components
            val alphaPart = if (slashIndex >= 0) components.substring(slashIndex + 1).trim() else null
            val values = colorPart.trim().split(WHITESPACE_PATTERN)
            if (values.size != 3) return null

            val lightness = parseLightness(values[0]) ?: return null
            val chroma = parseChroma(values[1]) ?: return null
            val hue = parseHue(values[2]) ?: return null
            val alpha = if (alphaPart == null) 1f else parseAlpha(alphaPart) ?: return null

            return runCatching { Oklch(lightness, chroma, hue, alpha) }.getOrNull()
        }

        /** Parses the numeric CSS Color 4 `oklch()` subset supported by [parseOrNull]. */
        fun parse(css: String): Oklch =
            parseOrNull(css) ?: throw IllegalArgumentException("Invalid OKLCH color: $css")
    }
}

/** Strategy used when an OKLCH color lies outside sRGB. */
enum class GamutMapping {
    /** Clamp converted RGB channels independently. */
    Clip,

    /** Reduce chroma to the exact sRGB boundary while retaining lightness and hue. */
    ReduceChroma,

    /** CSS Color 4 binary-search gamut mapping with local MINDE. */
    Css,
}

/** Direction used while interpolating hue angles. */
enum class HueInterpolation {
    Shorter,
    Longer,
    Increasing,
    Decreasing,
}

/**
 * Converts any specified Compose [Color] to OKLCH using D65 reference conversions.
 *
 * RGB inputs are converted directly without an intermediate sRGB conversion, preserving
 * wide-gamut colors.
 */
fun Color.toOklch(): Oklch {
    require(this != Color.Unspecified) { "Color.Unspecified cannot be converted to OKLCH" }
    if (colorSpace == ColorSpaces.Srgb) {
        return LinearRgb(
            red = srgbToLinear(red),
            green = srgbToLinear(green),
            blue = srgbToLinear(blue),
        ).toOklch(alpha)
    }

    if (colorSpace.model == ColorModel.Rgb) {
        val d65ColorSpace = colorSpace.adapt(Illuminant.D65)
        val xyz = d65ColorSpace.toXyz(red, green, blue)
        return xyzD65ToOklab(xyz[0], xyz[1], xyz[2]).toOklch(alpha)
    }

    if (colorSpace == ColorSpaces.Oklab) {
        return Oklab(red, green, blue).toOklch(alpha)
    }

    val oklab = convert(ColorSpaces.Oklab)
    return Oklab(oklab.red, oklab.green, oklab.blue).toOklch(oklab.alpha)
}

private data class Oklab(val lightness: Float, val a: Float, val b: Float) {
    fun toOklch(alpha: Float): Oklch {
        val chroma = sqrt(a * a + b * b)
        return Oklch(
            lightness = lightness.coerceIn(0f, 1f),
            chroma = chroma.coerceAtLeast(0f),
            hue = if (chroma <= ACHROMATIC_EPSILON) {
                0f
            } else {
                normalizeHue(atan2(b, a) * 180f / PI.toFloat())
            },
            alpha = alpha.coerceIn(0f, 1f),
        )
    }
}

private fun xyzD65ToOklab(x: Float, y: Float, z: Float): Oklab {
    val lRoot = (0.818933f * x + 0.36186674f * y - 0.12885971f * z).cubeRoot()
    val mRoot = (0.032984544f * x + 0.9293119f * y + 0.03614564f * z).cubeRoot()
    val sRoot = (0.0482003f * x + 0.26436627f * y + 0.6338517f * z).cubeRoot()
    return Oklab(
        lightness = 0.21045426f * lRoot + 0.7936178f * mRoot - 0.004072047f * sRoot,
        a = 1.9779985f * lRoot - 2.4285922f * mRoot + 0.4505937f * sRoot,
        b = 0.025904037f * lRoot + 0.78277177f * mRoot - 0.80867577f * sRoot,
    )
}

private data class LinearRgb(val red: Float, val green: Float, val blue: Float) {
    fun isInGamut(): Boolean =
        red >= -GAMUT_EPSILON && red <= 1f + GAMUT_EPSILON &&
            green >= -GAMUT_EPSILON && green <= 1f + GAMUT_EPSILON &&
            blue >= -GAMUT_EPSILON && blue <= 1f + GAMUT_EPSILON

    fun toOklch(alpha: Float): Oklch {
        val l = 0.41222146f * red + 0.53633255f * green + 0.051445995f * blue
        val m = 0.2119035f * red + 0.6806995f * green + 0.10739696f * blue
        val s = 0.08830246f * red + 0.28171885f * green + 0.6299787f * blue

        val lRoot = l.cubeRoot()
        val mRoot = m.cubeRoot()
        val sRoot = s.cubeRoot()
        val lightness =
            0.21045426f * lRoot + 0.7936178f * mRoot - 0.004072047f * sRoot
        val a = 1.9779985f * lRoot - 2.4285922f * mRoot + 0.4505937f * sRoot
        val b = 0.025904037f * lRoot + 0.78277177f * mRoot - 0.80867577f * sRoot
        val chroma = sqrt(a * a + b * b)

        return Oklch(
            lightness = lightness.coerceIn(0f, 1f),
            chroma = chroma.coerceAtLeast(0f),
            hue = if (chroma <= ACHROMATIC_EPSILON) {
                0f
            } else {
                normalizeHue(atan2(b, a) * 180f / PI.toFloat())
            },
            alpha = alpha,
        )
    }
}

private fun srgbToLinear(channel: Float): Float =
    if (channel <= 0.04045f) channel / 12.92f
    else ((channel + 0.055f) / 1.055f).pow(2.4f)

private fun linearToSrgb(channel: Float): Float =
    if (channel <= 0.0031308f) 12.92f * channel
    else 1.055f * channel.pow(1f / 2.4f) - 0.055f

private fun Float.cubeRoot(): Float =
    if (this < 0f) -abs(this).pow(1f / 3f) else pow(1f / 3f)

private fun normalizeHue(value: Float): Float = ((value % 360f) + 360f) % 360f

private fun hueDelta(start: Float, end: Float, mode: HueInterpolation): Float {
    val difference = end - start
    return when (mode) {
        HueInterpolation.Shorter -> when {
            difference > 180f -> difference - 360f
            difference < -180f -> difference + 360f
            else -> difference
        }
        HueInterpolation.Longer -> when {
            difference > 0f && difference < 180f -> difference - 360f
            difference > -180f && difference <= 0f -> difference + 360f
            else -> difference
        }
        HueInterpolation.Increasing ->
            if (end < start) difference + 360f else difference
        HueInterpolation.Decreasing ->
            if (end > start) difference - 360f else difference
    }
}

private fun parseLightness(value: String): Float? {
    val number = parseNumberOrPercentage(value, percentageScale = 0.01f) ?: return null
    return number.coerceIn(0f, 1f)
}

private fun parseChroma(value: String): Float? {
    val number = parseNumberOrPercentage(value, percentageScale = 0.004f) ?: return null
    return number.coerceAtLeast(0f)
}

private fun parseAlpha(value: String): Float? {
    val number = parseNumberOrPercentage(value, percentageScale = 0.01f) ?: return null
    return number.coerceIn(0f, 1f)
}

private fun parseNumberOrPercentage(value: String, percentageScale: Float): Float? {
    val percentage = value.endsWith('%')
    val text = if (percentage) value.dropLast(1) else value
    val number = text.toFloatOrNull()?.takeIf(Float::isFinite) ?: return null
    return if (percentage) number * percentageScale else number
}

private fun parseHue(value: String): Float? {
    val lower = value.lowercase()
    val hue = when {
        lower.endsWith("grad") -> lower.dropLast(4).toFloatOrNull()?.times(0.9f)
        lower.endsWith("turn") -> lower.dropLast(4).toFloatOrNull()?.times(360f)
        lower.endsWith("rad") -> lower.dropLast(3).toFloatOrNull()?.times(180f / PI.toFloat())
        lower.endsWith("deg") -> lower.dropLast(3).toFloatOrNull()
        else -> lower.toFloatOrNull()
    }
    return hue?.takeIf(Float::isFinite)?.let(::normalizeHue)
}

private fun formatNumber(value: Float, precision: Int): String {
    if (precision == 0) return round(value).toLong().toString()
    val scale = 10f.pow(precision)
    val rounded = round(value * scale) / scale
    return rounded.toString().trimEnd('0').trimEnd('.')
}

private fun lerp(start: Float, end: Float, fraction: Float): Float = start + (end - start) * fraction

private val OKLCH_PATTERN = Regex("^oklch\\((.*)\\)$", RegexOption.IGNORE_CASE)
private val WHITESPACE_PATTERN = Regex("\\s+")
private const val ACHROMATIC_EPSILON = 0.000004f
private const val GAMUT_EPSILON = 0.00001f
private const val GAMUT_SEARCH_STEPS = 24
private const val CSS_JUST_NOTICEABLE_DIFFERENCE = 0.02f
private const val CSS_GAMUT_EPSILON = 0.0001f
private const val COMPOSE_OKLAB_MIN = -0.5f
private const val COMPOSE_OKLAB_MAX = 0.5f
