package com.example.learn_english_smart.Translate;




import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.learn_english_smart.Model.phonetics;
import com.example.learn_english_smart.R;

import java.io.IOException;
import java.util.List;


public class PhoneticAdapter extends RecyclerView.Adapter<PhoneticViewHolder> {
    private Context context;
    private List<phonetics> phoneticsList;

    public PhoneticAdapter(Context context, List<phonetics> phoneticsList) {
        this.context = context;
        this.phoneticsList = phoneticsList;
    }

    @NonNull
    @Override
    public PhoneticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneticViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.phonetic_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticViewHolder holder, int position) {
        holder.textView_phonetic.setText(phoneticsList.get(position).getText());
        holder.imageButton_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = new MediaPlayer();
                try {
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(phoneticsList.get(holder.getAdapterPosition()).getAudio());
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return phoneticsList.size();
    }
}
