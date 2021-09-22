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


public class PhrasesFragment extends Fragment {
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

        wordList.add(new Words("minto wuksus","Where are you going?",R.color.category_phrases,R.raw.phrase_where_are_you_going));
        wordList.add(new Words("tinnә oyaase'nә","What is your name?",R.color.category_phrases,R.raw.phrase_what_is_your_name));
        wordList.add(new Words("oyaaset...","My name is...",R.color.category_phrases,R.raw.phrase_my_name_is));
        wordList.add(new Words("michәksәs?","How are you feeling?",R.color.category_phrases,R.raw.phrase_how_are_you_feeling));
        wordList.add(new Words("kuchi achit","I’m feeling good",R.color.category_phrases,R.raw.phrase_im_feeling_good));
        wordList.add(new Words("әәnәs'aa?","Are you coming?",R.color.category_phrases,R.raw.phrase_are_you_coming));
        wordList.add(new Words("hәә’ әәnәm","Yes, I’m coming.",R.color.category_phrases,R.raw.phrase_yes_im_coming));
        wordList.add(new Words("әәnәm","I’m coming.",R.color.category_phrases,R.raw.phrase_im_coming));
        wordList.add(new Words("yoowutis","Let’s go.",R.color.category_phrases,R.raw.phrase_lets_go));
        wordList.add(new Words("әnni'nem","Come here.",R.color.category_phrases,R.raw.phrase_come_here));






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