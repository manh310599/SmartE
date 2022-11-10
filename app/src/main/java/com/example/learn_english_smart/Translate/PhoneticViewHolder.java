package com.example.learn_english_smart.Translate;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learn_english_smart.R;


public class PhoneticViewHolder extends RecyclerView.ViewHolder {
    public TextView textView_phonetic;
    public ImageButton imageButton_audio;
    public PhoneticViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_phonetic = itemView.findViewById(R.id.textView_phonetic);
        imageButton_audio = itemView.findViewById(R.id.imageButton_audio);
    }

}
