package com.example.minichallenges.october_2025.pumpkin_splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.example.minichallenges.R
import com.example.minichallenges.october_2025.SurfaceBg
import com.example.minichallenges.october_2025.pumpkin_splash.MoveState.*

@Composable
fun PumpkinSplash(
    modifier: Modifier = Modifier
) {
    var startAnimation by remember { mutableStateOf(false) }

    // start the animation once this composable enters composition
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val time by animateFloatAsState(
        targetValue = if (startAnimation) 4.8f else 0f,
        animationSpec = tween(durationMillis = 4800),
        label = "scale"
    )

    var moveStatus by remember { mutableStateOf(NONE) }
    when (time) {
        in 0f..1.8f -> moveStatus = FIRST_TWO_COUNTER_CLOCKWISE
        in 1.8f..2f -> moveStatus = FIRST_TILT_TO_LEFT
        in 2f..2.4f -> moveStatus = FIRST_TILT_TO_RIGHT
        in 2.4f..3.2f -> moveStatus = PAUSE
        in 3.2f..3.6f -> moveStatus = SECOND_TILT_TO_LEFT
        in 3.6f..4f -> moveStatus = SECOND_TILT_TO_RIGHT
        in 4f..4.2f -> moveStatus = DISAPPEAR
    }

    val scale = when (moveStatus) {
        FIRST_TWO_COUNTER_CLOCKWISE -> 0.5f + (time / 1.8f) * 0.5f
        FIRST_TILT_TO_LEFT -> mapRange(time, 1.8f, 2f, 1.0f, 1.1f)
        FIRST_TILT_TO_RIGHT -> mapRange(time, 2f, 2.4f, 1.1f, 1.0f)
        PAUSE -> 1.0f
        SECOND_TILT_TO_LEFT -> mapRange(time, 3.2f, 3.6f, 1.0f, 1.1f)
        SECOND_TILT_TO_RIGHT -> mapRange(time, 3.6f, 4f, 1.1f, 1.0f)
        DISAPPEAR -> mapRange(time, 4f, 4.2f, 1.0f, 0f)
        else -> 1.0f
    }

    val rotation = when (moveStatus) {
        FIRST_TWO_COUNTER_CLOCKWISE -> 720f - ((scale - 0.5f) / 0.5f) * 720f
        FIRST_TILT_TO_LEFT -> mapRange(time, 1.8f, 2f, 0f, -30f)
        FIRST_TILT_TO_RIGHT -> tiltAngle(time)
        PAUSE -> 0f
        SECOND_TILT_TO_LEFT -> mapRange(time, 3.2f, 3.6f, 0f, -30f)
        SECOND_TILT_TO_RIGHT -> mapRange(time, 3.6f, 4f, -30f, 30f)
        else -> 0f
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().background(SurfaceBg)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pumpkin),
            contentDescription = "Pumpkin",
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,

                    rotationZ = rotation
                )
        )
    }
}

enum class MoveState {
    NONE,
    FIRST_TWO_COUNTER_CLOCKWISE,//1.8s
    FIRST_TILT_TO_LEFT,//0.2
    FIRST_TILT_TO_RIGHT,//0.4
    PAUSE,//0.8
    SECOND_TILT_TO_LEFT,//0.4
    SECOND_TILT_TO_RIGHT,//0.4
    DISAPPEAR//0.8
}

fun mapRange(
    value: Float,
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float
): Float {
    val t = ((value - inMin) / (inMax - inMin)).coerceIn(0f, 1f)
    return outMin + t * (outMax - outMin)
}

fun tiltAngle(progress: Float): Float = when {
    progress < 2f -> 0f
    progress <= 2.4f -> {
        // Map 2f–2.2f to -20f–20f (linear)
        val t = (progress - 2f) / 0.4f
        -30f + t * 60f
    }
    progress <= 2.8f -> {
        // Map 2.4f–2.8f to 20f–0f (return to neutral)
        val t = (progress - 2.4f) / 0.4f
        30f - t * 30f
    }
    else -> 0f
}