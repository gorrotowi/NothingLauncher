package com.gorrotowi.nothinglauncher

import android.content.Intent
import android.os.Bundle
import com.gorrotowi.nothinglauncher.applist.AppListActivity
import com.gorrotowi.nothinglauncher.databinding.ActivityMainBinding


class MainActivity : NothingBaseActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setNightMode()
        overrideNotch()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.txtNothing.setOnClickListener {
            startActivity(Intent(this, AppListActivity::class.java))
        }
    }
}
