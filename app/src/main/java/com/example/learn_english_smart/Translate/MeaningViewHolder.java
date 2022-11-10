package com.example.learn_english_smart.Translate;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learn_english_smart.R;


public class MeaningViewHolder extends RecyclerView.ViewHolder {
    public TextView textView_partsOfSpeech;
    public RecyclerView recycler_definitions;
    public MeaningViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_partsOfSpeech = itemView.findViewById(R.id.textView_partsOfSpeech);
        recycler_definitions = itemView.findViewById(R.id.recycler_definitions);
    }

}