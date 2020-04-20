package com.gorrotowi.nothinglauncher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gorrotowi.nothinglauncher.applist.AppListActivity
import com.gorrotowi.nothinglauncher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.txtNothing.setOnClickListener {
            startActivity(Intent(this, AppListActivity::class.java))
        }
    }
}
