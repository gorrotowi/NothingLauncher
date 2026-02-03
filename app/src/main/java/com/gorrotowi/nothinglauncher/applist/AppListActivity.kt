package com.gorrotowi.nothinglauncher.applist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorrotowi.nothinglauncher.NothingBaseActivity
import com.gorrotowi.nothinglauncher.R
import java.util.Locale

class AppListActivity : NothingBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val appList = getInstalledApps()

        setContent {
            NothingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppList(
                        apps = appList,
                        onAppClick = { appItem -> launchApp(appItem) },
                        onAppLongClick = { appItem -> uninstallApp(appItem) }
                    )
                }
            }
        }
    }

    private fun launchApp(appItem: AppItem) {
        val packageName = appItem.packageName
        val launchAppIntent = packageManager?.getLaunchIntentForPackage(packageName)
        startActivity(launchAppIntent)
        finish()
    }

    private fun uninstallApp(appItem: AppItem) {
        val packageName = appItem.packageName
        val intentDeleteApp = Intent(Intent.ACTION_DELETE)
        intentDeleteApp.data = Uri.parse("package:$packageName")
        startActivity(intentDeleteApp)
    }

    private fun getInstalledApps(): List<AppItem> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val installedApps = applicationContext.packageManager.queryIntentActivities(intent, 0)
        return installedApps.map { resolveInfo ->
            val appName = resolveInfo.activityInfo.loadLabel(packageManager).toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val packageName = resolveInfo.activityInfo.packageName
            AppItem(appName, packageName)
        }.sortedBy { it.name }
    }
}

@Composable
private fun NothingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color.White,
            onPrimary = Color.Black,
            background = Color.Black,
            onBackground = Color.White,
            surface = Color.Black,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color.Black,
            onPrimary = Color.White,
            background = Color.White,
            onBackground = Color.Black,
            surface = Color.White,
            onSurface = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
private fun AppList(
    apps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    onAppLongClick: (AppItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(apps) { appItem ->
            AppListItem(
                appItem = appItem,
                onClick = { onAppClick(appItem) },
                onLongClick = { onAppLongClick(appItem) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AppListItem(
    appItem: AppItem,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val questrialFont = FontFamily(Font(R.font.questrial))

    Text(
        text = appItem.name,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(16.dp),
        fontSize = 30.sp,
        fontFamily = questrialFont,
        color = MaterialTheme.colorScheme.onBackground
    )
}
