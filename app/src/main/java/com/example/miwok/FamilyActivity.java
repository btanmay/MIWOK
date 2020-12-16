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

public class FamilyActivity extends AppCompatActivity {
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
        words.add(new Word("Father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("Mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("Son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("Daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("Older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("Younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("Older Sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("Younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("Grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("Grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(this,words,R.color.category_family);

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


                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceID());

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