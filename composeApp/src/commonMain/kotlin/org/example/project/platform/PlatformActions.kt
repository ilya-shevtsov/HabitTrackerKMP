package org.example.project.platform

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PlatformActions {
    fun openUrl(url: String)
    fun shareText(text: String)
}