package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

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

        private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
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
            words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
            words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
            words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
            words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
            words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
            words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
            words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
            words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
            words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
            words.add(new Word("ten", "na'accha", R.drawable.number_ten, R.raw.number_ten));

            WordAdapter adapter = new WordAdapter(NumbersActivity.this, words, R.color.category_numbers);

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


                        mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceID());

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
            if (mMediaPlayer != null) {
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