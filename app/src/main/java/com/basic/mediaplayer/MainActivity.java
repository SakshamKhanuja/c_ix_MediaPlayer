package com.basic.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.basic.mediaplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Creates and performs all actions for Audio.
    private MediaPlayer mPlayer;

    // Shows messages to the user.
    private Toast mToast;

    // Gets invoked when an attached View is clicked.
    private final View.OnClickListener mClickListener = view -> {
        int id = view.getId();
        if (id == R.id.button_play) {
            userPressedPlay();
        } else if (id == R.id.button_pause) {
            userPressedPause();
        } else if (id == R.id.button_stop) {
            userPressedStop();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate((LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        setContentView(binding.getRoot());

        // Attach OnClickListener to all Buttons in "activity_main.xml" layout.
        binding.buttonPause.setOnClickListener(mClickListener);
        binding.buttonPlay.setOnClickListener(mClickListener);
        binding.buttonStop.setOnClickListener(mClickListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Stop playing and release resources.
        stopAndReleaseResources();
    }

    /**
     * Changes state of the player to "Stop" and releases all memory resources.
     */
    private void stopAndReleaseResources() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            showToast("Song is stopped, memory resources are released.");
        }
    }

    /**
     * Shows a Toast with custom message. It removes the current ongoing Toast.
     *
     * @param message is the argument.
     */
    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * Gets invoked when the user presses the "Pause" Button. It pauses the player if it was
     * playing audio.
     */
    private void userPressedPause() {
        if (mPlayer == null) {
            showToast("Song was never played. Play the song");
        } else if (mPlayer.isPlaying()) {
            showToast("Song is paused.");
            mPlayer.pause();
        } else {
            showToast("Song is already paused.");
        }
    }

    /**
     * Gets invoked when the user presses the "Play" Button.
     */
    private void userPressedPlay() {
        if (mPlayer == null) {
            showToast("Song is playing.");

            // Prepare the MediaPlayer to play "bensound_dubstep.mp3".
            mPlayer = MediaPlayer.create(this, R.raw.bensound_dubstep);

            // When the playback is completed release all resources.
            mPlayer.setOnCompletionListener(mediaPlayer -> stopAndReleaseResources());

            // Starts playing the song.
            mPlayer.start();
        } else if (mPlayer.isPlaying()) {
            showToast("Song is already playing.");
        } else {
            showToast("Song is playing.");
            mPlayer.start();
        }
    }

    /**
     * Gets invokes when the user presses the "Stop" Button. If player is initialized it stops and
     * releases all memory resources.
     */
    private void userPressedStop() {
        if (mPlayer != null) {
            stopAndReleaseResources();
        } else {
            showToast("Song was never played. Play the song");
        }
    }
}