package com.gorrotowi.nothinglauncher.applist

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.gorrotowi.nothinglauncher.NothingBaseActivity
import com.gorrotowi.nothinglauncher.databinding.ActivityAppListBinding
import kotlinx.android.synthetic.main.activity_app_list.*

class AppListActivity : NothingBaseActivity() {

    private val binding by lazy {
        ActivityAppListBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        AppListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setUpViews()
        setUpListeners()
    }

    private fun setUpListeners() {
        adapter.setOnAppClickListener { appItem ->
            val packageName = appItem.packageName
            val launchAppIntent = packageManager?.getLaunchIntentForPackage(packageName)
            startActivity(launchAppIntent)
            finish()
        }

        adapter.setOnLongAppClickListener { appItem ->
            val packageName = appItem.packageName
            val intentDeleteApp = Intent(Intent.ACTION_DELETE)
            intentDeleteApp.data = Uri.parse("package:$packageName")
            startActivity(intentDeleteApp)
        }
    }

    private fun setUpViews() {
        val appList = getInstalledApps()
        rcAppList.adapter = adapter
        adapter.dataSource = appList
    }

    @SuppressLint("DefaultLocale")
    private fun getInstalledApps(): List<AppItem> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val installedApps = applicationContext.packageManager.queryIntentActivities(intent, 0)
        return installedApps.map { resolveInfo ->
            val appName = resolveInfo.activityInfo.loadLabel(packageManager).toString().capitalize()
            val packageName = resolveInfo.activityInfo.packageName
            AppItem(appName, packageName)
        }
            .sortedBy { it.name }
    }
}
