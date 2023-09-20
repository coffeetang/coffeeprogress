package utils

import kotlin.math.cos
import kotlin.math.sin

fun pointX(radius: Float, centerX: Float, angle: Float): Float {
    val radian = Math.toRadians(angle.toDouble())
    return centerX - cos(radian).toFloat() * (radius)
}

fun pointY(radius: Float, centerY: Float, angle: Float): Float {
    val radian = Math.toRadians(angle.toDouble())
    return centerY - sin(radian).toFloat() * (radius)
}