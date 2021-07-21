package com.gigaworks.tech.bible.ui.yeshu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gigaworks.tech.bible.cache.entity.toDomain
import com.gigaworks.tech.bible.databinding.FragmentYeshuItemBinding
import com.gigaworks.tech.bible.domain.Sound
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.ui.yeshu.adapter.YeshuItemAdapter
import com.gigaworks.tech.bible.ui.yeshu.viewmodel.YeshuViewModel
import com.gigaworks.tech.bible.util.visible

class YeshuItemFragment : BaseFragment<FragmentYeshuItemBinding>() {

    private val viewModel: YeshuViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar(binding.toolbar, "Yeshu Masih Ka Jivan") {
            findNavController().navigateUp()
        }

        setupView()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.yeshuItemList.observe(viewLifecycleOwner) { yeshuItem ->
            val itemList = yeshuItem.map { it.toDomain() }
            val adapter = YeshuItemAdapter(itemList, object: YeshuItemAdapter.OnClickListener {
                override fun onClick(sound: Sound, position: Int) {
                    val action = YeshuItemFragmentDirections.playAudio(position, itemList.toTypedArray())
                    findNavController().navigate(action)
                }
            })
            binding.rv.adapter = adapter
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loader.visible(it)
        }
    }

    private fun setupView() {
        viewModel.getYeshuItemList()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentYeshuItemBinding.inflate(inflater, container, false)
}