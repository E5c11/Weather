package com.demo.weather.common.resources

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import com.demo.weather.R

val sourceSansPro = FontFamily(
    Font(R.font.source_sans_pro_black, FontWeight.Black),
    Font(R.font.source_sans_pro_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.source_sans_pro_bold, FontWeight.Bold),
    Font(R.font.source_sans_pro_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.source_sans_pro_extra_light, FontWeight.ExtraLight),
    Font(R.font.source_sans_pro_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.source_sans_pro_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.source_sans_pro_regular, FontWeight.Normal),
    Font(R.font.source_sans_pro_semi_bold, FontWeight.SemiBold),
    Font(R.font.source_sans_pro_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic)
)