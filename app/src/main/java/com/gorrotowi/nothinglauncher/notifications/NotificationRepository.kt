package com.gorrotowi.nothinglauncher.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NotificationRepository {

    private val notificationCounts = mutableMapOf<String, Int>()
    private val _notificationCountsLiveData = MutableLiveData<Map<String, Int>>()
    val notificationCountsLiveData: LiveData<Map<String, Int>> = _notificationCountsLiveData

    fun addNotification(packageName: String) {
        synchronized(notificationCounts) {
            val currentCount = notificationCounts[packageName] ?: 0
            notificationCounts[packageName] = currentCount + 1
            notifyObservers()
        }
    }

    fun removeNotification(packageName: String) {
        synchronized(notificationCounts) {
            val currentCount = notificationCounts[packageName] ?: 0
            if (currentCount > 1) {
                notificationCounts[packageName] = currentCount - 1
            } else {
                notificationCounts.remove(packageName)
            }
            notifyObservers()
        }
    }

    fun setNotifications(packageName: String, count: Int) {
        synchronized(notificationCounts) {
            if (count > 0) {
                notificationCounts[packageName] = count
            } else {
                notificationCounts.remove(packageName)
            }
            notifyObservers()
        }
    }

    fun getCountForPackage(packageName: String): Int {
        synchronized(notificationCounts) {
            return notificationCounts[packageName] ?: 0
        }
    }

    fun hasNotification(packageName: String): Boolean {
        return getCountForPackage(packageName) > 0
    }

    fun clearAll() {
        synchronized(notificationCounts) {
            notificationCounts.clear()
            notifyObservers()
        }
    }

    fun getAllCounts(): Map<String, Int> {
        synchronized(notificationCounts) {
            return notificationCounts.toMap()
        }
    }

    private fun notifyObservers() {
        _notificationCountsLiveData.postValue(notificationCounts.toMap())
    }
}
