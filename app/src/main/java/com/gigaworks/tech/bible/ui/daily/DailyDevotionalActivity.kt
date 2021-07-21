package com.gigaworks.tech.bible.ui.daily

import android.view.LayoutInflater
import com.gigaworks.tech.bible.databinding.ActivityDailyDevotionalBinding
import com.gigaworks.tech.bible.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyDevotionalActivity : BaseActivity<ActivityDailyDevotionalBinding>() {

    override fun getViewBinding(inflater: LayoutInflater) =
        ActivityDailyDevotionalBinding.inflate(inflater)
}