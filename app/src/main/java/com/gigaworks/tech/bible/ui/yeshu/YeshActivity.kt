package com.gigaworks.tech.bible.ui.yeshu

import android.view.LayoutInflater
import com.gigaworks.tech.bible.databinding.ActivityYeshuBinding
import com.gigaworks.tech.bible.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YeshActivity : BaseActivity<ActivityYeshuBinding>() {


    override fun getViewBinding(inflater: LayoutInflater) = ActivityYeshuBinding.inflate(inflater)
}