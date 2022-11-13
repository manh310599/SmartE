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
import com.example.learn_english_smart.Class.Vocabulary2;
import com.example.learn_english_smart.Class.course;
import com.example.learn_english_smart.Course.Content_course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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







                String timestamp = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                String currentDateString = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                long currentDate = Long.parseLong(currentDateString);

                FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary/"+course1.getType()+"learn/flag").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(Objects.equals(snapshot.getValue(), timestamp))
                        {
                            Toast.makeText(context, "Bài học ngày hôm nay đã đc nạp", Toast.LENGTH_SHORT).show();
                            System.out.println(course1.getType());
                            Intent intent = new Intent(context,Content_course.class);
                            intent.putExtra("key1",course1.getType());
                            context.startActivity(intent);
                        }
                        else

                        {
                            DatabaseReference getCount  = FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary");
                            getCount.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.child(course1.getType()+"learn").exists())
                                    {
                                        DatabaseReference count = FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary/"+course1.getType()+"learn");
                                        count.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                long c = snapshot.getChildrenCount();

                                                Query database  = FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary/"+course1.getType()).orderByChild("note").startAt(c) ;
                                                database.endAt(c+19);
                                                database.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {

                                                        GenericTypeIndicator<List<Vocabulary2>> t = new GenericTypeIndicator<List<Vocabulary2>>() {};
                                                        List<Vocabulary2> messages = snapshot2.getValue(t);

                                                        FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary/"+course1.getType()+"learn/flag").setValue(timestamp);
                                                        DatabaseReference database1=  FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary/"+course1.getType()+"learn");
                                                        database1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                                                for (int i = (int) c; i<=c+19; i++)
                                                                {

                                                                    Vocabulary2 vocabulary2 = new Vocabulary2(messages.get(i).getWord(),messages.get(i).getImage(),messages.get(i).getMeans(),currentDate,messages.get(i).getEf(),messages.get(i).getNote());


                                                                    database1.child(String.valueOf(i)).setValue(vocabulary2);



                                                                }
                                                                System.out.println(course1.getType());
                                                                Intent intent = new Intent(context,Content_course.class);
                                                                intent.putExtra("key1",course1.getType());
                                                                context.startActivity(intent);

                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
//

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                    else {
                                        Query database  = FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary/"+course1.getType()).orderByChild("note").startAt(0).endAt(19);
                                        database.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                                GenericTypeIndicator<List<Vocabulary2>> t = new GenericTypeIndicator<List<Vocabulary2>>() {};
                                                List<Vocabulary2> messages = snapshot.getValue(t);

                                                FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary/"+course1.getType()+"learn/flag").setValue(timestamp);
                                                DatabaseReference database1=  FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+course1.getType()+"/Vocabulary/"+course1.getType()+"learn");
                                                database1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                                        long a = snapshot1.getChildrenCount();
                                                        for (int i = 0; i<=19; i++)
                                                        {

                                                            Vocabulary2 vocabulary2 = new Vocabulary2(messages.get(i).getWord(),messages.get(i).getImage(),messages.get(i).getMeans(),currentDate,messages.get(i).getEf(),messages.get(i).getNote());


                                                            database1.child(String.valueOf(i)).setValue(vocabulary2);



                                                        }
                                                        System.out.println(course1.getType());
                                                        Intent intent = new Intent(context,Content_course.class);
                                                        intent.putExtra("key1",course1.getType());
                                                        context.startActivity(intent);

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
//

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    System.out.println("Lỗi");
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
