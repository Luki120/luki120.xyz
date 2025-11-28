package me.luki.website.composables

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.LinkStyle
import com.varabyte.kobweb.silk.components.overlay.*
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.defer.Deferred
import com.varabyte.kobweb.silk.style.addVariant
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.style.until
import com.varabyte.kobweb.silk.style.vars.color.ColorVar
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import com.varabyte.kobweb.silk.theme.colors.systemPreference
import kotlinx.browser.localStorage
import me.luki.website.core.COLOR_MODE_KEY
import me.luki.website.styles.TranslucentNavBarStyle
import me.luki.website.utils.CustomColors
import me.luki.website.utils.toSitePalette
import org.jetbrains.compose.web.css.*

val SideMenuSlideInAnimation = Keyframes {
    from { Modifier.translateX(100.percent) }
    to { Modifier }
}

private enum class SideMenuState {
    CLOSED,
    OPEN,
    CLOSING;

    fun close() = when (this) {
        CLOSED -> CLOSED
        OPEN -> CLOSING
        CLOSING -> CLOSING
    }
}

val NavigationBarLinkVariant = LinkStyle.addVariant {
    base {
        Modifier.textDecorationLine(TextDecorationLine.None)
    }

    until(Breakpoint.MD) {
        Modifier.color(ColorVar.value())
    }

    Breakpoint.MD {
        Modifier
            .border(
                color = CustomColors.Purple,
                style = LineStyle.Solid,
                width = 2.px
            )
            .borderRadius(20.px)
            .color(CustomColors.Purple)
            .padding(topBottom = 10.px, leftRight = 20.px)
    }
}

@Composable
fun Header() {
    Deferred {
        Row(
            TranslucentNavBarStyle.toModifier()
                .displayIfAtLeast(Breakpoint.MD)
                .gap(0.6.cssRem),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuItems()
            ColorModeButton()
        }
        Row(
            TranslucentNavBarStyle.toModifier()
                .displayUntil(Breakpoint.MD)
                .fontSize(0.8.cssRem)
                .gap(0.2.cssRem),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var menuState by remember { mutableStateOf(SideMenuState.CLOSED) }

            ColorModeButton()
            Button(
                modifier = Modifier.ariaLabel("Hamburger menu button"),
                onClick = { menuState = SideMenuState.OPEN }
            ) {
                HamburgerIcon()
            }

            if (menuState != SideMenuState.CLOSED) {
                SideMenu(
                    menuState,
                    close = { menuState = menuState.close() },
                    onAnimationEnd = {
                        if (menuState == SideMenuState.CLOSING) menuState = SideMenuState.CLOSED
                    }
                )
            }
        }
    }
}

@Composable
private fun SideMenu(menuState: SideMenuState, close: () -> Unit, onAnimationEnd: () -> Unit) {
    Overlay(
        Modifier
            .setVariable(OverlayVars.BackgroundColor, Colors.Transparent)
            .onClick { close() }
    ) {
        key(menuState) {
            Column(
                Modifier
                    .align(Alignment.CenterEnd)
                    .animation(
                        SideMenuSlideInAnimation.toAnimation(
                            duration = 200.ms,
                            timingFunction = if (menuState == SideMenuState.OPEN) AnimationTimingFunction.EaseOut else AnimationTimingFunction.EaseIn,
                            direction = if (menuState == SideMenuState.OPEN) AnimationDirection.Normal else AnimationDirection.Reverse,
                            fillMode = AnimationFillMode.Forwards
                        )
                    )
                    .backgroundColor(ColorMode.current.toSitePalette().nearBackground)
                    .fillMaxHeight()
                    .gap(1.5.cssRem)
                    .width(clamp(8.cssRem, 33.percent, 10.cssRem))
                    .padding(top = 1.cssRem, leftRight = 1.cssRem)
                    .onClick { it.stopPropagation() }
                    .onAnimationEnd { onAnimationEnd() },
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    modifier = Modifier.ariaLabel("Close hamburger menu button"),
                    onClick = { close() }
                ) {
                    CloseIcon()
                }
                Column(
                    Modifier
                        .gap(1.5.cssRem)
                        .fontSize(1.2.cssRem)
                        .textAlign(TextAlign.Center),
                    horizontalAlignment = Alignment.End
                ) {
                    MenuItems(onLinkClick = { close() })
                }
            }
        }
    }
}

@Composable
private fun MenuItems(onLinkClick: () -> Unit = {}) {
    MenuLink(path = "#experience", text = "Experience", onClick = onLinkClick)
    MenuLink(path = "#projects", text = "Projects", onClick = onLinkClick)
}

@Composable
private fun MenuLink(path: String, text: String, onClick: () -> Unit) {
    Link(
        path = path,
        text = text,
        modifier = Modifier.onClick { onClick() },
        variant = NavigationBarLinkVariant
    )
}

@Composable
private fun ColorModeButton() {
    var colorMode by ColorMode.currentState
    var shouldAnimate by remember { mutableStateOf(false) }
    val keepOpenStrategy = remember { KeepPopupOpenStrategy.manual() }
    val openCloseStrategy = remember { OpenClosePopupStrategy.manual() }

    fun closePopover() {
        keepOpenStrategy.shouldKeepOpen = false
        openCloseStrategy.isOpen = false
    }

    Button(
        modifier = Modifier.ariaLabel("Color mode button"),
        onClick = {
            shouldAnimate = true
            if (openCloseStrategy.isOpen) closePopover()
            else openCloseStrategy.isOpen = true
        }
    ) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    AdvancedPopover(
        target = ElementTarget.PreviousSibling,
        modifier = Modifier.transition(Transition.of("opacity", duration = 300.ms)),
        hiddenModifier = Modifier.thenIf(!shouldAnimate, Modifier.display(DisplayStyle.None)),
        keepOpenStrategy = keepOpenStrategy,
        openCloseStrategy = openCloseStrategy,
        placementStrategy = PopupPlacementStrategy.of(PopupPlacement.BottomRight, offsetPixels = 10)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .backgroundColor(ColorMode.current.toSitePalette().nearBackground)
                .onMouseLeave { closePopover() }
        ) {
            DropdownContentButton(
                text = "Dark",
                onClick = {
                    colorMode = ColorMode.DARK
                    colorMode.saveToLocalStorage(key = COLOR_MODE_KEY.name)
                    closePopover()
                    shouldAnimate = false
                }
            )
            DropdownContentButton(
                text = "Light",
                onClick = {
                    colorMode = ColorMode.LIGHT
                    colorMode.saveToLocalStorage(key = COLOR_MODE_KEY.name)
                    closePopover()
                    shouldAnimate = false
                }
            )
            DropdownContentButton(
                text = "System",
                onClick = {
                    colorMode = ColorMode.systemPreference
                    localStorage.removeItem(key = COLOR_MODE_KEY.name)
                    closePopover()
                    shouldAnimate = false
                }
            )
        }
    }
}

@Composable
private fun DropdownContentButton(text: String, onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        SpanText(
            text = text,
            modifier = Modifier
                .fontFamily("Barlow")
                .fontWeight(FontWeight.Normal)
        )
    }
}
