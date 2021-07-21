package com.gigaworks.tech.bible.ui.bible

import android.view.LayoutInflater
import com.gigaworks.tech.bible.databinding.ActivityBibleBinding
import com.gigaworks.tech.bible.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BibleActivity : BaseActivity<ActivityBibleBinding>() {

    override fun getViewBinding(inflater: LayoutInflater) = ActivityBibleBinding.inflate(inflater)
}