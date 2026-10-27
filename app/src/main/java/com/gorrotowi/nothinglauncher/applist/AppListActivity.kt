package com.gorrotowi.nothinglauncher.applist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.gorrotowi.nothinglauncher.NothingBaseActivity
import com.gorrotowi.nothinglauncher.databinding.ActivityAppListBinding

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

        setUpViews()
        setUpListeners()
    }

    private fun setUpListeners() {
        adapter.setOnAppClickListener { appItem ->
            val packageName = appItem.packageName
            val launchAppIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (launchAppIntent != null) {
                startActivity(launchAppIntent)
                finish()
            }
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
        binding.rcAppList.adapter = adapter
        adapter.dataSource = appList
    }

    private fun getInstalledApps(): List<AppItem> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val installedApps = applicationContext.packageManager.queryIntentActivities(intent, 0)
        val selfPackage = packageName
        return installedApps.map { resolveInfo ->
            val appName = resolveInfo.activityInfo.loadLabel(packageManager).toString()
                .replaceFirstChar { it.uppercaseChar() }
            val packageName = resolveInfo.activityInfo.packageName
            AppItem(appName, packageName)
        }
            .filter { it.packageName != selfPackage }
            .sortedBy { it.name }
    }
}
