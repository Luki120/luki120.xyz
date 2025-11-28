package me.luki.website.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import me.luki.website.composables.Language
import me.luki.website.composables.Project
import me.luki.website.utils.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.vh

@Composable
fun ExperienceSection() {
    Languages()
    Projects()
}

@Composable
private fun Languages() {
    Column(
        modifier = Modifier
            .id("experience")
            .classNames("section-container")
            .fillMaxSize()
            .minHeight(50.vh)
            .scrollMargin(top = 4.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SpanText(
            text = "Languages I know",
            modifier = Modifier
                .color(ColorMode.current.toSitePalette().accent)
                .fontSize(2.2.cssRem)
                .fontFamily("Quicksand")
        )
        SimpleGrid(
            numColumns(1, md = 4),
            modifier = Modifier
                .gap(0.8.cssRem)
                .padding(top = 1.2.cssRem)
        ) {
            Language(name = "Swift", skillLevel = "Proficient")
            Language(name = "Objective-C", skillLevel = "Proficient")
            Language(name = "Kotlin", skillLevel = "Novice")
            Language(name = "Ruby", skillLevel = "Novice")
        }
    }
}

@Composable
private fun Projects() {
    Column(
        modifier = Modifier
            .id("projects")
            .classNames("section-container")
            .fillMaxSize()
            .minHeight(100.vh)
            .padding(topBottom = 5.cssRem)
            .scrollMargin(top = 4.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SpanText(
            text = "Projects",
            modifier = Modifier
                .color(ColorMode.current.toSitePalette().accent)
                .fontSize(2.2.cssRem)
                .fontFamily("Quicksand")
        )
        SimpleGrid(
            numColumns(1, md = 3),
            modifier = Modifier
                .gap(0.8.cssRem)
                .padding(top = 1.cssRem)
        ) {
            Project(name = "Areesha", description = "Keep track of your favorite TV shows & movies")
            Project(name = "Azure", description = "FOSS TOTP 2FA with a clean, straightforward UI")
            Project(name = "Chrissa", description = "An actually good weather framework for jailbroken devices")
        }
    }
}
