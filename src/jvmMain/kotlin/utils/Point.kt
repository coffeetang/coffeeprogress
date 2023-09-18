package utils

import kotlin.math.cos
import kotlin.math.sin

fun pointX(width: Float, centerX: Float, fl: Float): Float {
    val angle = Math.toRadians(fl.toDouble())
    return centerX - cos(angle).toFloat() * (width)
}

fun pointY(width: Float, centerY: Float, fl: Float): Float {
    val angle = Math.toRadians(fl.toDouble())
    return centerY - sin(angle).toFloat() * (width)
}