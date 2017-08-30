package com.example.lin.beginnerchineseapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lin on 08/07/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int myColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        myColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word current = getItem(position);

        TextView chineseTranslation = convertView.findViewById(R.id.chinese_text_view);
        TextView defaultTranslation = convertView.findViewById(R.id.default_text_view);
        ImageView imageView = convertView.findViewById(R.id.image_view);
        RelativeLayout container = convertView.findViewById(R.id.text_container);

        chineseTranslation.setText(current.getChineseTranslation());
        defaultTranslation.setText(current.getDefaultTranslation());
        container.setBackgroundResource(myColorResourceId);

        if (!current.hasImage()) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(current.getImageResourceID());
        }

        return convertView;
    }
}
