package com.gorrotowi.nothinglauncher.notifications

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationService : NotificationListenerService() {

    private lateinit var notificationFilter: NotificationFilter

    override fun onCreate() {
        super.onCreate()
        notificationFilter = NotificationFilter(packageManager)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        refreshNotificationCounts()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn ?: return
        if (notificationFilter.shouldShowDot(sbn)) {
            NotificationRepository.addNotification(sbn.packageName)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        sbn ?: return
        if (notificationFilter.shouldShowDot(sbn)) {
            NotificationRepository.removeNotification(sbn.packageName)
        }
    }

    private fun refreshNotificationCounts() {
        NotificationRepository.clearAll()

        val activeNotifications = try {
            activeNotifications ?: emptyArray()
        } catch (e: Exception) {
            emptyArray()
        }

        val countsByPackage = activeNotifications
            .filter { notificationFilter.shouldShowDot(it) }
            .groupBy { it.packageName }
            .mapValues { it.value.size }

        countsByPackage.forEach { (packageName, count) ->
            NotificationRepository.setNotifications(packageName, count)
        }
    }
}
