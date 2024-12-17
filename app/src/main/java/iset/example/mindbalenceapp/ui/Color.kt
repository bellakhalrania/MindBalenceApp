package iset.example.mindbalenceapp.ui

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val bg = Color(0xFFE9F2F2)
val ColorUserMessage = Color(0xFFD0BCFF)
val ColorModelMessage = Color(0xFFC1F3ED)

val gradientBrush = Brush.linearGradient(
    colors = listOf(Color(0xFFF1E0F6), Color(0xFFD0EEEB)), // Calm Purple to Calm Sky Blue
    start = androidx.compose.ui.geometry.Offset(0f, 0f),
    end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
)