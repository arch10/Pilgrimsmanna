package com.gigaworks.tech.bible.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import com.gigaworks.tech.bible.BuildConfig
import com.gigaworks.tech.bible.databinding.ActivityAboutBinding
import com.gigaworks.tech.bible.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class AboutActivity : BaseActivity<ActivityAboutBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBar(binding.toolbar, "About") {
            finish()
        }

        val buildDate = BuildConfig.buildTime
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val buildDateText = "Build Date: " + sdf.format(buildDate)
        val versionText = "Version: " + BuildConfig.VERSION_NAME

        binding.build.text = buildDateText
        binding.version.text = versionText
    }

    override fun getViewBinding(inflater: LayoutInflater) = ActivityAboutBinding.inflate(inflater)
}