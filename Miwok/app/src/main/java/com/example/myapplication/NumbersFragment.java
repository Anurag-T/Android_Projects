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
 * A simple {@link Fragment} subclass.
 * Use the {@link NumbersFragment#} factory method to
 * create an instance of this fragment.
 */
public class NumbersFragment extends Fragment {



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
    public void onStop() {
        super.onStop();
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.word_list, container, false);

        // Inflate the layout for this fragment
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);




        ArrayList<Words> wordList = new ArrayList<>();
        wordList.add(new Words("lutti","one",R.drawable.number_one,R.color.category_numbers,R.raw.number_one));
        wordList.add(new Words("otiiko","two",R.drawable.number_two,R.color.category_numbers,R.raw.number_two));
        wordList.add(new Words("tolookosu","three",R.drawable.number_three,R.color.category_numbers,R.raw.number_three));
        wordList.add(new Words("oyyisa","four",R.drawable.number_four,R.color.category_numbers,R.raw.number_four));
        wordList.add(new Words("massokka","five",R.drawable.number_five,R.color.category_numbers,R.raw.number_five));
        wordList.add(new Words("temmokka","six",R.drawable.number_six,R.color.category_numbers,R.raw.number_six));
        wordList.add(new Words("kenekaku","seven",R.drawable.number_seven,R.color.category_numbers,R.raw.number_seven));
        wordList.add(new Words("kawinta","eight",R.drawable.number_eight,R.color.category_numbers,R.raw.number_eight));
        wordList.add(new Words("wo’e","nine",R.drawable.number_nine,R.color.category_numbers,R.raw.number_nine));
        wordList.add(new Words("na’aacha","ten",R.drawable.number_ten,R.color.category_numbers,R.raw.number_ten));





        WordAdapter numbersAdapter = new WordAdapter(getActivity(), wordList);
        ListView listView = (ListView) rootview.findViewById(R.id.list);
        listView.setAdapter(numbersAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words current = (Words) listView.getItemAtPosition(position);
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
        return rootview;
    }
}