package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A s
 */
public class ColorsFragment extends Fragment {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    private final AudioManager.OnAudioFocusChangeListener audioListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){

                if(mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.word_list, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        ArrayList<Words> wordList = new ArrayList<>();

        wordList.add(new Words("weṭeṭṭi","red",R.drawable.color_red,R.color.category_colors,R.raw.color_red));
        wordList.add(new Words("chokokki","green",R.drawable.color_green,R.color.category_colors,R.raw.color_green));
        wordList.add(new Words("ṭakaakki","brown",R.drawable.color_brown,R.color.category_colors,R.raw.color_brown));
        wordList.add(new Words("ṭopoppi","gray",R.drawable.color_gray,R.color.category_colors,R.raw.color_gray));
        wordList.add(new Words("kululli","black",R.drawable.color_black,R.color.category_colors,R.raw.color_black));
        wordList.add(new Words("kelelli","white",R.drawable.color_white,R.color.category_colors,R.raw.color_white));
        wordList.add(new Words("ṭopiisә","dusky yellow",R.drawable.color_dusty_yellow,R.color.category_colors,R.raw.color_dusty_yellow));
        wordList.add(new Words("chiwiiṭә","mustard yellow",R.drawable.color_mustard_yellow,R.color.category_colors,R.raw.color_mustard_yellow));






        WordAdapter numbersAdapter = new WordAdapter(getActivity(), wordList);
        ListView rootView = (ListView) view.findViewById(R.id.list);
        rootView.setAdapter(numbersAdapter);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words current = (Words)rootView.getItemAtPosition(position);
                if(mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = MediaPlayer.create(getActivity(),current.getSongId());

                int result = audioManager.requestAudioFocus(audioListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if (mp != null) {
                                mp.release();
                                audioManager.abandonAudioFocus(audioListener);
                                mp = null;
                            }
                        }
                    });
                }else if(result == AudioManager.AUDIOFOCUS_REQUEST_FAILED){
                    mediaPlayer.release();
                }




            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}