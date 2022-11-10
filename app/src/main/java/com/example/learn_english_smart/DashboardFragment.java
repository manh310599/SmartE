package com.example.learn_english_smart;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.fonts.Font;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learn_english_smart.Class.course;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    public DashboardFragment() {
    }



    DashboardFragmentApdapter apdapter;
    ArrayList<course> courses;


    RecyclerView listcourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_dashboard,container,false);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("tải dữ liệu");
        builder.setMessage("Xin vui lòng đợi");
        builder.setIcon(R.drawable.smarte);


        AlertDialog alert = builder.create();
        alert.show();





        listcourse = v.findViewById(R.id.course);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listcourse.setLayoutManager(linearLayoutManager);

        courses = new ArrayList<>();
        apdapter = new DashboardFragmentApdapter(getContext(),courses);

        listcourse.setAdapter(apdapter);



        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference();


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.child("course").getChildren())
                {
                    course course1 = dataSnapshot.getValue(course.class);
                    courses.add(course1);

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


}