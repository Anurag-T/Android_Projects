package com.example.earthquake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.*;
import java.util.zip.Inflater;

import static com.example.earthquake.R.*;

public class CustomAdapter extends ArrayAdapter<Quake> {

    CustomAdapter(Context context, List<Quake> quakeList){
        super(context,0,quakeList);

    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Quake currentItem = getItem(position);

        View currentView = convertView;
        
        if(currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(layout.quake_view,parent,false);
        }
        TextView name =  (TextView)currentView.findViewById(id.name_view);
        String[] arr = currentItem.getmName().split(" of ",-1);
        if(arr.length == 1){
            name.setText(arr[0]);
        }else{
            name.setText(arr[1]);
        }

        name = (TextView) currentView.findViewById(id.ofname_view);
        if(arr.length == 1){
            name.setText("Near the");
        }else
        name.setText(arr[0] + " of");

        name = (TextView) currentView.findViewById(id.magnitude_text_view);
        arr[0] = currentItem.getmMagvalue();
        if(arr[0].length() > 3){
            name.setText(arr[0].substring(0,3));


        }else
        name.setText(arr[0]);

        new GradientDrawable();
        GradientDrawable magnitudeCircle;
        magnitudeCircle = (GradientDrawable) currentView.findViewById(id.magnitude_text_view).getBackground();


        // Get the appropriate background color based on the current earthquake magnitude
        int id;

        switch (Integer.parseInt(arr[0].substring(0,1))){
            case 0:
            case 1:
                id = R.color.magnitude1;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));


                break;

            case 2:
                id = R.color.magnitude2;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));
                break;
            case 3:
                id = R.color.magnitude3;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));
                break;
            case 4:
                id = R.color.magnitude4;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));
                break;
            case 5:
                id = R.color.magnitude5;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));
                break;
            case 6:
                id = R.color.magnitude6;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));
                break;
            case 7:
                id = R.color.magnitude7;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));
                break;
            case 8:
                id = R.color.magnitude8;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));
                break;
            case 9:
                id = R.color.magnitude9;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));
                break;
            default:

                id = color.magnitude10plus;
                magnitudeCircle.setColor( ContextCompat.getColor(getContext(), id));


        }




        name = (TextView) currentView.findViewById(R.id.date_text_view);
        name.setText(currentItem.getmDate());
        return currentView;
         
    }
}
