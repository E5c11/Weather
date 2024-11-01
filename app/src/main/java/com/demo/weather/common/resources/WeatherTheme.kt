package com.demo.weather.common.resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue01 = Color(0xFF154F9E)
val Blue02 = Color(0xFF7BB9E7)
val Sunrise = Color(0xFFE16B14)
val ErrorRed = Color(0xFFFF1D25)
val CardBg = Color(0x54000000)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val TextDisabled = Color(0xFFCED0D1)
val EditTextField = Color(0xFF6D6E6F)
val TextBody = Color(0xFF272425)
val TextSubHeader = Color(0xFF154F9E)
val TextDisabledDark = Color(0x4DFFFFFF)
val EditTextFieldDark = Color(0xB3FFFFFF)
val Blue03 = Color(0x55154F9E)

private val LightColorScheme = lightColorScheme(
    primary = Blue01,
    secondary = Blue02,
    secondaryContainer = Sunrise,
    onSecondary = Black,
    error = ErrorRed,
    background = White,
    surface = CardBg,
    onBackground = TextBody,
    onSurface = TextBody
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue01,
    secondary = Blue02,
    secondaryContainer = Sunrise,
    onSecondary = White,
    error = ErrorRed,
    background = Black,
    surface = CardBg,
    onBackground = White,
    onSurface = TextDisabledDark
)

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = customTypography,
        content = content
    )
}