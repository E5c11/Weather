package com.demo.weather.common.resources

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val customTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.Black,
        fontSize = Dimens.textSizeMega
    ),
    displayMedium = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.Bold,
        fontSize = Dimens.textSizeXXLarge
    ),
    displaySmall = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.SemiBold,
        fontSize = Dimens.textSizeLarge
    ),
    headlineLarge = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.textSizeLarge
    ),
    headlineMedium = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.Light,
        fontSize = Dimens.textSizeMLarge
    ),
    headlineSmall = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.ExtraLight,
        fontSize = Dimens.textSizeMedium
    ),
    bodyLarge = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.textSizeMedium
    ),
    bodyMedium = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.Light,
        fontSize = Dimens.textSizeSmall
    ),
    bodySmall = TextStyle(
        fontFamily = sourceSansPro,
        fontWeight = FontWeight.ExtraLight,
        fontSize = Dimens.textSizeMini
    )
)