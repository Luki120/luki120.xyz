package me.luki.website.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.css.CSSLengthOrPercentageNumericValue
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.LinkStyle
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.style.until
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import me.luki.website.styles.SocialButtonStyle
import me.luki.website.utils.Constants
import me.luki.website.utils.CustomColors
import me.luki.website.utils.Images
import me.luki.website.utils.toSitePalette
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.filter
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import kotlin.js.Date

val FooterLinkVariant = LinkStyle.addVariant {
    base {
        Modifier.color(CustomColors.Purple)
    }

    until(Breakpoint.MD) {
        Modifier.textDecorationLine(TextDecorationLine.None)
    }
}

@Composable
fun Footer() {
    val currentYear = remember { Date().getFullYear() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(ColorMode.current.toSitePalette().nearBackground)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.px),
            modifier = Modifier
                .fillMaxWidth()
                .padding(topBottom = 15.px)
        ) {
            Span {
                FooterText(text = "This website is ")
                Link(path = Constants.WEBSITE_URL, text = "open source", variant = FooterLinkVariant)
                FooterText(text = ", written using ")
                Link(path = Constants.KOBWEB_URL, text = "Kobweb", variant = FooterLinkVariant)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                SocialButton(image = Images.GITHUB, description = "GitHub link", url = Constants.GITHUB_URL)
                SocialButton(image = Images.TWITTER, description = "Twitter link", url = Constants.TWITTER_URL)
            }
            FooterText(text = "© $currentYear Luki120", fontSize = 0.75.cssRem)
        }
    }
}

@Composable
private fun FooterText(text: String, fontSize: CSSLengthOrPercentageNumericValue = 1.cssRem) {
    SpanText(
        text = text,
        modifier = Modifier
            .color(Colors.Gray)
            .fontFamily("Barlow")
            .fontSize(fontSize)
    )
}

@OptIn(ExperimentalComposeWebApi::class)
@Composable
private fun SocialButton(image: String, description: String, url: String) {
    val isLight = when (ColorMode.current) {
        ColorMode.DARK -> false
        ColorMode.LIGHT -> true
    }
    val context = rememberPageContext()

    Button(
        onClick = { context.router.navigateTo(url) },
        modifier = SocialButtonStyle.toModifier()
    ) {
        Image(
            src = image,
            description = description,
            modifier = Modifier
                .styleModifier {
                    filter { if (isLight) invert(1) else invert(0) }
                }
        )
    }
}
