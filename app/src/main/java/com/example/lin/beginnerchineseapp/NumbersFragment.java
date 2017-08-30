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
public class NumbersFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

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

    public NumbersFragment() {
        // Required empty public constructor
    }

    public static NumbersFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        NumbersFragment fragment = new NumbersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("one", "一", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "二", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "三", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "四", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "五", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "六", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "七", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "八", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "九", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "十", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                int res = am.requestAudioFocus(amChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getActivity(), words.get(i).getAudioResourceID());
                    mp.start();
                    mp.setOnCompletionListener(myCompleteListener);
                }

            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
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
