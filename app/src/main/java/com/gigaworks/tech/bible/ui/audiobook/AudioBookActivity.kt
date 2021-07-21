package com.gigaworks.tech.bible.ui.audiobook

import android.view.LayoutInflater
import com.gigaworks.tech.bible.databinding.ActivityAudioBookBinding
import com.gigaworks.tech.bible.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioBookActivity : BaseActivity<ActivityAudioBookBinding>() {

    override fun getViewBinding(inflater: LayoutInflater) =
        ActivityAudioBookBinding.inflate(inflater)

}