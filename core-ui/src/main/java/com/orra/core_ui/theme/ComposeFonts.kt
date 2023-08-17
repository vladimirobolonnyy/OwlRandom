package com.orra.core_ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


object Styles {

    val BodyPrimary = TextStyle(
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.32).sp
    )

    val BodySecondary = TextStyle(
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.08).sp
    )

    val TitlePrimary = TextStyle(
        fontWeight = FontWeight(600),
        fontSize = 26.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.24).sp
    )

    val TitleSecondary = TextStyle(
        fontWeight = FontWeight(600),
        fontSize = 19.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.24).sp
    )

    val LabelPrimary = TextStyle(
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.08).sp
    )

    val LabelSecondary = TextStyle(
        fontWeight = FontWeight(500),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (0.08).sp
    )
}