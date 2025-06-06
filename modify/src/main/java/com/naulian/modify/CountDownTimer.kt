package com.naulian.modify

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@ExperimentalModifyApi
class CountDownTimer(
    private val durationMillis: Long,
    private val format: (Long) -> String = { "$it" },
    private val onTick: (Long) -> Unit = {},
    private val onFinish: () -> Unit = {},
    private val intervalMillis: Long = 1000L,
) {

    private var countDownJob: Job? = null
    private var _startMillis = 0L
    val startMillis get() = _startMillis
    private var actualDurationMillis = durationMillis

    //use this if you need the time left in millis
    private val _millis = MutableStateFlow(durationMillis)
    val millis get() = _millis.asStateFlow()

    //use this if you need the time formatted in onTick
    private var _formattedValue by mutableStateOf("")
    val formattedValue get() = _formattedValue

    fun restoreStartMillis(millis: Long) {
        _startMillis = millis
    }

    suspend fun start() {
        coroutineScope { countDownJob = run() }
    }

    private fun CoroutineScope.run() = launch {
        if (startMillis == 0L) {
            _startMillis = SystemClock.elapsedRealtime()
        }

        var millisLeft = actualDurationMillis
        while (millisLeft > 1000L) {
            val currentMillis = SystemClock.elapsedRealtime()
            val passedMillis = currentMillis - startMillis
            millisLeft = (actualDurationMillis - passedMillis).coerceAtLeast(0L)

            //update
            onTick(millisLeft)
            _millis.value = millisLeft
            _formattedValue = format(millisLeft)

            val nextTick = intervalMillis - (currentMillis % intervalMillis)
            delay(nextTick)
        }
        _startMillis = 0L
        onFinish()
    }

    fun pause() {
        val currentMillis = SystemClock.elapsedRealtime()
        val passedMillis = currentMillis - startMillis
        actualDurationMillis = durationMillis - passedMillis
        cancel()
    }

    suspend fun resume() {
        _startMillis = 0L
        start()
    }


    fun cancel() {
        countDownJob?.cancel()
        actualDurationMillis = durationMillis
        _startMillis = 0L
    }

    suspend fun restart() {
        cancel()
        start()
    }
}

@OptIn(ExperimentalModifyApi::class)
private fun countdownTimerSaver(
    totalMillis: Long,
    format: (Long) -> String,
    onTick: (Long) -> Unit,
    onFinish: () -> Unit,
    intervalMillis: Long = 1000L,
): Saver<CountDownTimer, Long> = Saver(
    save = { state -> state.startMillis },
    restore = { startMillis ->
        CountDownTimer(
            durationMillis = totalMillis,
            onTick = onTick,
            format = format,
            onFinish = onFinish,
            intervalMillis = intervalMillis
        ).also { it.restoreStartMillis(startMillis) }
    }
)

@OptIn(ExperimentalModifyApi::class)
@Composable
fun rememberCountdownTimer(
    totalTimeMillis: Long,
    format: (Long) -> String = { "$it" },
    onTick: (Long) -> Unit = {},
    onFinish: () -> Unit = {},
    intervalMillis: Long = 1000L,
) = rememberSaveable(
    saver = countdownTimerSaver(
        totalMillis = totalTimeMillis,
        onTick = onTick,
        format = format,
        onFinish = onFinish,
        intervalMillis = intervalMillis
    )
) {
    CountDownTimer(
        durationMillis = totalTimeMillis,
        onTick = onTick,
        format = format,
        onFinish = onFinish,
        intervalMillis = intervalMillis
    )
}