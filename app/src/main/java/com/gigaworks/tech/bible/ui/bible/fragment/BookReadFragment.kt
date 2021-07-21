package com.gigaworks.tech.bible.ui.bible.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gigaworks.tech.bible.databinding.FragmentBookReadBinding
import com.gigaworks.tech.bible.domain.Book
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.util.getSoundUrl
import com.gigaworks.tech.bible.util.visible
import java.io.IOException


class BookReadFragment : BaseFragment<FragmentBookReadBinding>() {

    private val args: BookReadFragmentArgs by navArgs()

    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer()
    }
    private var isPrepared = false

    private var currentChapter = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar(binding.toolbar, getTitle(args.chapters)) {
            backPressed()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressed()
                }
            })

        setupView()

    }

    private fun setupView() {
        mediaPlayer.setOnPreparedListener {
            it.start()
            binding.mediaProgressBar.visible(false)
            binding.play.visible(false)
            binding.pause.visible(true)
            isPrepared = true
        }
        mediaPlayer.setOnCompletionListener {
            binding.pause.visible(false)
            binding.mediaProgressBar.visible(false)
            binding.play.visible(true)
        }

        val chapterMap = mapChapters(args.chapters)
        setChapter(chapterMap)

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            getChapterList(chapterMap.size)
        )

        binding.chapter.adapter = spinnerAdapter
        binding.chapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentChapter = position + 1
                setChapter(chapterMap)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //on play pressed listener
        binding.play.setOnClickListener {
            getCurrentChapter(chapterMap)?.soundFile?.let {
                val soundUrl = getSoundUrl(it)
                if (isPrepared) {
                    binding.mediaProgressBar.visible(false)
                    binding.play.visible(false)
                    binding.pause.visible(true)
                    mediaPlayer.start()
                } else {
                    loadMedia(soundUrl)
                }
            }
        }

        //on pause pressed listener
        binding.pause.setOnClickListener {
            binding.mediaProgressBar.visible(false)
            binding.play.visible(true)
            binding.pause.visible(false)
            mediaPlayer.pause()
        }

        //on next click
        binding.forward.setOnClickListener {
            if (currentChapter < chapterMap.size) {
                currentChapter++
                binding.chapter.setSelection(currentChapter - 1)
                val sound = getCurrentChapter(chapterMap)
                sound?.soundFile?.let {
                    val soundUrl = getSoundUrl(it)
                    isPrepared = false
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    loadMedia(soundUrl)
                }
            }
        }

        //on prev click
        binding.backward.setOnClickListener {
            if (currentChapter > 1) {
                currentChapter--
                binding.chapter.setSelection(currentChapter - 1)
                val sound = getCurrentChapter(chapterMap)
                sound?.soundFile?.let {
                    val soundUrl = getSoundUrl(it)
                    isPrepared = false
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    loadMedia(soundUrl)
                }
            }
        }

    }

    private fun loadMedia(soundUrl: String) {
        try {
            mediaPlayer.setDataSource(soundUrl)
            mediaPlayer.prepareAsync()
            binding.mediaProgressBar.visible(true)
            binding.play.visible(false)
            binding.pause.visible(false)
        } catch (e: IOException) {
            Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getChapterList(size: Int): List<String> {
        val chapters = mutableListOf<String>()
        for (i in 1..size) {
            chapters.add("Chapter $i")
        }
        return chapters
    }

    private fun setChapter(chapterMap: Map<String, Book>) {
        val text = getCurrentChapter(chapterMap)?.text ?: ""
        binding.tvContent.text = refineText(text)
    }

    private fun getCurrentChapter(chapterMap: Map<String, Book>): Book? {
        return chapterMap[currentChapter.toString()]
    }

    private fun refineText(text: String): String {
        var text = text
        text = text.replace("</p><p>".toRegex(), "\n\n")
        text = text.replace("<p>".toRegex(), "")
        text = text.replace("</p>".toRegex(), "")
        return text
    }

    private fun mapChapters(chapters: Array<Book>): Map<String, Book> {
        val map = mutableMapOf<String, Book>()
        for (chapter in chapters) {
            map[chapter.chapter] = chapter
        }
        return map
    }

    private fun getTitle(chapters: Array<Book>): String {
        return if (chapters.isNotEmpty()) {
            chapters[0].title
        } else {
            "Book"
        }
    }

    private fun backPressed() {
        mediaPlayer.reset()
        mediaPlayer.release()
        findNavController().navigateUp()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBookReadBinding.inflate(inflater, container, false)

}