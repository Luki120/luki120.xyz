package me.luki.website.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.svh
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowDown
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import me.luki.website.styles.AboutStyle
import me.luki.website.styles.DescriptionStyle
import me.luki.website.utils.toSitePalette
import org.jetbrains.compose.web.css.*
import kotlin.js.Date

val JumpKeyframe = Keyframes {
    from { Modifier.translateY(0.px) }
    to { Modifier.translateY(10.px) }
}

@Composable
fun MainSection() {
    About()
}

@Composable
private fun About() {
    val pageContext = rememberPageContext()

    Column(
        modifier = Modifier
            .id("about")
            .classNames("section-container")
            .height(100.svh)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.position(Position.Relative)) {
            SpanText(
                text = "Hi I'm Luki120",
                modifier = AboutStyle.toModifier()
                    .align(Alignment.CenterHorizontally)
                    .color(ColorMode.current.toSitePalette().accent)
                    .textAlign(TextAlign.Center)
                    .fontFamily("Quicksand")
            )
            SpanText(
                text = "iOS developer with +${getYearsOfExperience()} years of experience",
                modifier = DescriptionStyle.toModifier()
                    .align(Alignment.CenterHorizontally)
                    .color(Colors.LightGray)
                    .textAlign(TextAlign.Center)
                    .fontFamily("Barlow")
            )
        }
        Button(
            onClick = { pageContext.router.navigateTo("#experience") },
            modifier = Modifier
                .animation(
                    JumpKeyframe.toAnimation(
                        duration = 1.s,
                        iterationCount = AnimationIterationCount.Infinite,
                        timingFunction = AnimationTimingFunction.EaseOut,
                        direction = AnimationDirection.AlternateReverse
                    )
                )
                .ariaLabel("Scroll down arrow button")
                .bottom(10.percent)
                .color(ColorMode.current.toSitePalette().accent)
                .position(Position.Absolute)
        ) {
            FaArrowDown(modifier = Modifier.fontSize(2.2.cssRem))
        }
    }
}

private fun getYearsOfExperience(): Int {
    val startDate = Date(2020, 10, 1)

    val diffMilliseconds = Date.now() - startDate.getTime()
    val millisecondsInYear = 1000 * 60 * 60 * 24 * 365.25
    val diffYears = diffMilliseconds / millisecondsInYear

    return diffYears.toInt()
}
