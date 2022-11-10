package com.example.learn_english_smart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.learn_english_smart.Class.course;
import com.example.learn_english_smart.Course.Content_course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.virtualiz.blurshadowimageview.BlurShadowImageView;

public class MailFragmentAdapter extends RecyclerView.Adapter<MailFragmentAdapter.MyviewHoler> {

    Context context;
    ArrayList<course> list;





    public MailFragmentAdapter(Context context, ArrayList<course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_course_fragment,parent,false);


        return new MyviewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHoler holder, int position) {
        course course1 = list.get(position);




        Glide.with(context)
                .asBitmap()
                .load(course1.getImage_course())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.course.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

        holder.course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                String uid = user.getUid();


                FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {


                            System.out.println(course1.getType());
                            Intent intent = new Intent(context,Content_course.class);
                            intent.putExtra("key1",course1.getType());
                            context.startActivity(intent);

                        }
                        else {
                            System.out.println("không");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyviewHoler extends RecyclerView.ViewHolder {

        BlurShadowImageView course;

        public MyviewHoler(@NonNull View itemView) {
            super(itemView);

            course = itemView.findViewById(R.id.course);
        }
    }
}
