package com.example.learn_english_smart;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.learn_english_smart.Class.course;

import com.example.learn_english_smart.SearchImage.API_Image;
import com.example.learn_english_smart.SearchImage.GetImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.lib.customedittext.CustomEditText;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MailFragment extends Fragment {

    public MailFragment() {
        // Required empty public constructor
    }

    MailFragmentAdapter apdapter;
    ArrayList<course> courses;

    RecyclerView listcourse;
    ImageView Null_Content;
    Button Add;


    ImageView image_add;
    Button add;
    CustomEditText name_course;
    Button add_course;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_mail,container,false);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("tải dữ liệu");
        builder.setMessage("Xin vui lòng đợi");
        builder.setIcon(R.drawable.smarte);


        AlertDialog alert = builder.create();
        alert.show();

        Add = v.findViewById(R.id.add);


        Null_Content = v.findViewById(R.id.null_content);
        listcourse = v.findViewById(R.id.content);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listcourse.setLayoutManager(linearLayoutManager);

        courses = new ArrayList<>();
        apdapter = new MailFragmentAdapter(getContext(),courses);

        listcourse.setAdapter(apdapter);
        
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCoure();
            }
        });



        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();


        myRef.addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    courses.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("/users/"+uid+"/course").getChildren())
                {
                    if (dataSnapshot.exists())
                    {
                        course course1 = dataSnapshot.getValue(course.class);
                        courses.add(course1);
                        Null_Content.setVisibility(View.GONE);
                        listcourse.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        listcourse.setVisibility(View.GONE);
                        Null_Content.setVisibility(View.VISIBLE);
                    }

                }


                apdapter.notifyDataSetChanged();
                alert.dismiss();



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;


    }

    private void addCoure() {

        Dialog alertDialog = new Dialog(getContext());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_add_course);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


         image_add = alertDialog.findViewById(R.id.image_add);
         add = alertDialog.findViewById(R.id.add);
         name_course = alertDialog.findViewById(R.id.name_course);
         add_course = alertDialog.findViewById(R.id.add_course);
         add_course.setVisibility(View.GONE);
        //////////////////////////Nút thêm hình///////////////////////////////

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name_course.getText().equals("")){
                    Toast.makeText(getContext(), "Hãy điền vào tên khóa học", Toast.LENGTH_SHORT).show();
                }
                else {
                    add_course.setVisibility(View.VISIBLE);
                    UpdateImage(name_course.getText(), image_add, add_course ,alertDialog);
                }
            }
        });



        alertDialog.show();





    }

    private void UpdateImage(String query,ImageView image,Button add_course,Dialog dialog) {
        API_Image.API_IMAGE.GET_IMAGE_CALL("30364055-ba526c17b5bca96e736c16990", query, "photo", true).enqueue(new Callback<GetImage>() {
            @Override
            public void onResponse(Call<GetImage> call, Response<GetImage> response) {
                GetImage getImage = response.body();
                if (getImage != null) {

                    try {
                        int random = ThreadLocalRandom.current().nextInt(0, 10);
                        String a = getImage.getHits().get(random).getWebformatURL();
                        System.out.println(a);
                        Picasso.get().load(getImage.getHits().get(random).getWebformatURL()).into(image);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();

                        add_course.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+query+"/image_course").setValue(a);
                                FirebaseDatabase.getInstance().getReference().child("/users/"+uid+"/course/"+query+"/type").setValue(query);
                                System.out.println("Tạo khóa học thành công");

                                dialog.dismiss();
                            }

                        });

                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), "Không có hình ảnh này", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<GetImage> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

}