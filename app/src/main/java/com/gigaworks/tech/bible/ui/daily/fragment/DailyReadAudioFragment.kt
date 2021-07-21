package com.gigaworks.tech.bible.ui.daily.fragment

import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gigaworks.tech.bible.databinding.FragmentDailyReadAudioBinding
import com.gigaworks.tech.bible.domain.Sound
import com.gigaworks.tech.bible.ui.base.BaseFragment
import com.gigaworks.tech.bible.ui.daily.viewmodel.DailyDevotionalViewModel
import com.gigaworks.tech.bible.util.getSoundUrl
import com.gigaworks.tech.bible.util.logD
import com.gigaworks.tech.bible.util.visible
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class DailyReadAudioFragment : BaseFragment<FragmentDailyReadAudioBinding>() {

    private val args: DailyReadAudioFragmentArgs by navArgs()
    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer()
    }
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = Runnable {
        setCurrentPosition()
    }
    private var isPrepared = false

    private var position = 0

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
        setupObservables()

    }

    private fun setupObservables() {
    }

    private fun setupView() {
        position = args.position
        val soundList = args.soundList
        val currentSound = soundList[position]
        setTrackTitle(currentSound)

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

        //on play pressed listener
        binding.play.setOnClickListener {
            currentSound.soundFile?.let {
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
            binding.mediaProgressBar.visible(false)
            binding.play.visible(true)
            binding.pause.visible(false)
            mediaPlayer.pause()
            handler.removeCallbacks(runnable)
        }

        //on next click
        binding.forward.setOnClickListener {
            if(position < soundList.size - 1) {
                position++
                val sound = soundList[position]
                sound.soundFile?.let {
                    val soundUrl = getSoundUrl(it)
                    setTrackTitle(sound)
                    isPrepared = false
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    handler.removeCallbacks(runnable)
                    try{
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

        //on prev click
        binding.backward.setOnClickListener {
            if(position > 0) {
                position--
                val sound = soundList[position]
                sound.soundFile?.let {
                    val soundUrl = getSoundUrl(it)
                    setTrackTitle(sound)
                    isPrepared = false
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    handler.removeCallbacks(runnable)
                    try{
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

    private fun setTrackTitle(sound: Sound) {
        binding.trackTitle.text = sound.title
        binding.subtitle.text = sound.mainCategory
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDailyReadAudioBinding.inflate(inflater, container, false)

}