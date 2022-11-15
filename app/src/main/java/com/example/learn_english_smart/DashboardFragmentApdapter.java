package com.example.learn_english_smart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.learn_english_smart.Class.Vocabulary2;
import com.example.learn_english_smart.Class.Vocabulary3;
import com.example.learn_english_smart.Class.course;
import com.example.learn_english_smart.Course.Content_course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import me.virtualiz.blurshadowimageview.BlurShadowImageView;

public class DashboardFragmentApdapter extends RecyclerView.Adapter<DashboardFragmentApdapter.MyviewHoler> {

    Context context;
    ArrayList<course> list;





    public DashboardFragmentApdapter(Context context, ArrayList<course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {





        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard,parent,false);


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
//                view.getContext().startActivity(new Intent(view.getContext(),Content_course.class));

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                String uid = user.getUid();


                FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/3000_vocabulary/Vocabulary/3000_vocabulary").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            Toast.makeText(context, "Bạn đã đăng kí kháo học này rồi", Toast.LENGTH_SHORT).show();
                        }
                        else {
                        DatabaseReference data =  FirebaseDatabase.getInstance().getReference().child("/course/3000_vocabulary/Vocabulary/3000_Vocabulary");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                        long a = dataSnapshot1.getChildrenCount();

                                    GenericTypeIndicator<List<Vocabulary3>> t = new GenericTypeIndicator<List<Vocabulary3>>() {};
                                    List<Vocabulary3> messages = dataSnapshot1.getValue(t);

                                        for (int i = 0;i<a;i++)
                                        {
                                            Vocabulary3 vocabulary3 = new Vocabulary3(messages.get(i).getWord(),messages.get(i).getImage(),messages.get(i).getMeans(),0,2.5F,i);
                                            FirebaseDatabase.getInstance().getReference("/users/"+uid+"/course/3000_vocabulary/Vocabulary/3000_vocabulary").child(String.valueOf(i)).setValue(vocabulary3);
                                        }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



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

    public static class MyviewHoler extends RecyclerView.ViewHolder{

        BlurShadowImageView course;

        public MyviewHoler(@NonNull View itemView) {
            super(itemView);

             course = itemView.findViewById(R.id.course);
        }
    }

}
