package com.ckgin.modify

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class OklchTest {

    @Test
    fun convertsSrgbPrimariesToReferenceValues() {
        assertOklch(
            actual = Color.Red.toOklch(),
            lightness = 0.62796f,
            chroma = 0.25768f,
            hue = 29.234f,
        )
        assertOklch(
            actual = Color.Green.toOklch(),
            lightness = 0.86644f,
            chroma = 0.29483f,
            hue = 142.495f,
        )
        assertOklch(
            actual = Color.Blue.toOklch(),
            lightness = 0.45201f,
            chroma = 0.31321f,
            hue = 264.052f,
        )
    }

    @Test
    fun roundTripsSrgbColor() {
        val original = Color(0xFF3366CC)
        val roundTrip = original.toOklch().toColor(GamutMapping.Clip)

        assertEquals(original.red, roundTrip.red, RGB_TOLERANCE)
        assertEquals(original.green, roundTrip.green, RGB_TOLERANCE)
        assertEquals(original.blue, roundTrip.blue, RGB_TOLERANCE)
        assertEquals(original.alpha, roundTrip.alpha, RGB_TOLERANCE)
    }

    @Test
    fun roundTripsSrgbGrid() {
        val components = listOf(0f, 0.25f, 0.5f, 0.75f, 1f)

        for (red in components) {
            for (green in components) {
                for (blue in components) {
                    val original = Color(red, green, blue)
                    val roundTrip = original.toOklch().toColor(GamutMapping.Clip)
                    assertEquals(original.red, roundTrip.red, RGB_TOLERANCE)
                    assertEquals(original.green, roundTrip.green, RGB_TOLERANCE)
                    assertEquals(original.blue, roundTrip.blue, RGB_TOLERANCE)
                }
            }
        }
    }

    @Test
    fun preservesDisplayP3ColorThroughOklab() {
        val displayP3Red = Color(
            red = 1f,
            green = 0f,
            blue = 0f,
            alpha = 1f,
            colorSpace = ColorSpaces.DisplayP3,
        )

        val converted = displayP3Red.toOklch()
        val roundTrip = converted.toColor(ColorSpaces.DisplayP3)

        assertTrue(converted.chroma > Color.Red.toOklch().chroma + 0.02f)
        assertEquals(displayP3Red.red, roundTrip.red, RGB_TOLERANCE)
        assertEquals(displayP3Red.green, roundTrip.green, RGB_TOLERANCE)
        assertEquals(displayP3Red.blue, roundTrip.blue, RGB_TOLERANCE)
    }

    @Test
    fun parsesNumbersPercentagesAnglesAndClampsCssRanges() {
        val parsed = Oklch.parse("oklch(42.1% 48.25% -0.25turn / 120%)")

        assertEquals(0.421f, parsed.lightness, COMPONENT_TOLERANCE)
        assertEquals(0.193f, parsed.chroma, COMPONENT_TOLERANCE)
        assertEquals(270f, parsed.normalizedHue, COMPONENT_TOLERANCE)
        assertEquals(1f, parsed.alpha, COMPONENT_TOLERANCE)

        val clamped = Oklch.parse("oklch(2 -0.1 400grad / -10%)")
        assertEquals(1f, clamped.lightness, COMPONENT_TOLERANCE)
        assertEquals(0f, clamped.chroma, COMPONENT_TOLERANCE)
        assertEquals(0f, clamped.normalizedHue, COMPONENT_TOLERANCE)
        assertEquals(0f, clamped.alpha, COMPONENT_TOLERANCE)
    }

    @Test
    fun rejectsUnsupportedOrInvalidCss() {
        assertEquals(null, Oklch.parseOrNull("oklch(none 0 none)"))
        assertEquals(null, Oklch.parseOrNull("oklch(50%, 0.2, 20)"))
        assertEquals(null, Oklch.parseOrNull("oklch(50% 0.2 20 / NaN)"))
    }

    @Test
    fun cssStringRoundTrips() {
        val original = Oklch(0.5385f, 0.1725f, 320.67f, 0.7f)
        val parsed = Oklch.parse(original.toCssString(precision = 5))

        assertEquals(original.lightness, parsed.lightness, COMPONENT_TOLERANCE)
        assertEquals(original.chroma, parsed.chroma, COMPONENT_TOLERANCE)
        assertEquals(original.normalizedHue, parsed.normalizedHue, COMPONENT_TOLERANCE)
        assertEquals(original.alpha, parsed.alpha, COMPONENT_TOLERANCE)
    }

    @Test
    fun achromaticEndpointBorrowsChromaticHue() {
        val gray = Oklch(0.5f, 0f, 0f)
        val blue = Oklch(0.7f, 0.2f, 240f)

        val midpoint = gray.interpolate(blue, 0.5f)

        assertEquals(240f, midpoint.normalizedHue, COMPONENT_TOLERANCE)
    }

    @Test
    fun interpolationUsesPremultipliedAlpha() {
        val opaque = Oklch(0.8f, 0.2f, 40f, alpha = 1f)
        val transparent = Oklch(0.2f, 0.1f, 40f, alpha = 0f)

        val midpoint = opaque.interpolate(transparent, 0.5f)

        assertEquals(0.8f, midpoint.lightness, COMPONENT_TOLERANCE)
        assertEquals(0.2f, midpoint.chroma, COMPONENT_TOLERANCE)
        assertEquals(0.5f, midpoint.alpha, COMPONENT_TOLERANCE)
    }

    @Test
    fun hueInterpolationMatchesCssDirectionEdgeCases() {
        val start = Oklch(0.6f, 0.2f, 180f)
        val end = Oklch(0.6f, 0.2f, 0f)
        assertEquals(
            90f,
            start.interpolate(end, 0.5f, HueInterpolation.Shorter).normalizedHue,
            COMPONENT_TOLERANCE,
        )

        val sameHue = Oklch(0.6f, 0.2f, 30f)
        assertEquals(
            210f,
            sameHue.interpolate(sameHue, 0.5f, HueInterpolation.Longer).normalizedHue,
            COMPONENT_TOLERANCE,
        )
    }

    @Test
    fun gamutMappingProducesSrgbColor() {
        val outOfGamut = Oklch(0.7f, 0.4f, 30f)
        assertFalse(outOfGamut.isInSrgbGamut())

        assertTrue(outOfGamut.mapToSrgbGamut(GamutMapping.ReduceChroma).isInSrgbGamut())
        assertTrue(outOfGamut.mapToSrgbGamut(GamutMapping.Css).isInSrgbGamut())
    }

    @Test
    fun cssGamutMappingHandlesLightnessAndHueGrid() {
        for (lightness in listOf(0.05f, 0.25f, 0.5f, 0.75f, 0.95f)) {
            for (hue in 0 until 360 step 15) {
                val mapped = Oklch(lightness, 0.5f, hue.toFloat())
                    .mapToSrgbGamut(GamutMapping.Css)
                assertTrue("L=$lightness H=$hue mapped outside sRGB", mapped.isInSrgbGamut())
            }
        }
    }

    @Test
    fun rejectsNonFiniteInterpolationFraction() {
        assertThrows(IllegalArgumentException::class.java) {
            Oklch(0.5f, 0.1f, 20f).interpolate(
                Oklch(0.6f, 0.1f, 30f),
                Float.NaN,
            )
        }
    }

    private fun assertOklch(
        actual: Oklch,
        lightness: Float,
        chroma: Float,
        hue: Float,
    ) {
        assertEquals(lightness, actual.lightness, REFERENCE_TOLERANCE)
        assertEquals(chroma, actual.chroma, REFERENCE_TOLERANCE)
        assertEquals(hue, actual.normalizedHue, HUE_TOLERANCE)
    }

    private companion object {
        const val COMPONENT_TOLERANCE = 0.00001f
        const val REFERENCE_TOLERANCE = 0.0002f
        const val HUE_TOLERANCE = 0.02f
        const val RGB_TOLERANCE = 0.002f
    }
}
