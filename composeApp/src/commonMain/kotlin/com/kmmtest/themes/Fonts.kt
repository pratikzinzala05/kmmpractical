package com.kmmtest.themes

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kmmpractical.composeapp.generated.resources.Res
import kmmpractical.composeapp.generated.resources.alegreya_black
import kmmpractical.composeapp.generated.resources.alegreya_bold
import kmmpractical.composeapp.generated.resources.alegreya_extra_bold
import kmmpractical.composeapp.generated.resources.alegreya_medium
import kmmpractical.composeapp.generated.resources.alegreya_regular
import kmmpractical.composeapp.generated.resources.alegreya_semi_bold
import org.jetbrains.compose.resources.Font

@Composable
fun getFontFamily(): Fonts {

    return Fonts(
        regular = FontFamily(
            Font(resource = Res.font.alegreya_regular)
        ),
        medium = FontFamily(
            Font(resource = Res.font.alegreya_medium, weight = FontWeight.Medium)
        ),
        extraBold = FontFamily(
            Font(resource = Res.font.alegreya_extra_bold)
        ),
        semiBold = FontFamily(
            Font(resource = Res.font.alegreya_semi_bold)
        ),
        bold = FontFamily(Font(resource = Res.font.alegreya_bold)),
        black = FontFamily(Font(resource = Res.font.alegreya_black)),

        )
}

data class Fonts(
    val regular: FontFamily,
    val medium: FontFamily,
    val extraBold: FontFamily,
    val semiBold: FontFamily,
    val bold: FontFamily,
    val black: FontFamily,


    )
