package com.gigaworks.tech.bible.ui.yeshu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gigaworks.tech.bible.cache.entity.toDomain
import com.gigaworks.tech.bible.databinding.FragmentYesuListBinding
import com.gigaworks.tech.bible.domain.Sound
import com.gigaworks.tech.bible.ui.audiobook.fragment.AudioBookFragmentDirections
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.ui.yeshu.adapter.YeshuListAdapter
import com.gigaworks.tech.bible.ui.yeshu.viewmodel.YeshuViewModel
import com.gigaworks.tech.bible.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YeshuListFragment : BaseFragment<FragmentYesuListBinding>() {

    private val viewModel: YeshuViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar(binding.toolbar, "More") {
            requireActivity().finish()
        }

        setupView()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.yeshuList.observe(viewLifecycleOwner) { yeshuList ->
            val adapter = YeshuListAdapter(yeshuList.map { it.toDomain() },
                object : YeshuListAdapter.OnYeshuListItemClickListener {
                    override fun onItemClick(sound: Sound) {
                        if (sound.page == "dummy") {
                            val actions = YeshuListFragmentDirections.playAudio(sound)
                            findNavController().navigate(actions)
                        } else if (sound.page == "yeshu") {
                            val actions = YeshuListFragmentDirections.yeshuList()
                            findNavController().navigate(actions)
                        }
                    }
                })
            binding.rv.adapter = adapter
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loader.visible(it)
        }
    }

    private fun setupView() {
        viewModel.getYeshuList()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentYesuListBinding.inflate(inflater, container, false)
}