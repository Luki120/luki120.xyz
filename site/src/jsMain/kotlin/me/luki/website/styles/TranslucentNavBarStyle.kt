package me.luki.website.styles

import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

val TranslucentNavBarStyle = CssStyle.base {
    Modifier
        .backdropFilter(blur(5.px))
        .fillMaxWidth()
        .padding(15.px)
        .position(Position.Fixed)
        .top(0.percent)
}
