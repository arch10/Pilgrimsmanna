package com.gigaworks.tech.bible.ui.daily.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gigaworks.tech.bible.cache.entity.toDomain
import com.gigaworks.tech.bible.databinding.FragmentDailyDevotionalListBinding
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.ui.daily.adapter.DailyReadAdapter
import com.gigaworks.tech.bible.ui.daily.viewmodel.DailyDevotionalViewModel
import com.gigaworks.tech.bible.util.logD
import com.gigaworks.tech.bible.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyDevotionalListFragment : BaseFragment<FragmentDailyDevotionalListBinding>() {

    private val args: DailyDevotionalListFragmentArgs by navArgs()
    private val viewModel: DailyDevotionalViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar(binding.toolbar, args.title) {
            findNavController().navigateUp()
        }

        setupView()
        setupObservables()

    }

    private fun setupObservables() {
        viewModel.dailyDevotionals.observe(viewLifecycleOwner) { dailyReadList ->
            val adapter = DailyReadAdapter(dailyReadList.map { it.title },
                object : DailyReadAdapter.OnClickListener {
                    override fun onClick(string: String, position: Int) {
                        val action = DailyDevotionalListFragmentDirections.actionPlay(
                            position,
                            dailyReadList.map { it.toDomain() }.toTypedArray()
                        )
                        findNavController().navigate(action)
                    }
                })
            binding.rv.adapter = adapter
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loader.visible(it)
            binding.rv.visible(!it)
        }
    }

    private fun setupView() {
        viewModel.getDailyDevotionals(args.title, binding.month.selectedItemId.toString())
        binding.month.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = binding.month.selectedItem.toString()
                viewModel.getDailyDevotionals(args.title, selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDailyDevotionalListBinding.inflate(inflater, container, false)
}