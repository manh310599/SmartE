package com.example.learn_english_smart;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.crashlytics.buildtools.ndk.internal.elf.EMachine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsFragment extends Fragment {
    Context context;
    FirebaseAuth mAuth;

    TextView name,gmail;
    Button siguot;
    CircleImageView image;



    public SettingsFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        name = view.findViewById(R.id.Name);
        gmail = view.findViewById(R.id.gmail);
        siguot = view.findViewById(R.id.sigout);
        image = view.findViewById(R.id.Avatar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            String uid = user.getUid();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("users/"+uid);


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);

                    name.setText(user1.username);
                        gmail.setText(user1.email);
                Glide.with(getActivity()).load(user1.image).into(image);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        siguot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),MainActivity.class));
                requireActivity().finish();
            }
        });

        return view;
    }
}


