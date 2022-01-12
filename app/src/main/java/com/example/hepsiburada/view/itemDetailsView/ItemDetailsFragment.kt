package com.example.hepsiburada.view.itemDetailsView

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.hepsiburada.R
import com.example.hepsiburada.databinding.ItemDetailsFragmentBinding
import com.example.hepsiburada.network.request.iTunesSearchKeys
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemDetailsFragment : Fragment(), ItemDetailsClickListener {

    private val args: ItemDetailsFragmentArgs by navArgs()
    private lateinit var mediaPlayer: MediaPlayer
    private var currentAudioPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mediaPlayer = MediaPlayer()

        val viewWithBinding: ItemDetailsFragmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_details_fragment,
            container,
            false
        )

        // if category is books, iTunesSearch Api won't return "primaryGenre" key so instead we set it from "genreList"
        if (args.itemCategory == iTunesSearchKeys.BOOKS) {
            if (!args.selectedItem.genreList.isNullOrEmpty()) {
                args.selectedItem.genreName = args.selectedItem.genreList!![0]
            }
        }

        // to show song sample we prepare media player if category is music
        if (args.itemCategory == iTunesSearchKeys.MUSIC) {
            prepareMediaPlayer(args.selectedItem.previewUrl)
        }

        // these three is for databinding, gives parameters for data variables
        viewWithBinding.item = args.selectedItem
        viewWithBinding.category = args.itemCategory
        viewWithBinding.clickListener = this

        // these scenarios handled with view binding.
        viewWithBinding.descriptonLayer.visibility = if (args.itemCategory != iTunesSearchKeys.MOVIES && args.itemCategory != iTunesSearchKeys.BOOKS) View.GONE else View.VISIBLE
        viewWithBinding.previewSongLayer.visibility = if (args.itemCategory == iTunesSearchKeys.MUSIC) View.VISIBLE else View.GONE
        /*
        *  only books and movies has descriptions so if category is not one of them description layer view is gone
        *  also only musics has previewSong feature so it's only visible on music category
        * */

        viewWithBinding.details.movementMethod = ScrollingMovementMethod() // scroll added for long description scenarios

        // handles play button for song sample
        viewWithBinding.listenButton.setOnClickListener {
            if (currentAudioPosition == 0 && !mediaPlayer.isPlaying) {
                mediaPlayer.prepareAsync()
                viewWithBinding.listenButton.text = getString(R.string.pause)
            } else {
                if (mediaPlayer.isPlaying) {
                    currentAudioPosition = mediaPlayer.currentPosition
                    mediaPlayer.pause()
                    viewWithBinding.listenButton.text = getString(R.string.play)
                } else {
                    mediaPlayer.seekTo(currentAudioPosition)
                    mediaPlayer.start()
                    viewWithBinding.listenButton.text = getString(R.string.pause)
                }
            }
        }

        return viewWithBinding.root
    }

    // navigates to iTunes page of item
    override fun onNavigateITunesButtonClick(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }

    private fun prepareMediaPlayer(url: String) {
        val uri = Uri.parse(url)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        mediaPlayer.setDataSource(requireContext(), uri)
        mediaPlayer.setAudioAttributes(audioAttributes)

        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }

    }
}