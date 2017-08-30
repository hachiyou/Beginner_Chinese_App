package com.example.lin.beginnerchineseapp;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {


    private MediaPlayer mp;
    private AudioManager am;
    private AudioManager.OnAudioFocusChangeListener amChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mp.pause();
                mp.seekTo(0);
            } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                mp.start();
            } else if (i == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener myCompleteListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> colors = new ArrayList<>();
        colors.add(new Word("red", "红", R.drawable.color_red, R.raw.color_red));
        colors.add(new Word("orange", "橙", R.drawable.color_orange, R.raw.color_orange));
        colors.add(new Word("yellow", "黄", R.drawable.color_yellow, R.raw.color_yellow));
        colors.add(new Word("green", "绿", R.drawable.color_green, R.raw.color_green));
        colors.add(new Word("cyan", "青", R.drawable.color_cyan, R.raw.color_cyan));
        colors.add(new Word("blue", "蓝", R.drawable.color_blue, R.raw.color_blue));
        colors.add(new Word("indigo", "靛", R.drawable.color_indigo, R.raw.color_indigo));
        colors.add(new Word("violet", "紫", R.drawable.color_violet, R.raw.color_violet));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), colors, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                int res = am.requestAudioFocus(amChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getActivity(), colors.get(i).getAudioResourceID());
                    mp.start();
                    mp.setOnCompletionListener(myCompleteListener);
                }
            }
        });

        return rootView;
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp = null;
            am.abandonAudioFocus(amChangeListener);
        }
    }
}
