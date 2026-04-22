package org.example.project.platform


import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformActions {

    actual fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication.openURL(
            nsUrl,
            options = emptyMap<Any?, Any>(),
            completionHandler = null
        )
    }

    actual fun shareText(text: String) {
        val rootViewController = findTopViewController() ?: return

        val activityController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )

        rootViewController.presentViewController(
            viewControllerToPresent = activityController,
            animated = true,
            completion = null
        )
    }
}
private fun findTopViewController(): UIViewController? {
    val root = UIApplication.sharedApplication
        .windows
        .firstOrNull { (it as? UIWindow)?.isKeyWindow() == true }
        ?.let { it as UIWindow }
        ?.rootViewController
        ?: return null

    return topMost(root)
}

private tailrec fun topMost(viewController: UIViewController): UIViewController {
    val presented = viewController.presentedViewController
    return if (presented != null) topMost(presented) else viewController
}