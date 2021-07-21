package com.gigaworks.tech.bible.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.gigaworks.tech.bible.R
import com.gigaworks.tech.bible.databinding.ActivityMainBinding
import com.gigaworks.tech.bible.ui.about.AboutActivity
import com.gigaworks.tech.bible.ui.audiobook.AudioBookActivity
import com.gigaworks.tech.bible.ui.base.BaseActivity
import com.gigaworks.tech.bible.ui.bible.BibleActivity
import com.gigaworks.tech.bible.ui.contact.ContactActivity
import com.gigaworks.tech.bible.ui.daily.DailyDevotionalActivity
import com.gigaworks.tech.bible.ui.home.viewmodel.MainViewModel
import com.gigaworks.tech.bible.ui.yeshu.YeshActivity
import com.gigaworks.tech.bible.util.PLAY_STORE_LINK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar(binding.toolbar, getText(R.string.app_name).toString())

        setupView()
        setupObservables()
    }

    private fun setupObservables() {

    }

    private fun setupView() {
        viewModel.getData()
        binding.bible.setOnClickListener {
            startActivity(Intent(this, BibleActivity::class.java))
        }
        binding.audioBooks.setOnClickListener {
            startActivity(Intent(this, AudioBookActivity::class.java))
        }
        binding.dailyDevotional.setOnClickListener {
            startActivity(Intent(this, DailyDevotionalActivity::class.java))
        }
        binding.yeshu.setOnClickListener {
            startActivity(Intent(this, YeshActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> startActivity(Intent(this, AboutActivity::class.java))
            R.id.share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "PilgrimsManna")
                val msg =
                    """
                    Hey, checkout this really cool app called PilgrimsManna. Go to this link to download this app now.
                    $PLAY_STORE_LINK
                    """.trimIndent()
                intent.putExtra(Intent.EXTRA_TEXT, msg)
                startActivity(Intent.createChooser(intent, "Choose one"))
            }
            R.id.contact -> startActivity(Intent(this, ContactActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

}