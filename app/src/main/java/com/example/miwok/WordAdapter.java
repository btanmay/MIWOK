package com.example.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    public WordAdapter(Activity context, ArrayList<Word> words,int colorResourceId){
        super(context,0,words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
     //Check whether the existing view is being reused ,otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        //get the {@link } object located at this location in the list
        Word currentWord = getItem(position);

        //find the textview in list_item.xml layout  with the id version name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        //get the version name for the curent Word object and set this text on the
        miwokTextView.setText(currentWord.getmiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        defaultTextView.setText(currentWord.getdefaultTranslation());
//return the whole list of item layout (containing two textviews)

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
    if(currentWord.hasImage()) {

        imageView.setImageResource(currentWord.getImageResourceId());

        imageView.setVisibility(View.VISIBLE);
    }
    else{
        imageView.setVisibility(View.GONE);
    }
//set the theme color for list item
    View textContainer =listItemView.findViewById(R.id.text_container);
//find the color id that resource id map to
    int color = ContextCompat.getColor(getContext(),mColorResourceId);
 //set the background color of the text container view
    textContainer.setBackgroundColor(color);


        return listItemView;

    }
}
