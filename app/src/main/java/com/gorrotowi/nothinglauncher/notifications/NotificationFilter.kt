package com.gorrotowi.nothinglauncher.notifications

import android.app.Notification
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.service.notification.StatusBarNotification

class NotificationFilter(private val packageManager: PackageManager) {

    companion object {
        private val SYSTEM_PACKAGE_PREFIXES = listOf(
            "com.android.",
            "com.google.android.gms",
            "com.google.android.gsf",
            "com.google.android.providers.",
            "android",
            "com.samsung.android.providers.",
            "com.huawei.android.providers.",
            "com.xiaomi.providers.",
            "com.oppo.providers.",
            "com.vivo.providers."
        )

        private val SYSTEM_PACKAGES = setOf(
            "com.google.android.gms",
            "com.google.android.gsf",
            "com.google.android.packageinstaller",
            "com.android.systemui",
            "com.android.settings",
            "com.android.phone",
            "com.android.server.telecom",
            "com.android.providers.downloads",
            "com.android.vending",
            "com.android.bluetooth",
            "com.android.nfc",
            "com.android.shell",
            "com.android.launcher",
            "com.android.launcher3",
            "com.android.inputmethod.latin"
        )

        private val SYSTEM_CATEGORIES = setOf(
            Notification.CATEGORY_SYSTEM,
            Notification.CATEGORY_SERVICE,
            Notification.CATEGORY_STATUS,
            Notification.CATEGORY_PROGRESS,
            Notification.CATEGORY_TRANSPORT
        )
    }

    fun shouldShowDot(sbn: StatusBarNotification): Boolean {
        val packageName = sbn.packageName
        val notification = sbn.notification

        // Layer 1: Package-based filtering
        if (isSystemPackage(packageName)) return false

        // Layer 2: ApplicationInfo flags
        if (isSystemAppWithoutLauncher(packageName)) return false

        // Layer 3: Notification category
        if (isSystemCategory(notification)) return false

        // Layer 4: Notification flags
        if (hasSystemFlags(notification)) return false

        // Layer 5: Priority check
        if (isLowPriority(notification)) return false

        // Layer 6: Ongoing non-clearable check
        if (sbn.isOngoing && !sbn.isClearable) return false

        return true
    }

    private fun isSystemPackage(packageName: String): Boolean {
        if (SYSTEM_PACKAGES.contains(packageName)) return true
        return SYSTEM_PACKAGE_PREFIXES.any { prefix ->
            packageName.startsWith(prefix)
        }
    }

    private fun isSystemAppWithoutLauncher(packageName: String): Boolean {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            val isSystem = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
            val isUpdatedSystem = (appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

            if (!isSystem && !isUpdatedSystem) {
                return false
            }

            // If it's a system app, check if it has a launcher activity (user-facing)
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            launchIntent == null
        } catch (e: PackageManager.NameNotFoundException) {
            true
        }
    }

    private fun isSystemCategory(notification: Notification): Boolean {
        val category = notification.category ?: return false
        return category in SYSTEM_CATEGORIES
    }

    private fun hasSystemFlags(notification: Notification): Boolean {
        val flags = notification.flags

        // Foreground service notifications
        if ((flags and Notification.FLAG_FOREGROUND_SERVICE) != 0) return true

        // Ongoing notifications that cannot be cleared
        if ((flags and Notification.FLAG_ONGOING_EVENT) != 0 &&
            (flags and Notification.FLAG_NO_CLEAR) != 0) return true

        return false
    }

    @Suppress("DEPRECATION")
    private fun isLowPriority(notification: Notification): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API 26+, priority is deprecated but still accessible
            notification.priority <= Notification.PRIORITY_LOW
        } else {
            notification.priority <= Notification.PRIORITY_LOW
        }
    }
}
