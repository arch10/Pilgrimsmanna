package com.gigaworks.tech.bible.ui.bible.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.gigaworks.tech.bible.databinding.FragmentBibleBinding
import com.gigaworks.tech.bible.ui.base.BaseFragment

class BibleFragment : BaseFragment<FragmentBibleBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar(binding.toolbar, "Bible") {
            requireActivity().finish()
        }
        setupView()
    }

    private fun setupView() {
        binding.gujrati.setOnClickListener {
            val action = BibleFragmentDirections.booksAction("gujarati", "Gujarati Bible")
            it.findNavController().navigate(action)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBibleBinding.inflate(inflater, container, false)

}