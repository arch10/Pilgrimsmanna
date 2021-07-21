package com.gigaworks.tech.bible.ui.audiobook.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gigaworks.tech.bible.R
import com.gigaworks.tech.bible.databinding.FragmentAudioBinding
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.util.getSoundUrl
import com.gigaworks.tech.bible.util.visible
import java.io.IOException


class AudioFragment : BaseFragment<FragmentAudioBinding>() {

    private val args: AudioFragmentArgs by navArgs()
    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer()
    }
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = Runnable {
        setCurrentPosition()
    }

    private var isPrepared = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar(binding.toolbar, "Now Playing") {
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

        val audioBook = args.audioBook
        binding.trackTitle.text = audioBook.title

        if(!audioBook.image.isNullOrEmpty()) {
            val imgUrl = getSoundUrl(audioBook.image)
            Glide.with(binding.root)
                .load(imgUrl)
                .placeholder(R.drawable.ic_note)
                .into(binding.graphicImage)
        }

        mediaPlayer.setOnPreparedListener {
            it.start()
            setCurrentPosition()
            binding.mediaProgressBar.visible(false)
            binding.play.visible(false)
            binding.pause.visible(true)
            isPrepared = true
        }

        mediaPlayer.setOnCompletionListener {
            binding.pause.visible(false)
            binding.mediaProgressBar.visible(false)
            binding.play.visible(true)
            handler.removeCallbacks(runnable)
        }

        //hide prev and next button
        binding.forward.visible(false)
        binding.backward.visible(false)

        //on play pressed listener
        binding.play.setOnClickListener {

            audioBook.soundFile?.let {
                val soundUrl = getSoundUrl(it)
                if (isPrepared) {
                    binding.mediaProgressBar.visible(false)
                    binding.play.visible(false)
                    binding.pause.visible(true)
                    mediaPlayer.start()
                    setCurrentPosition()
                } else {
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
            }
        }

        //on pause pressed listener
        binding.pause.setOnClickListener {
            handler.removeCallbacks(runnable)
            binding.mediaProgressBar.visible(false)
            binding.play.visible(true)
            binding.pause.visible(false)
            mediaPlayer.pause()
        }
    }

    private fun setCurrentPosition() {
        val currentPos = mediaPlayer.currentPosition
        binding.currentPosition.text = getCurrentPositionString(currentPos)
        handler.postDelayed(runnable, 1000L)
    }

    private fun getCurrentPositionString(currentPos: Int): String {
        return when (val second = currentPos / 1000) {
            in 0..59 -> {
                val sec = prefixNumber(second)
                "00:$sec"
            }
            in 60..3599 -> {
                val min = prefixNumber(second / 60)
                val sec = prefixNumber(second % 60)
                "$min:$sec"
            }
            else -> {
                val hour = prefixNumber(second/3600)
                val min = prefixNumber((second/60)%60)
                val sec = prefixNumber(second)
                "$hour:$min:$sec"
            }
        }
    }

    private fun prefixNumber(num: Int): String {
        return if(num < 10){
            "0$num"
        } else {
            num.toString()
        }
    }

    private fun backPressed() {
        handler.removeCallbacks(runnable)
        mediaPlayer.reset()
        mediaPlayer.release()
        findNavController().navigateUp()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAudioBinding.inflate(inflater, container, false)

}