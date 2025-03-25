package de.thro.packsimulator

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import de.thro.packsimulator.ui.SetBriefOverview
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        SetBriefOverview()
    }
}