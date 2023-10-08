import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.sqrt

@Composable
fun NeonLamp(modifier: Modifier, progress: Int) {
    var screenW by remember { mutableStateOf(0f) }
    var screenY by remember { mutableStateOf(0f) }
    val gridCount = 100
    val xUnit = screenW / gridCount
    val yUnit = screenY / gridCount
    val pRadius = xUnit.coerceAtMost(yUnit) / 2
    val xList = Array(gridCount) { xUnit + it * xUnit }
    val yList = Array(gridCount) { yUnit + it * yUnit }
    val lightColorList = listOf(
        Color(0xff0055ff),
        Color(0xffFF1d1d),
        Color(0xffFFcf93),
        Color(0xffFFEBE7),
        Color(0xff39E7FF),
        Color(0xff43B988),
        Color(0xffFE6672),
        Color(0xffEB4C40),
        Color(0xffE6A639),
        Color(0xff2951ef),
        Color(0xffFFA300),
    )
    val lightColor by animateColorAsState(lightColorList[progress / 10], animationSpec = TweenSpec(1000))

    val transition = rememberInfiniteTransition()
    val circleSize by transition.animateFloat(
        40f, 30f, animationSpec = InfiniteRepeatableSpec(
            tween(1500),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(modifier = modifier.background(Color.Black)) {
        Canvas(
            modifier = modifier.blur(3.dp)
        ) {
            screenW = size.width
            screenY = size.height
            for (xIndex in xList.indices) {
                for (yIndex in yList.indices) {
                    val xdis = abs(xList[xIndex] - size.center.x)
                    val ydis = abs(yList[yIndex] - size.center.y)
                    val radius = sqrt((xdis * xdis + ydis * ydis).toDouble()).toInt()
                    val div = radius / circleSize
                    if (div <= 9) {
                        val radio = (div + 1) / 10
                        drawCircle(
                            lightColor, 2 * pRadius * (1 - radio),
                            center = Offset(xList[xIndex], yList[yIndex]),
                            alpha = 1 - radio
                        )
                    }
                }
            }
        }
        Surface(
            modifier = Modifier.width(100.dp).height(100.dp).align(Alignment.Center),
            shape = CircleShape,
            color = Color(0xff212121)
        ) {
            val content = "${progress}%"
            Row(
                modifier = modifier.padding(horizontal = 12.dp, vertical = 28.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (char in content) {
                    LedChild(modifier = Modifier.fillMaxHeight().width(18.dp), char.toString(), color = lightColor)
                }
            }
        }
    }

}

@Composable
fun LedChild(modifier: Modifier, value: String, color: Color = Color.White) {
    var screenW by remember { mutableStateOf(0f) }
    var screenY by remember { mutableStateOf(0f) }
    val gridCount = 8
    val xUnit = screenW / gridCount
    val yUnit = screenY / gridCount
    val xList = Array(gridCount) { xUnit + it * xUnit }
    val yList = Array(gridCount) { yUnit + it * yUnit }
    val numMap = mapOf(
        "1" to listOf(
            GridPoint(4, 0), GridPoint(4, 1),
            GridPoint(3, 1), GridPoint(4, 2), GridPoint(4, 3), GridPoint(4, 4), GridPoint(4, 5),
            GridPoint(4, 6), GridPoint(3, 6), GridPoint(5, 6)
        ), "2" to listOf(
            GridPoint(1, 1), GridPoint(2, 0), GridPoint(3, 0), GridPoint(4, 0),
            GridPoint(5, 1), GridPoint(5, 2), GridPoint(4, 3), GridPoint(3, 4),
            GridPoint(2, 5), GridPoint(1, 6), GridPoint(2, 6), GridPoint(3, 6),
            GridPoint(4, 6), GridPoint(5, 6)
        ),
        "3" to listOf(
            GridPoint(1, 0), GridPoint(2, 0), GridPoint(3, 0), GridPoint(4, 0), GridPoint(5, 0),
            GridPoint(4, 1), GridPoint(3, 2), GridPoint(2, 3), GridPoint(3, 3), GridPoint(4, 3),
            GridPoint(5, 4), GridPoint(5, 5), GridPoint(4, 6), GridPoint(3, 6), GridPoint(2, 6),
            GridPoint(1, 5)
        ),
        "4" to listOf(
            GridPoint(4, 0), GridPoint(4, 1), GridPoint(3, 1), GridPoint(4, 2), GridPoint(2, 2),
            GridPoint(1, 3), GridPoint(4, 3), GridPoint(1, 4), GridPoint(2, 4), GridPoint(3, 4),
            GridPoint(4, 4), GridPoint(5, 4), GridPoint(4, 4), GridPoint(4, 5), GridPoint(4, 6)
        ),
        "5" to listOf(
            GridPoint(1, 0), GridPoint(2, 0), GridPoint(3, 0), GridPoint(4, 0), GridPoint(5, 0),
            GridPoint(1, 1), GridPoint(1, 2), GridPoint(2, 2), GridPoint(3, 2), GridPoint(4, 2),
            GridPoint(5, 3), GridPoint(5, 4), GridPoint(5, 5), GridPoint(4, 6), GridPoint(3, 6),
            GridPoint(2, 6), GridPoint(1, 5)
        ),
        "6" to listOf(
            GridPoint(3, 0), GridPoint(4, 0), GridPoint(1, 2), GridPoint(2, 1), GridPoint(1, 3),
            GridPoint(2, 3), GridPoint(3, 3), GridPoint(4, 3), GridPoint(1, 4), GridPoint(5, 4),
            GridPoint(1, 5), GridPoint(5, 5), GridPoint(2, 6), GridPoint(3, 6), GridPoint(4, 6)
        ),
        "7" to listOf(
            GridPoint(1, 0), GridPoint(2, 0), GridPoint(3, 0), GridPoint(4, 0), GridPoint(5, 0),
            GridPoint(5, 1), GridPoint(4, 2), GridPoint(3, 3), GridPoint(3, 4), GridPoint(3, 5), GridPoint(3, 6)
        ),
        "8" to listOf(
            GridPoint(2, 0), GridPoint(3, 0), GridPoint(4, 0), GridPoint(2, 3), GridPoint(3, 3),
            GridPoint(4, 3), GridPoint(2, 6), GridPoint(3, 6), GridPoint(4, 6), GridPoint(1, 1),
            GridPoint(5, 1), GridPoint(1, 2), GridPoint(5, 2), GridPoint(1, 4), GridPoint(5, 4),
            GridPoint(1, 5), GridPoint(5, 5)
        ),
        "9" to listOf(
            GridPoint(2, 0), GridPoint(3, 0), GridPoint(4, 0), GridPoint(2, 3), GridPoint(3, 3),
            GridPoint(4, 3), GridPoint(2, 6), GridPoint(3, 6), GridPoint(4, 6), GridPoint(1, 1),
            GridPoint(5, 1), GridPoint(1, 2), GridPoint(5, 2), GridPoint(5, 4),
            GridPoint(1, 5), GridPoint(5, 5), GridPoint(5, 3)
        ),
        "0" to listOf(
            GridPoint(2, 0), GridPoint(3, 0), GridPoint(4, 0), GridPoint(2, 4), GridPoint(3, 3),
            GridPoint(4, 2), GridPoint(2, 6), GridPoint(3, 6), GridPoint(4, 6), GridPoint(1, 1),
            GridPoint(5, 1), GridPoint(1, 2), GridPoint(5, 2), GridPoint(1, 4), GridPoint(5, 4),
            GridPoint(1, 5), GridPoint(5, 5), GridPoint(1, 3), GridPoint(5, 3)
        ),
        "%" to listOf(
            GridPoint(5, 1), GridPoint(4, 2), GridPoint(3, 3), GridPoint(2, 4), GridPoint(1, 5),
            GridPoint(1, 2), GridPoint(1, 1), GridPoint(2, 1),
            GridPoint(5, 5), GridPoint(5, 4), GridPoint(4, 5)
        )
    )
    Canvas(
        modifier = modifier
    ) {
        screenW = size.width
        screenY = size.height
        for (point in numMap[value]!!) {
            drawRect(
                color,
                topLeft = Offset(xList[point.x], yList[point.y]),
                size = Size(xUnit * 0.8f, yUnit * 0.8f)
            )
        }
    }
}