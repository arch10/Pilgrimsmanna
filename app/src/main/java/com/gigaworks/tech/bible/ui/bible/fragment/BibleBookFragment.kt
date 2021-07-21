package com.gigaworks.tech.bible.ui.bible.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gigaworks.tech.bible.cache.entity.toDomain
import com.gigaworks.tech.bible.databinding.FragmentBibleBookBinding
import com.gigaworks.tech.bible.domain.Book
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.ui.bible.adapter.BookAdapter
import com.gigaworks.tech.bible.ui.bible.viewmodel.BibleViewModel
import com.gigaworks.tech.bible.util.logD
import com.gigaworks.tech.bible.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BibleBookFragment : BaseFragment<FragmentBibleBookBinding>() {

    private val args: BibleBookFragmentArgs by navArgs()
    private val viewModel: BibleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar(binding.toolbar, args.title) {
            findNavController().navigateUp()
        }
        setupView()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.books.observe(viewLifecycleOwner) { result ->
            val bookList = filterBooksByCategory(result.map { it.toDomain() })
            val adapter =
                BookAdapter(bookList, object : BookAdapter.OnBookClickListener {
                    override fun onBookClick(book: Book) {
                        val action = BibleBookFragmentDirections.actionRead(
                            getBookChapters(
                                book,
                                result.map { it.toDomain() }).toTypedArray()
                        )
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
        viewModel.getBooksByCategory(args.category)
    }

    private fun filterBooksByCategory(list: List<Book>): List<Book> {
        val bookList = mutableListOf<Book>()
        val titleSet = mutableSetOf<String>()
        for (book in list) {
            val title = book.title
            if (!titleSet.contains(title)) {
                titleSet.add(title)
                bookList.add(book)
            }
        }
        return bookList
    }

    private fun getBookChapters(book: Book, list: List<Book>): List<Book> {
        val bookList = mutableListOf<Book>()
        for (b in list) {
            val title = b.title
            if (title == book.title) {
                bookList.add(b)
            }
        }
        return bookList
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBibleBookBinding.inflate(inflater, container, false)

}