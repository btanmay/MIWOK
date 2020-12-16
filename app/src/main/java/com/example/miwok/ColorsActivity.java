package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;

    private AudioManager mAudioManaager;

    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };
    private  MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManaager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("Green","chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("Brown","tolookosu",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("Gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("Black","kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("White","kelelli",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("Dusty Yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("Mustard Yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this,words,R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManaager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback


                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceID());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }






    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * clean up the media player by releasing its resources
     */
    private void releaseMediaPlayer() {
        //if the media player is not null then it maybe currently playing a sound
        if(mMediaPlayer != null) {
            //regardless of the currrent state of the mediaplayer release its resources because we no longer need it
            mMediaPlayer.release();

            //setting the media player back to null for our code we've decided setting the mediaplayer to null is an easy way to tell that it is
            //not configured to play an audio file at the moment
            mMediaPlayer = null;

            // Abandon audio focus when playback complete
            mAudioManaager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

};