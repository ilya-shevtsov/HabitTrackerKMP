package org.example.project.platform

import java.awt.Desktop
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.net.URI

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

actual class PlatformActions {
    actual fun openUrl(url: String) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI(url))
        }
    }

    actual fun shareText(text: String) {
        val selection = StringSelection(text)
        Toolkit.getDefaultToolkit()
            .systemClipboard
            .setContents(selection, selection)
    }
}