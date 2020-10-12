package com.octavian.youtubeplayer

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "nNe4RUHpLWI"
const val YOUTUBE_PLAYLIST = "PLhQCJTkrHOwSX8LUnIMgaTq3chP1tiTut"

class YouTubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val TAG = "YouTubeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_youtube)
//        val layout = findViewById<ConstraintLayout>(R.id.activity_youtube)

        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

//        val button1 = Button(this)
//        button1.layoutParams = ConstraintLayout.LayoutParams(600, 180)
//        button1.text = "Button added"
//        layout.addView(button1)
        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?,
                                         wasRestored: Boolean) {
        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: youTubePlayer is ${youTubePlayer?.javaClass}")
        Toast.makeText(this, "Initialized YouTube Player successfully", Toast.LENGTH_SHORT).show()

        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)
        if (!wasRestored) {
            youTubePlayer?.cueVideo(YOUTUBE_VIDEO_ID)
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?,
                                         youTubeInitializationResult: YouTubeInitializationResult?) {
        val REQUEST_CODE = 0

        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the YouTube Player ($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {

        override fun onPlaying() {
            Toast.makeText(this@YouTubeActivity, "Good, video is playing ok", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YouTubeActivity, "Video has paused", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YouTubeActivity, "Video has stopped", Toast.LENGTH_SHORT).show()
        }

        override fun onBuffering(isBuffering: Boolean) {

        }

        override fun onSeekTo(newPositionMillis: Int) {

        }
    }

    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {

        override fun onLoading() {
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onAdStarted() {
            Toast.makeText(this@YouTubeActivity, "Click Ad now, make the video creator rich!", Toast.LENGTH_SHORT).show()
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YouTubeActivity, "Video has started", Toast.LENGTH_SHORT).show()
        }

        override fun onVideoEnded() {
            Toast.makeText(this@YouTubeActivity, "Congratulations! You've completed another video.", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }
}