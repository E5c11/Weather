package com.demo.weather.common.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.demo.weather.common.resources.Dimens

@Composable
fun TitleText(
    text: String,
    color: Color = lightColorScheme().onSurface,
    modifier: Modifier = Modifier
        .padding(start = Dimens.spacingMedium, end = Dimens.spacingMedium)
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.displaySmall,
        modifier = modifier
    )
}

@Composable
fun SubtitleText(
    text: String,
    modifier: Modifier = Modifier
        .padding(Dimens.spacingMedium),
    onClick: () -> Unit = {}
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.clickable {
            onClick()
        }
    )
}

@Composable
fun BodyText(
    text: String,
    color: Color = lightColorScheme().onSurface,
    modifier: Modifier = Modifier
        .padding(
            start = Dimens.spacingMedium,
            end = Dimens.spacingMedium,
            bottom = Dimens.spacingMini
        )
        .wrapContentWidth(),
    onClick: () -> Unit = {}
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        modifier = modifier.clickable {
            onClick()
        }
    )
}