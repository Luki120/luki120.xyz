package me.luki.website.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import kotlinx.browser.document
import kotlinx.browser.window
import me.luki.website.composables.Footer
import me.luki.website.composables.Header
import me.luki.website.sections.ExperienceSection
import me.luki.website.sections.MainSection
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.Element
import org.w3c.dom.asList

@Page
@Composable
fun HomePage() {
    val sections = remember { document.getElementsByClassName("section-container").asList() }
    val selectedSectionId by remember { mutableStateOf("") }

    UpdateSectionId(selectedSectionId = selectedSectionId, sections = sections)

    Div {
        Header()
        MainSection()
        ExperienceSection()
        Footer()
    }
}

@Composable
private fun UpdateSectionId(selectedSectionId: String, sections: List<Element>) {
    // credits ⇝ https://github.com/ItamiOMW/ItamiMobileSite/
    @Suppress("NAME_SHADOWING")
    var selectedSectionId by remember { mutableStateOf(selectedSectionId) }

    LaunchedEffect(selectedSectionId) {
        window.history.replaceState(null, "", "#$selectedSectionId")
    }

    window.onscroll = { _ ->
        sections.forEach { section ->
            val positionInfo = section.getBoundingClientRect()
            val top = window.scrollY
            val offset = positionInfo.top + top - 250

            if (top >= offset && top < offset + positionInfo.height) selectedSectionId = section.id
        }
    }
}
