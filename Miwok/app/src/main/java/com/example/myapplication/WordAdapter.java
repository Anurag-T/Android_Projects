package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Words> {


    public WordAdapter(@NonNull Context context, @NonNull List<Words> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Words currentWord = getItem(position);
        View WordsView = convertView;
        if(WordsView == null){
            WordsView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        TextView mivok = (TextView) WordsView.findViewById(R.id.miwokLetter);
        mivok.setText(currentWord.getMivok());
        TextView english = (TextView) WordsView.findViewById(R.id.englishLetter);
        english.setText(currentWord.getEnglish());
        WordsView.findViewById(R.id.place_holder).setBackgroundResource(currentWord.getColor());
        if(currentWord.getResourceId() != -1){
            ImageView image = (ImageView)WordsView.findViewById(R.id.imageView);
            image.setBackgroundResource(currentWord.getResourceId());


        }else{
            ImageView image = (ImageView)WordsView.findViewById(R.id.imageView);
            image.setVisibility(View.GONE);
        }



        return WordsView;
    }
}
