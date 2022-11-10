package com.example.learn_english_smart.Translate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learn_english_smart.R;


public class DefinitionViewHolder extends RecyclerView.ViewHolder {
    public TextView textView_definitions, textView_example, translate,trandic;

    public DefinitionViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_definitions = itemView.findViewById(R.id.textView_definitions);
        textView_example = itemView.findViewById(R.id.textView_example);
        translate = itemView.findViewById(R.id.translate);
        trandic = itemView.findViewById(R.id.trandic);

    }

}
