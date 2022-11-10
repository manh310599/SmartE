package com.example.learn_english_smart.Translate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.learn_english_smart.Model.meanings;
import com.example.learn_english_smart.R;

import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningViewHolder> {
    private Context context;
    private List<meanings> meaningsList;

    public MeaningAdapter(Context context, List<meanings> meaningsList) {
        this.context = context;
        this.meaningsList = meaningsList;
    }

    @NonNull
    @Override
    public MeaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeaningViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meanings_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningViewHolder holder, int position) {
        holder.textView_partsOfSpeech.setText("Parts of Speech: " + meaningsList.get(position).getPartOfSpeech());

        holder.recycler_definitions.setHasFixedSize(true);
        holder.recycler_definitions.setLayoutManager(new GridLayoutManager(context, 1));
        DefinitionAdapter definitionAdapter = new DefinitionAdapter(context, meaningsList.get(position).getDefinitions());
        holder.recycler_definitions.setAdapter(definitionAdapter);

    }


    @Override
    public int getItemCount() {
        return meaningsList.size();
    }
}