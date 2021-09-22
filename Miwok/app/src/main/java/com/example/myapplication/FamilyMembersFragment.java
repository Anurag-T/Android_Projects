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


public class FamilyMembersFragment extends Fragment {

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
        wordList.add(new Words("әpә","father",R.drawable.family_father,R.color.category_family,R.raw.family_father));
        wordList.add(new Words("әṭa","mother",R.drawable.family_mother,R.color.category_family,R.raw.family_mother));
        wordList.add(new Words("angsi","son",R.drawable.family_son,R.color.category_family,R.raw.family_son));
        wordList.add(new Words("tune","daughter",R.drawable.family_daughter,R.color.category_family,R.raw.family_daughter));
        wordList.add(new Words("taachi","older brother",R.drawable.family_older_brother,R.color.category_family,R.raw.family_older_brother));
        wordList.add(new Words("chalitti","younger brother",R.drawable.family_younger_brother,R.color.category_family,R.raw.family_younger_brother));
        wordList.add(new Words("teṭe","older sister",R.drawable.family_older_sister,R.color.category_family,R.raw.family_older_sister));
        wordList.add(new Words("kolliti","younger sister",R.drawable.family_younger_sister,R.color.category_family,R.raw.family_younger_sister));
        wordList.add(new Words("ama","grandmother",R.drawable.family_grandmother,R.color.category_family,R.raw.family_grandmother));
        wordList.add(new Words("paapa","grandfather",R.drawable.family_grandfather,R.color.category_family,R.raw.family_grandfather));





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