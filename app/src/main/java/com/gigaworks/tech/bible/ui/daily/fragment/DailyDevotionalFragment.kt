package com.gigaworks.tech.bible.ui.daily.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gigaworks.tech.bible.cache.entity.toDomain
import com.gigaworks.tech.bible.databinding.FragmentDailyDevotionalBinding
import com.gigaworks.tech.bible.domain.Sound
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.ui.daily.adapter.DailyDevotionalAdapter
import com.gigaworks.tech.bible.ui.daily.viewmodel.DailyDevotionalViewModel
import com.gigaworks.tech.bible.util.logD
import com.gigaworks.tech.bible.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyDevotionalFragment : BaseFragment<FragmentDailyDevotionalBinding>() {

    private val viewModel: DailyDevotionalViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar(binding.toolbar, "Daily Devotional") {
            requireActivity().finish()
        }

        setupView()
        setupObservables()

    }

    private fun setupView() {

    }

    private fun setupObservables() {
        viewModel.books.observe(viewLifecycleOwner) { soundList ->
            val adapter = DailyDevotionalAdapter(soundList.map { it.toDomain() },
                object : DailyDevotionalAdapter.OnClickListener {
                    override fun onClick(sound: Sound) {
                        val action =
                            DailyDevotionalFragmentDirections.dailyDevotionalAction(sound.mainCategory!!)
                        findNavController().navigate(action)
                    }
                })
            binding.rv.adapter = adapter
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loader.visible(it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDailyDevotionalBinding.inflate(inflater, container, false)

}