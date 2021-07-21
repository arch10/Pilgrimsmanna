package com.gigaworks.tech.bible.ui.audiobook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gigaworks.tech.bible.cache.entity.toDomain
import com.gigaworks.tech.bible.databinding.FragmentAudioBookBinding
import com.gigaworks.tech.bible.domain.Sound
import com.gigaworks.tech.bible.ui.audiobook.adapter.AudioBookAdapter
import com.gigaworks.tech.bible.ui.audiobook.viewmodel.AudioBookViewModel
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.util.logD
import com.gigaworks.tech.bible.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioBookFragment : BaseFragment<FragmentAudioBookBinding>() {

    private val viewModel: AudioBookViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar(binding.toolbar, "Audio Books"){
            requireActivity().finish()
        }

        setupView()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.books.observe(viewLifecycleOwner) { audioBookList ->
            val adapter = AudioBookAdapter(audioBookList.map { it.toDomain() }, object: AudioBookAdapter.OnAudioBookClickListener{
                override fun onAudioBookClick(book: Sound) {
                    val action = AudioBookFragmentDirections.audioAction(book)
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

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAudioBookBinding.inflate(inflater, container, false)
}