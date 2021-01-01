package com.example.android.earthquake;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import java.util.List;

public class WordAdaptar extends ArrayAdapter<EQuack>{

    public WordAdaptar(Activity context, List<EQuack> objects){
        super(context,0,objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        EQuack currentWord=getItem(position);
        TextView magnitude=(TextView)listItemView.findViewById(R.id.magnitude);
        magnitude.setText(currentWord.getMagnitude());


        TextView place=(TextView)listItemView.findViewById((R.id.place1));
        place.setText(currentWord.getPlace1());

        place=(TextView)listItemView.findViewById((R.id.place2));
        place.setText(currentWord.getPlace2());

        TextView date=(TextView)listItemView.findViewById((R.id.date));
        date.setText(currentWord.getDate());

        TextView time =(TextView)listItemView.findViewById((R.id.time));
        time.setText(currentWord.getTime());


        GradientDrawable magnitudeCircle = (GradientDrawable)magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentWord.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);





        return  listItemView;
    }

    int getMagnitudeColor(String color){
        double mcolor = Double.parseDouble(color);

        if(mcolor<2) return ContextCompat.getColor(getContext(), R.color.magnitude1);
        else if (mcolor<3) return  ContextCompat.getColor(getContext(), R.color.magnitude2);
        else if (mcolor<4) return  ContextCompat.getColor(getContext(), R.color.magnitude3);
        else if (mcolor<5) return  ContextCompat.getColor(getContext(), R.color.magnitude4);
        else if (mcolor<6) return  ContextCompat.getColor(getContext(), R.color.magnitude5);
        else if (mcolor<7) return  ContextCompat.getColor(getContext(), R.color.magnitude6);
        else if (mcolor<8) return  ContextCompat.getColor(getContext(), R.color.magnitude7);
        else if (mcolor<9) return  ContextCompat.getColor(getContext(), R.color.magnitude8);
        else if (mcolor<10) return  ContextCompat.getColor(getContext(), R.color.magnitude9);

        return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
    }
}
