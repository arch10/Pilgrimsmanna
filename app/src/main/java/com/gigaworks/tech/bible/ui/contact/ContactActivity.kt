package com.gigaworks.tech.bible.ui.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import com.gigaworks.tech.bible.R
import com.gigaworks.tech.bible.databinding.ActivityContactBinding
import com.gigaworks.tech.bible.ui.base.BaseActivity


class ContactActivity : BaseActivity<ActivityContactBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar(binding.toolbar, "Contact us") {
            finish()
        }

        setupView()
    }

    private fun setupView() {
        binding.locationCard.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode("Raghunath Vihar"))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.phoneCard.setOnClickListener {
            var phone = getString(R.string.phone).trim { it <= ' ' }
            phone = phone.replace(" ", "")
            val uri = "tel:$phone"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }
        binding.websiteCard.setOnClickListener {
            val url = resources.getString(R.string.website).trim { it <= ' ' }
            val webPage = Uri.parse("http:$url")
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    override fun getViewBinding(inflater: LayoutInflater) = ActivityContactBinding.inflate(inflater)
}