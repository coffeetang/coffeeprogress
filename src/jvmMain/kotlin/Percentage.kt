import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import utils.pointX
import utils.pointY
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RollingArc(modifier: Modifier, progress: Int) {
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }
    val outRadius = centerX.coerceAtLeast(centerY) * 3 / 5
    val transition = rememberInfiniteTransition()
    val start by transition.animateFloat(
        0f, 360f, animationSpec = InfiniteRepeatableSpec(
            tween(1500)
        )
    )
    val sweepDif by animateFloatAsState(progress * 3.6f, animationSpec = TweenSpec(150))
    val circleColorList = listOf(
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
    val ringColor by animateColorAsState(circleColorList[progress / 10], animationSpec = TweenSpec(1000))
    Box(modifier = modifier.background(Color.Black)) {
        Canvas(modifier = modifier) {
            centerX = this.size.center.x
            centerY = this.size.center.y
            drawArc(
                brush = Brush.verticalGradient(
                    listOf(ringColor, Color.White)
                ),
                startAngle = start,
                sweepAngle = sweepDif,
                useCenter = false,
                style = Stroke(30f, cap = StrokeCap.Round),
                topLeft = Offset(centerX - outRadius, centerY - outRadius),
                size = Size(outRadius * 2, outRadius * 2)
            )
        }
        Text(
            text = "$progress%", modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                fontSize = 35.sp,
                brush = Brush.radialGradient(
                    listOf(ringColor, Color.White)
                ),
                drawStyle = Stroke(
                    width = 3f,
                    cap = StrokeCap.Square,
                )
            )
        )
    }
}

@Composable
fun RollingLine(modifier: Modifier, progress: Int) {
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }
    val maxRadius = centerX.coerceAtLeast(centerY) * 1 / 4
    val outRadius = maxRadius + 300f
    val measure = rememberTextMeasurer()
    val pathList = Array(100) {
        val path = Path()
        path.moveTo(
            pointX(maxRadius, centerX, it * 3.6f),
            pointY(maxRadius, centerY, it * 3.6f)
        )
        path.quadraticBezierTo(
            pointX(outRadius, centerX, it * 3.6f + if(it%2==0) 45f else 55f),
            pointY(outRadius, centerY, it * 3.6f + if(it%2==0) 55f else 45f),
            pointX(maxRadius, centerX, (it + 1) * 3.6f + 90f),
            pointY(maxRadius, centerY, (it + 1) * 3.6f + 90f)
        )
        path
    }
    val colorList = listOf(
        Color(0xffFF1d1d),
        Color(0xffFFcf93),
        Color(0xffFFEBE7),
        Color(0xff39E7FF),
        Color(0xff43B988),
    )
    Canvas(modifier = modifier.background(Color.Black)) {
        centerX = this.size.center.x
        centerY = this.size.center.y
        pathList.forEachIndexed { index, path ->
            if (index < progress) {
                drawPath(
                    path, brush = Brush.linearGradient(colorList),
                    style = Stroke(
                        3f,
                        cap = StrokeCap.Round,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
                    ),
                    alpha = if (progress / 100f > 1f) 1f else progress / 100f
                )
            }
        }
        drawText(
            measure,
            text = "$progress%",
            topLeft = Offset(centerX - 50, centerY - 30),
            style = TextStyle(
                fontSize = 25.sp,
                color = Color.White,
                drawStyle = Stroke(
                    width = 1f,
                    cap = StrokeCap.Round,
                )
            ),
        )
    }
}

