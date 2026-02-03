package com.gorrotowi.nothinglauncher.applist

data class AppItem(
    val name: String,
    val packageName: String,
    val hasNotification: Boolean = false
)