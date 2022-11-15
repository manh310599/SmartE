package com.example.learn_english_smart.Course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.learn_english_smart.Class.Vocabulary2;
import com.example.learn_english_smart.Class.Vocabulary3;
import com.example.learn_english_smart.Lottie_BottomBar;
import com.example.learn_english_smart.R;
import com.google.android.gms.common.util.JsonUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lib.customedittext.CustomEditText;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class Content_course extends AppCompatActivity {



    private int progress = 0;
//    boolean check = false;
    private ProgressBar progressBar;
    private TextView textViewProgress,leveruser,WordContent,temp,check,repeat,inputText;
    private Button easy,hard_to_remember,agree,absolutely_not,add_word,consider,do_remember_but_long,perfect;
    private boolean mIsBackVisible = false;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private ImageView image,Notdata;
    private CustomEditText answer;
    View font_layout,back_layout;
    private RelativeLayout information;

    String key;
    @SuppressLint("SimpleDateFormat")

    long now = System.currentTimeMillis();

    @SuppressLint("SimpleDateFormat")


    int demNote = -1;
    int demi = 0;
    int demw = 0;
    int demm = 0;

    String aw;
    String eq;

    String mot = "1";
    String hai = "2";
    String ba = "3";
    String bon = "4";
    String nam = "5";
    String sau = "6";
    String bay = "7";
    String tam = "8";
    String chin = "9";
    String muoi = "10";
    String MuoiMot = "11";
    String MuoiHai = "12";
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        assert users != null;
        String getUid  = users.getUid();


        ///////////////////////get dữ liệu từ bên adapter////////////////////////


///////////////////////////////////////////////////////////////////////////////

        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               long a = snapshot.child("exp").getValue(Long.class);
               progress = (int) a;
               ////Hiển thị màu sắc vòng khi mới vào//////////
                progressBar.setProgress( progress);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updateProgressBar();

        readDataWordImage(new FirebaseCallbackWord() {
            @Override
            public void onCallBackWord(List<String> listw) {
                WordContent.setText(listw.get(0));
            }

            @Override
            public void onCallBackimage(List<String> listi) {
                Glide.with(Content_course.this).load(listi.get(0)).into(image);
            }

            @Override
            public void onCallBackNote(List<Long> listNote) {

            }
        });


    }



    private void lever(TextView leveruser) {




        ///////////////////get uid/////////////////////

        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                leveruser.setText("Level "+snapshot.child("lever").getValue(Long.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content_course);

        key = getIntent().getStringExtra("key1");

        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

        System.out.println(key);

        font_layout = findViewById(R.id.card_fondmain);
        back_layout = findViewById(R.id.card_backmain);



        Notdata = font_layout.findViewById(R.id.NotData);
        check = back_layout.findViewById(R.id.check);
        answer = font_layout.findViewById(R.id.answer);
        WordContent = font_layout.findViewById(R.id.content_text);
        image = font_layout.findViewById(R.id.image_content);
        temp = back_layout.findViewById(R.id.temptext);
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);

        information = font_layout.findViewById(R.id.Information);
///////////////////////////////////////////////////////////////////
        loadAnimations();
        changeCameraDistance(font_layout,back_layout);
///////////////////////Button choice////////////////////////////////////////////
        add_word = font_layout.findViewById(R.id.add_word);
        back_layout.setVisibility(View.INVISIBLE);
        absolutely_not = font_layout.findViewById(R.id.absolutely_not);
        hard_to_remember = font_layout.findViewById(R.id.hard_to_remember);
        consider = font_layout.findViewById(R.id.consider);
        do_remember_but_long = font_layout.findViewById(R.id.do_remember_but_long);
        easy = font_layout.findViewById(R.id.easy);
        perfect = font_layout.findViewById(R.id.perfect);
///////////////////////////////////////////////////////////////////////////////////


        inputText = back_layout.findViewById(R.id.input_text);
        repeat = back_layout.findViewById(R.id.repeat);
        leveruser = font_layout.findViewById(R.id.lever);
        progressBar = font_layout.findViewById(R.id.progress_bar);
        textViewProgress = font_layout.findViewById(R.id.text_view_progress);



        agree = back_layout.findViewById(R.id.agree);






        progressBar.setProgress(0);



//////////////get data lever//////////////////////////////////////////

        uid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long a = snapshot.child("lever").getValue(Long.class);
                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                lever.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        long b = snapshot1.child("lever"+a).getValue(Long.class);
                        progressBar.setMax((int) b);

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



        textViewProgress.setText("0%");
///////////////////////////////////Set các chức năng cho nút bấm/////////////////////////////////////////
        absolutely_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                repeat.setText("Lập lại sau 1p");
                ///////////////////////////////Khi click vào thay đổi thông tin hình ảnh và từ vựng////////////////////

                inputText.setText(answer.getText());

                Boolean found1;
                Boolean found2;
                Boolean found3;
                Boolean found4;
                Boolean found5;
                Boolean found6;
                Boolean found7;
                Boolean found8;
                Boolean found9;
                Boolean found10;
                Boolean found11;
                Boolean found12;

                eq = answer.getText().toString().trim().toUpperCase(Locale.ROOT);

                found10 = eq.contains(muoi);
                if (found10 == true)
                {
                    eq = eq.replace("10","mười");

                }
                found11 = eq.contains(MuoiMot);
                if (found11 == true)
                {
                    eq = eq.replace("11","mười một");

                }
                found12 = eq.contains(MuoiHai);
                if (found12 == true)
                {
                    eq = eq.replace("12","mười hai");

                }

                found1 = eq.contains(mot);
                if (found1 == true)
                {
                    eq = eq.replace("1","một");

                }

                found2 = eq.contains(hai);
                if (found2 == true)
                {
                    eq = eq.replace("2","hai");

                }
                found3 = eq.contains(ba);
                if (found3 == true)
                {
                    eq = eq.replace("3","ba");

                }
                found4 = eq.contains(bon);
                if (found4 == true)
                {
                    eq = eq.replace("4","tư");

                }
                found5 = eq.contains(nam);
                if (found5 == true)
                {
                    eq = eq.replace("5","năm");

                }
                found6 = eq.contains(sau);
                if (found6 == true)
                {
                    eq = eq.replace("6","sáu");

                }
                found7 = eq.contains(bay);
                if (found7 == true)
                {
                    eq = eq.replace("7","bảy");

                }
                found8 = eq.contains(tam);
                if (found8 == true)
                {
                    eq = eq.replace("8","tám");

                }
                found9 = eq.contains(chin);
                if (found9 == true)
                {
                    eq = eq.replace("9","chín");

                }

                eq = eq.toUpperCase(Locale.ROOT);

                readDataMean(new FirebaseCallbackMean() {

                    @Override
                    public void onCallBack(List<String> list) {

                        temp.setText(list.get(demm));


                        boolean found1;
                        boolean found2;
                        boolean found3;
                        boolean found4;
                        boolean found5;
                        boolean found6;
                        boolean found7;
                        boolean found8;
                        boolean found9;
                        boolean found10;
                        boolean found11;
                        boolean found12;

                        String getMean =     WordContent.getText().toString();
                        aw = temp.getText().toString().trim().toUpperCase(Locale.ROOT);

                        found10 = aw.contains(muoi);
                        if (found10 == true)
                        {
                            aw = aw.replace("10","mười");

                        }
                        found11 = aw.contains(MuoiMot);
                        if (found11 == true)
                        {
                            aw = aw.replace("11","mười một");

                        }
                        found12 = aw.contains(MuoiHai);
                        if (found12 == true)
                        {
                            aw = aw.replace("12","mười hai");

                        }

                        found1 = aw.contains(mot);
                        if (found1 == true)
                        {
                            aw = aw.replace("1","một");

                        }

                        found2 = aw.contains(hai);
                        if (found2 == true)
                        {
                            aw = aw.replace("2","hai");

                        }
                        found3 = aw.contains(ba);
                        if (found3 == true)
                        {
                            aw = aw.replace("3","ba");

                        }
                        found4 = aw.contains(bon);
                        if (found4 == true)
                        {
                            aw = aw.replace("4","tư");

                        }
                        found5 = aw.contains(nam);
                        if (found5 == true)
                        {
                            aw = aw.replace("5","năm");

                        }
                        found6 = aw.contains(sau);
                        if (found6 == true)
                        {
                            aw = aw.replace("6","sáu");

                        }
                        found7 = aw.contains(bay);
                        if (found7 == true)
                        {
                            aw = aw.replace("7","bảy");

                        }
                        found8 = aw.contains(tam);
                        if (found8 == true)
                        {
                            aw = aw.replace("8","tám");

                        }
                        found9 = aw.contains(chin);
                        if (found9 == true)
                        {
                            aw = aw.replace("9","chín");

                        }

                        aw = aw.toUpperCase(Locale.ROOT);


                        if (eq.equals(aw))
                        {
                            check.setText("Đáp của bạn đã đúng");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen2));
                        }
                        else {
                            check.setText("Đáp án bạn chọn đã sai");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen1));
                        }

                        ///////////////////////////////chỉnh sửa từ vựng để chuẩn bị đưa vào đường link đọc file âm thanh///////////////////////////////////
                        getMean = getMean.replace(" ","%20");

                        String text = "https://translate.google.com/translate_tts?ie=UTF-8&q="+getMean+"&tl=en&total=1&idx=0&textlen=12&tk=372388.179982&client=t";

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(text);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        agree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ok(font_layout,back_layout);



                                temp.setText(list.get(demm));

                                readDataMean(new FirebaseCallbackMean() {
                                    @Override
                                    public void onCallBack(List<String> list) {
                                        if (list.size()  > demm)
                                        {
                                            demm++;
                                        }
                                        if (list.size() == demi)
                                        {
                                            demm = 0;
                                        }
                                    }
                                });

                                answer.setText("");
                            }

                        });




                    }


                });
                long time_count = 100000;
                Equal(0);
                onClickabsolutely_not(font_layout,back_layout);
            }



        });


        hard_to_remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ///////////////////////////////Khi click vào thay đổi thông tin hình ảnh và từ vựng////////////////////

                repeat.setText("Lập lại sau 6 phút");
                inputText.setText(answer.getText());

                Boolean found1;
                Boolean found2;
                Boolean found3;
                Boolean found4;
                Boolean found5;
                Boolean found6;
                Boolean found7;
                Boolean found8;
                Boolean found9;
                Boolean found10;
                Boolean found11;
                Boolean found12;

                eq = answer.getText().toString().trim().toUpperCase(Locale.ROOT);

                found10 = eq.contains(muoi);
                if (found10 == true)
                {
                    eq = eq.replace("10","mười");

                }
                found11 = eq.contains(MuoiMot);
                if (found11 == true)
                {
                    eq = eq.replace("11","mười một");

                }
                found12 = eq.contains(MuoiHai);
                if (found12 == true)
                {
                    eq = eq.replace("12","mười hai");

                }

                found1 = eq.contains(mot);
                if (found1 == true)
                {
                    eq = eq.replace("1","một");

                }

                found2 = eq.contains(hai);
                if (found2 == true)
                {
                    eq = eq.replace("2","hai");

                }
                found3 = eq.contains(ba);
                if (found3 == true)
                {
                    eq = eq.replace("3","ba");

                }
                found4 = eq.contains(bon);
                if (found4 == true)
                {
                    eq = eq.replace("4","tư");

                }
                found5 = eq.contains(nam);
                if (found5 == true)
                {
                    eq = eq.replace("5","năm");

                }
                found6 = eq.contains(sau);
                if (found6 == true)
                {
                    eq = eq.replace("6","sáu");

                }
                found7 = eq.contains(bay);
                if (found7 == true)
                {
                    eq = eq.replace("7","bảy");

                }
                found8 = eq.contains(tam);
                if (found8 == true)
                {
                    eq = eq.replace("8","tám");

                }
                found9 = eq.contains(chin);
                if (found9 == true)
                {
                    eq = eq.replace("9","chín");

                }

                eq = eq.toUpperCase(Locale.ROOT);

                readDataMean(new FirebaseCallbackMean() {

                    @Override
                    public void onCallBack(List<String> list) {

                        temp.setText(list.get(demm));

                        boolean found1;
                        boolean found2;
                        boolean found3;
                        boolean found4;
                        boolean found5;
                        boolean found6;
                        boolean found7;
                        boolean found8;
                        boolean found9;
                        boolean found10;
                        boolean found11;
                        boolean found12;

                        String getMean = WordContent.getText().toString();
                        aw = temp.getText().toString().trim().toUpperCase(Locale.ROOT);

                        found10 = aw.contains(muoi);
                        if (found10 == true)
                        {
                            aw = aw.replace("10","mười");

                        }
                        found11 = aw.contains(MuoiMot);
                        if (found11 == true)
                        {
                            aw = aw.replace("11","mười một");

                        }
                        found12 = aw.contains(MuoiHai);
                        if (found12 == true)
                        {
                            aw = aw.replace("12","mười hai");

                        }

                        found1 = aw.contains(mot);
                        if (found1 == true)
                        {
                            aw = aw.replace("1","một");

                        }

                        found2 = aw.contains(hai);
                        if (found2 == true)
                        {
                            aw = aw.replace("2","hai");

                        }
                        found3 = aw.contains(ba);
                        if (found3 == true)
                        {
                            aw = aw.replace("3","ba");

                        }
                        found4 = aw.contains(bon);
                        if (found4 == true)
                        {
                            aw = aw.replace("4","tư");

                        }
                        found5 = aw.contains(nam);
                        if (found5 == true)
                        {
                            aw = aw.replace("5","năm");

                        }
                        found6 = aw.contains(sau);
                        if (found6 == true)
                        {
                            aw = aw.replace("6","sáu");

                        }
                        found7 = aw.contains(bay);
                        if (found7 == true)
                        {
                            aw = aw.replace("7","bảy");

                        }
                        found8 = aw.contains(tam);
                        if (found8 == true)
                        {
                            aw = aw.replace("8","tám");

                        }
                        found9 = aw.contains(chin);
                        if (found9 == true)
                        {
                            aw = aw.replace("9","chín");

                        }

                        aw = aw.toUpperCase(Locale.ROOT);


                        if (eq.equals(aw))
                        {
                            check.setText("Đáp của bạn đã đúng");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen2));
                        }
                        else {
                            check.setText("Đáp án bạn chọn đã sai");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen1));
                        }

                        ///////////////////////////////chỉnh sửa từ vựng để chuẩn bị đưa vào đường link đọc file âm thanh///////////////////////////////////
                        getMean = getMean.replace(" ","%20");

                        String text = "https://translate.google.com/translate_tts?ie=UTF-8&q="+getMean+"&tl=en&total=1&idx=0&textlen=12&tk=372388.179982&client=t";

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(text);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                        agree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ok(font_layout,back_layout);



                                temp.setText(list.get(demm));
                                readDataMean(new FirebaseCallbackMean() {
                                    @Override
                                    public void onCallBack(List<String> list) {
                                        if (list.size()  > demm)
                                        {
                                            demm++;
                                        }
                                        if (list.size() == demm)
                                        {
                                            demm = 0;
                                        }
                                    }
                                });

                                answer.setText("");
                            }

                        });




                    }


                });
                int time_count = 600000;
                Equal(1);
                onClickUpHardtoremember(font_layout,back_layout);
            }



        });


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ///////////////////////////////Khi click vào thay đổi thông tin hình ảnh và từ vựng////////////////////
                inputText.setText(answer.getText());
                repeat.setText("Lập lại sau 3 ngày");


                Boolean found1;
                Boolean found2;
                Boolean found3;
                Boolean found4;
                Boolean found5;
                Boolean found6;
                Boolean found7;
                Boolean found8;
                Boolean found9;
                Boolean found10;
                Boolean found11;
                Boolean found12;

                eq = answer.getText().toString().trim().toUpperCase(Locale.ROOT);

                found10 = eq.contains(muoi);
                if (found10 == true)
                {
                    eq = eq.replace("10","mười");

                }
                found11 = eq.contains(MuoiMot);
                if (found11 == true)
                {
                    eq = eq.replace("11","mười một");

                }
                found12 = eq.contains(MuoiHai);
                if (found12 == true)
                {
                    eq = eq.replace("12","mười hai");

                }

                found1 = eq.contains(mot);
                if (found1 == true)
                {
                    eq = eq.replace("1","một");

                }

                found2 = eq.contains(hai);
                if (found2 == true)
                {
                    eq = eq.replace("2","hai");

                }
                found3 = eq.contains(ba);
                if (found3 == true)
                {
                    eq = eq.replace("3","ba");

                }
                found4 = eq.contains(bon);
                if (found4 == true)
                {
                    eq = eq.replace("4","tư");

                }
                found5 = eq.contains(nam);
                if (found5 == true)
                {
                    eq = eq.replace("5","năm");

                }
                found6 = eq.contains(sau);
                if (found6 == true)
                {
                    eq = eq.replace("6","sáu");

                }
                found7 = eq.contains(bay);
                if (found7 == true)
                {
                    eq = eq.replace("7","bảy");

                }
                found8 = eq.contains(tam);
                if (found8 == true)
                {
                    eq = eq.replace("8","tám");

                }
                found9 = eq.contains(chin);
                if (found9 == true)
                {
                    eq = eq.replace("9","chín");

                }

                eq = eq.toUpperCase(Locale.ROOT);

                readDataMean(new FirebaseCallbackMean() {

                    @Override
                    public void onCallBack(List<String> list) {

                        temp.setText(list.get(demm));

                        boolean found1;
                        boolean found2;
                        boolean found3;
                        boolean found4;
                        boolean found5;
                        boolean found6;
                        boolean found7;
                        boolean found8;
                        boolean found9;
                        boolean found10;
                        boolean found11;
                        boolean found12;

                        String getMean = WordContent.getText().toString();
                        aw = temp.getText().toString().trim().toUpperCase(Locale.ROOT);

                        found10 = aw.contains(muoi);
                        if (found10 == true)
                        {
                            aw = aw.replace("10","mười");

                        }
                        found11 = aw.contains(MuoiMot);
                        if (found11 == true)
                        {
                            aw = aw.replace("11","mười một");

                        }
                        found12 = aw.contains(MuoiHai);
                        if (found12 == true)
                        {
                            aw = aw.replace("12","mười hai");

                        }

                        found1 = aw.contains(mot);
                        if (found1 == true)
                        {
                            aw = aw.replace("1","một");

                        }

                        found2 = aw.contains(hai);
                        if (found2 == true)
                        {
                            aw = aw.replace("2","hai");

                        }
                        found3 = aw.contains(ba);
                        if (found3 == true)
                        {
                            aw = aw.replace("3","ba");

                        }
                        found4 = aw.contains(bon);
                        if (found4 == true)
                        {
                            aw = aw.replace("4","tư");

                        }
                        found5 = aw.contains(nam);
                        if (found5 == true)
                        {
                            aw = aw.replace("5","năm");

                        }
                        found6 = aw.contains(sau);
                        if (found6 == true)
                        {
                            aw = aw.replace("6","sáu");

                        }
                        found7 = aw.contains(bay);
                        if (found7 == true)
                        {
                            aw = aw.replace("7","bảy");

                        }
                        found8 = aw.contains(tam);
                        if (found8 == true)
                        {
                            aw = aw.replace("8","tám");

                        }
                        found9 = aw.contains(chin);
                        if (found9 == true)
                        {
                            aw = aw.replace("9","chín");

                        }

                        aw = aw.toUpperCase(Locale.ROOT);


                        if (eq.equals(aw))
                        {
                            check.setText("Đáp của bạn đã đúng");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen2));
                        }
                        else {
                            check.setText("Đáp án bạn chọn đã sai");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen1));
                        }

                        ///////////////////////////////chỉnh sửa từ vựng để chuẩn bị đưa vào đường link đọc file âm thanh///////////////////////////////////
                        getMean = getMean.replace(" ","%20");

                        String text = "https://translate.google.com/translate_tts?ie=UTF-8&q="+getMean+"&tl=en&total=1&idx=0&textlen=12&tk=372388.179982&client=t";

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(text);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                        agree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ok(font_layout,back_layout);



                                temp.setText(list.get(demm));
                                readDataMean(new FirebaseCallbackMean() {
                                    @Override
                                    public void onCallBack(List<String> list) {
                                        if (list.size()  > demm)
                                        {
                                            demm++;
                                        }
                                        if (list.size() == demi)
                                        {
                                            demm = 0;
                                        }
                                    }
                                });


                                answer.setText("");
                            }

                        });




                    }


                });
                int time_count = 1000000;
                Equal(4);
                onClickUpEasy(font_layout,back_layout);
            }



        });

        perfect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repeat.setText("Lập lại sau 6 ngày");
                inputText.setText(answer.getText());
                ///////////////////////////////Khi click vào thay đổi thông tin hình ảnh và từ vựng////////////////////



                Boolean found1;
                Boolean found2;
                Boolean found3;
                Boolean found4;
                Boolean found5;
                Boolean found6;
                Boolean found7;
                Boolean found8;
                Boolean found9;
                Boolean found10;
                Boolean found11;
                Boolean found12;

                eq = answer.getText().toString().trim().toUpperCase(Locale.ROOT);

                found10 = eq.contains(muoi);
                if (found10 == true)
                {
                    eq = eq.replace("10","mười");

                }
                found11 = eq.contains(MuoiMot);
                if (found11 == true)
                {
                    eq = eq.replace("11","mười một");

                }
                found12 = eq.contains(MuoiHai);
                if (found12 == true)
                {
                    eq = eq.replace("12","mười hai");

                }

                found1 = eq.contains(mot);
                if (found1 == true)
                {
                    eq = eq.replace("1","một");

                }

                found2 = eq.contains(hai);
                if (found2 == true)
                {
                    eq = eq.replace("2","hai");

                }
                found3 = eq.contains(ba);
                if (found3 == true)
                {
                    eq = eq.replace("3","ba");

                }
                found4 = eq.contains(bon);
                if (found4 == true)
                {
                    eq = eq.replace("4","tư");

                }
                found5 = eq.contains(nam);
                if (found5 == true)
                {
                    eq = eq.replace("5","năm");

                }
                found6 = eq.contains(sau);
                if (found6 == true)
                {
                    eq = eq.replace("6","sáu");

                }
                found7 = eq.contains(bay);
                if (found7 == true)
                {
                    eq = eq.replace("7","bảy");

                }
                found8 = eq.contains(tam);
                if (found8 == true)
                {
                    eq = eq.replace("8","tám");

                }
                found9 = eq.contains(chin);
                if (found9 == true)
                {
                    eq = eq.replace("9","chín");

                }

                eq = eq.toUpperCase(Locale.ROOT);

                readDataMean(new FirebaseCallbackMean() {

                    @Override
                    public void onCallBack(List<String> list) {

                        temp.setText(list.get(demm));

                        boolean found1;
                        boolean found2;
                        boolean found3;
                        boolean found4;
                        boolean found5;
                        boolean found6;
                        boolean found7;
                        boolean found8;
                        boolean found9;
                        boolean found10;
                        boolean found11;
                        boolean found12;

                        String getMean = WordContent.getText().toString();
                        aw = temp.getText().toString().trim().toUpperCase(Locale.ROOT);

                        found10 = aw.contains(muoi);
                        if (found10 == true)
                        {
                            aw = aw.replace("10","mười");

                        }
                        found11 = aw.contains(MuoiMot);
                        if (found11 == true)
                        {
                            aw = aw.replace("11","mười một");

                        }
                        found12 = aw.contains(MuoiHai);
                        if (found12 == true)
                        {
                            aw = aw.replace("12","mười hai");

                        }

                        found1 = aw.contains(mot);
                        if (found1 == true)
                        {
                            aw = aw.replace("1","một");

                        }

                        found2 = aw.contains(hai);
                        if (found2 == true)
                        {
                            aw = aw.replace("2","hai");

                        }
                        found3 = aw.contains(ba);
                        if (found3 == true)
                        {
                            aw = aw.replace("3","ba");

                        }
                        found4 = aw.contains(bon);
                        if (found4 == true)
                        {
                            aw = aw.replace("4","tư");

                        }
                        found5 = aw.contains(nam);
                        if (found5 == true)
                        {
                            aw = aw.replace("5","năm");

                        }
                        found6 = aw.contains(sau);
                        if (found6 == true)
                        {
                            aw = aw.replace("6","sáu");

                        }
                        found7 = aw.contains(bay);
                        if (found7 == true)
                        {
                            aw = aw.replace("7","bảy");

                        }
                        found8 = aw.contains(tam);
                        if (found8 == true)
                        {
                            aw = aw.replace("8","tám");

                        }
                        found9 = aw.contains(chin);
                        if (found9 == true)
                        {
                            aw = aw.replace("9","chín");

                        }

                        aw = aw.toUpperCase(Locale.ROOT);


                        if (eq.equals(aw))
                        {
                            check.setText("Đáp của bạn đã đúng");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen2));
                        }
                        else {
                            check.setText("Đáp án bạn chọn đã sai");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen1));
                        }

                        ///////////////////////////////chỉnh sửa từ vựng để chuẩn bị đưa vào đường link đọc file âm thanh///////////////////////////////////
                        getMean = getMean.replace(" ","%20");

                        String text = "https://translate.google.com/translate_tts?ie=UTF-8&q="+getMean+"&tl=en&total=1&idx=0&textlen=12&tk=372388.179982&client=t";

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(text);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                        agree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ok(font_layout,back_layout);



                                temp.setText(list.get(demm));
                                readDataMean(new FirebaseCallbackMean() {
                                    @Override
                                    public void onCallBack(List<String> list) {
                                        if (list.size()  > demm)
                                        {
                                            demm++;
                                        }
                                        if (list.size() == demi)
                                        {
                                            demm = 0;
                                        }
                                    }
                                });


                                answer.setText("");
                            }

                        });




                    }


                });
                Equal(5);
                onClickUpperfect(font_layout,back_layout);
            }



        });

        consider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                repeat.setText("Lập lại sau 10p");
                ///////////////////////////////Khi click vào thay đổi thông tin hình ảnh và từ vựng////////////////////
                inputText.setText(answer.getText());


                Boolean found1;
                Boolean found2;
                Boolean found3;
                Boolean found4;
                Boolean found5;
                Boolean found6;
                Boolean found7;
                Boolean found8;
                Boolean found9;
                Boolean found10;
                Boolean found11;
                Boolean found12;

                eq = answer.getText().toString().trim().toUpperCase(Locale.ROOT);

                found10 = eq.contains(muoi);
                if (found10 == true)
                {
                    eq = eq.replace("10","mười");

                }
                found11 = eq.contains(MuoiMot);
                if (found11 == true)
                {
                    eq = eq.replace("11","mười một");

                }
                found12 = eq.contains(MuoiHai);
                if (found12 == true)
                {
                    eq = eq.replace("12","mười hai");

                }

                found1 = eq.contains(mot);
                if (found1 == true)
                {
                    eq = eq.replace("1","một");

                }

                found2 = eq.contains(hai);
                if (found2 == true)
                {
                    eq = eq.replace("2","hai");

                }
                found3 = eq.contains(ba);
                if (found3 == true)
                {
                    eq = eq.replace("3","ba");

                }
                found4 = eq.contains(bon);
                if (found4 == true)
                {
                    eq = eq.replace("4","tư");

                }
                found5 = eq.contains(nam);
                if (found5 == true)
                {
                    eq = eq.replace("5","năm");

                }
                found6 = eq.contains(sau);
                if (found6 == true)
                {
                    eq = eq.replace("6","sáu");

                }
                found7 = eq.contains(bay);
                if (found7 == true)
                {
                    eq = eq.replace("7","bảy");

                }
                found8 = eq.contains(tam);
                if (found8 == true)
                {
                    eq = eq.replace("8","tám");

                }
                found9 = eq.contains(chin);
                if (found9 == true)
                {
                    eq = eq.replace("9","chín");

                }

                eq = eq.toUpperCase(Locale.ROOT);

                readDataMean(new FirebaseCallbackMean() {

                    @Override
                    public void onCallBack(List<String> list) {

                        temp.setText(list.get(demm));

                        boolean found1;
                        boolean found2;
                        boolean found3;
                        boolean found4;
                        boolean found5;
                        boolean found6;
                        boolean found7;
                        boolean found8;
                        boolean found9;
                        boolean found10;
                        boolean found11;
                        boolean found12;

                        String getMean = WordContent.getText().toString();
                        aw = temp.getText().toString().trim().toUpperCase(Locale.ROOT);

                        found10 = aw.contains(muoi);
                        if (found10 == true)
                        {
                            aw = aw.replace("10","mười");

                        }
                        found11 = aw.contains(MuoiMot);
                        if (found11 == true)
                        {
                            aw = aw.replace("11","mười một");

                        }
                        found12 = aw.contains(MuoiHai);
                        if (found12 == true)
                        {
                            aw = aw.replace("12","mười hai");

                        }

                        found1 = aw.contains(mot);
                        if (found1 == true)
                        {
                            aw = aw.replace("1","một");

                        }

                        found2 = aw.contains(hai);
                        if (found2 == true)
                        {
                            aw = aw.replace("2","hai");

                        }
                        found3 = aw.contains(ba);
                        if (found3 == true)
                        {
                            aw = aw.replace("3","ba");

                        }
                        found4 = aw.contains(bon);
                        if (found4 == true)
                        {
                            aw = aw.replace("4","tư");

                        }
                        found5 = aw.contains(nam);
                        if (found5 == true)
                        {
                            aw = aw.replace("5","năm");

                        }
                        found6 = aw.contains(sau);
                        if (found6 == true)
                        {
                            aw = aw.replace("6","sáu");

                        }
                        found7 = aw.contains(bay);
                        if (found7 == true)
                        {
                            aw = aw.replace("7","bảy");

                        }
                        found8 = aw.contains(tam);
                        if (found8 == true)
                        {
                            aw = aw.replace("8","tám");

                        }
                        found9 = aw.contains(chin);
                        if (found9 == true)
                        {
                            aw = aw.replace("9","chín");

                        }

                        aw = aw.toUpperCase(Locale.ROOT);


                        if (eq.equals(aw))
                        {
                            check.setText("Đáp của bạn đã đúng");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen2));
                        }
                        else {
                            check.setText("Đáp án bạn chọn đã sai");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen1));
                        }

                        ///////////////////////////////chỉnh sửa từ vựng để chuẩn bị đưa vào đường link đọc file âm thanh///////////////////////////////////
                        getMean = getMean.replace(" ","%20");

                        String text = "https://translate.google.com/translate_tts?ie=UTF-8&q="+getMean+"&tl=en&total=1&idx=0&textlen=12&tk=372388.179982&client=t";

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(text);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                        agree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ok(font_layout,back_layout);



                                temp.setText(list.get(demm));
                                readDataMean(new FirebaseCallbackMean() {
                                    @Override
                                    public void onCallBack(List<String> list) {
                                        if (list.size()  > demm)
                                        {
                                            demm++;
                                        }
                                        if (list.size() == demi)
                                        {
                                            demm = 0;
                                        }
                                    }
                                });


                                answer.setText("");
                            }

                        });




                    }


                });
                Equal(2);
                onClickconsider(font_layout,back_layout);
            }



        });

        do_remember_but_long.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repeat.setText("Lập lai sau 1 ngày");
                inputText.setText(answer.getText());
                ///////////////////////////////Khi click vào thay đổi thông tin hình ảnh và từ vựng////////////////////



                Boolean found1;
                Boolean found2;
                Boolean found3;
                Boolean found4;
                Boolean found5;
                Boolean found6;
                Boolean found7;
                Boolean found8;
                Boolean found9;
                Boolean found10;
                Boolean found11;
                Boolean found12;

                eq = answer.getText().toString().trim().toUpperCase(Locale.ROOT);

                found10 = eq.contains(muoi);
                if (found10 == true)
                {
                    eq = eq.replace("10","mười");

                }
                found11 = eq.contains(MuoiMot);
                if (found11 == true)
                {
                    eq = eq.replace("11","mười một");

                }
                found12 = eq.contains(MuoiHai);
                if (found12 == true)
                {
                    eq = eq.replace("12","mười hai");

                }

                found1 = eq.contains(mot);
                if (found1 == true)
                {
                    eq = eq.replace("1","một");

                }

                found2 = eq.contains(hai);
                if (found2 == true)
                {
                    eq = eq.replace("2","hai");

                }
                found3 = eq.contains(ba);
                if (found3 == true)
                {
                    eq = eq.replace("3","ba");

                }
                found4 = eq.contains(bon);
                if (found4 == true)
                {
                    eq = eq.replace("4","tư");

                }
                found5 = eq.contains(nam);
                if (found5 == true)
                {
                    eq = eq.replace("5","năm");

                }
                found6 = eq.contains(sau);
                if (found6 == true)
                {
                    eq = eq.replace("6","sáu");

                }
                found7 = eq.contains(bay);
                if (found7 == true)
                {
                    eq = eq.replace("7","bảy");

                }
                found8 = eq.contains(tam);
                if (found8 == true)
                {
                    eq = eq.replace("8","tám");

                }
                found9 = eq.contains(chin);
                if (found9 == true)
                {
                    eq = eq.replace("9","chín");

                }

                eq = eq.toUpperCase(Locale.ROOT);

                readDataMean(new FirebaseCallbackMean() {

                    @Override
                    public void onCallBack(List<String> list) {

                        temp.setText(list.get(demm));

                        boolean found1;
                        boolean found2;
                        boolean found3;
                        boolean found4;
                        boolean found5;
                        boolean found6;
                        boolean found7;
                        boolean found8;
                        boolean found9;
                        boolean found10;
                        boolean found11;
                        boolean found12;

                        String getMean = WordContent.getText().toString();
                        aw = temp.getText().toString().trim().toUpperCase(Locale.ROOT);

                        found10 = aw.contains(muoi);
                        if (found10 == true)
                        {
                            aw = aw.replace("10","mười");

                        }
                        found11 = aw.contains(MuoiMot);
                        if (found11 == true)
                        {
                            aw = aw.replace("11","mười một");

                        }
                        found12 = aw.contains(MuoiHai);
                        if (found12 == true)
                        {
                            aw = aw.replace("12","mười hai");

                        }

                        found1 = aw.contains(mot);
                        if (found1 == true)
                        {
                            aw = aw.replace("1","một");

                        }

                        found2 = aw.contains(hai);
                        if (found2 == true)
                        {
                            aw = aw.replace("2","hai");

                        }
                        found3 = aw.contains(ba);
                        if (found3 == true)
                        {
                            aw = aw.replace("3","ba");

                        }
                        found4 = aw.contains(bon);
                        if (found4 == true)
                        {
                            aw = aw.replace("4","tư");

                        }
                        found5 = aw.contains(nam);
                        if (found5 == true)
                        {
                            aw = aw.replace("5","năm");

                        }
                        found6 = aw.contains(sau);
                        if (found6 == true)
                        {
                            aw = aw.replace("6","sáu");

                        }
                        found7 = aw.contains(bay);
                        if (found7 == true)
                        {
                            aw = aw.replace("7","bảy");

                        }
                        found8 = aw.contains(tam);
                        if (found8 == true)
                        {
                            aw = aw.replace("8","tám");

                        }
                        found9 = aw.contains(chin);
                        if (found9 == true)
                        {
                            aw = aw.replace("9","chín");

                        }

                        aw = aw.toUpperCase(Locale.ROOT);


                        if (eq.equals(aw))
                        {
                            check.setText("Đáp của bạn đã đúng");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen2));
                        }
                        else {
                            check.setText("Đáp án bạn chọn đã sai");
                            check.setTextColor(getResources().getColor(R.color.dot_dark_screen1));
                        }

                        ///////////////////////////////chỉnh sửa từ vựng để chuẩn bị đưa vào đường link đọc file âm thanh///////////////////////////////////
                        getMean = getMean.replace(" ","%20");

                        String text = "https://translate.google.com/translate_tts?ie=UTF-8&q="+getMean+"&tl=en&total=1&idx=0&textlen=12&tk=372388.179982&client=t";

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(text);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                        agree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ok(font_layout,back_layout);



                                temp.setText(list.get(demm));
                                readDataMean(new FirebaseCallbackMean() {
                                    @Override
                                    public void onCallBack(List<String> list) {
                                        if (list.size()  > demm)
                                        {
                                            demm++;
                                        }
                                        if (list.size() == demi)
                                        {
                                            demm = 0;
                                        }
                                    }
                                });


                                answer.setText("");
                            }

                        });




                    }


                });
                Equal(3);
                onClickDo_remember_but_long(font_layout,back_layout);
            }



        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        readDataMean(new FirebaseCallbackMean() {

            @Override
            public void onCallBack(List<String> list) {





                agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ok(font_layout,back_layout);


                        temp.setText(list.get(demm));
                        readDataMean(new FirebaseCallbackMean() {
                            @Override
                            public void onCallBack(List<String> list) {
                                if (list.size()  > demm)
                                {
                                    demm++;
                                }
                                if (list.size() == demi)
                                {
                                    demm = 0;
                                }
                            }
                        });


                        answer.setText("");
                    }

                });




            }


        });






        lever(leveruser);



    }

    private void  Equal(int quality) {


        readDataWordImage(new FirebaseCallbackWord() {

            @Override
            public void onCallBackWord(List<String> listw) {


               

                if (listw.size() > demw) {

                    demw++;

                }
                if (listw.size()  == demw)
                {
                    demw = 0;
                }


                WordContent.setText(listw.get(demw));


                System.out.println(listw.size());
                System.out.println(demw);







            }

            @Override
            public void onCallBackimage(List<String> listi) {

                if (listi.size() > demi) {

                    demi++;

                }

                if (listi.size()  == demi)
                {
                    demi = 0;
                }
                Glide.with(Content_course.this).load(listi.get(demi)).into(image);








            }





            @Override
            public void onCallBackNote(List<Long> listNote) {

                if (listNote.size() > demNote) {

                    demNote++;

                }

                if (listNote.size()  == demNote)
                {
                    demNote = 0;
                }
                FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                String getUid  = users.getUid();

           FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").orderByChild("note").equalTo(listNote.get(demNote)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            Vocabulary2 vocabulary2 = dataSnapshot.getValue(Vocabulary2.class);

                            Vocabulary2 vocabulary = new Vocabulary2();
                            // retrieve the stored values (default values if new cards)
                            long repetitions = vocabulary2.getRepetitions();
                            float easiness = vocabulary2.getEf();
                            long interval = vocabulary2.getInterval();

                            // easiness factor
                            easiness = (float) Math.max(1.3, easiness + 0.1 - (5.0 - quality) * (0.08 + (5.0 - quality) * 0.02));
                            vocabulary.setEf(easiness);
                            FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").child(String.valueOf(listNote.get(demNote))).updateChildren(vocabulary.toMap3());


                            // repetitions
                            if (quality < 3) {

                                vocabulary.setRepetitions(0);
                                FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").child(String.valueOf(listNote.get(demNote))).updateChildren(vocabulary.toMap());


                            } else {
                                vocabulary.setRepetitions(vocabulary2.getRepetitions()+1);
                                FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").child(String.valueOf(listNote.get(demNote))).updateChildren(vocabulary.toMap());

                                if (repetitions <= 1) {
                                    interval = 1;
                                    vocabulary.setInterval(1);
                                    FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").child(String.valueOf(listNote.get(demNote))).updateChildren(vocabulary.toMap1());

                                } else if (repetitions == 2) {
                                    interval = 6;
                                    vocabulary.setInterval(6);
                                    FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").child(String.valueOf(listNote.get(demNote))).updateChildren(vocabulary.toMap1());
                                } else {
                                    interval = Math.round(interval * easiness);
                                    vocabulary.setInterval(interval);
                                    FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").child(String.valueOf(listNote.get(demNote))).updateChildren(vocabulary.toMap1());
                                }

                                // next practice
                                int millisecondsInDay = 60 * 60 * 24 * 1000;

                                long nextPracticeDate = now + millisecondsInDay*interval;
                                vocabulary.setTemporary_time(nextPracticeDate);
                                FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").child(String.valueOf(listNote.get(demNote))).updateChildren(vocabulary.toMap2());
                                long now = System.currentTimeMillis();
                                Toast.makeText(Content_course.this, "test đếm note: "+now, Toast.LENGTH_SHORT).show();;
                            }

                            // interval




                            // Create a calendar object that will convert the date and time value in milliseconds to date.

//                            int notifi = (int) (millisecondsInDay/1000/60/60*interval);
//                            if (notifi>=24)
//                            {
//                                int t = notifi/24;
//                                int e = notifi%24;
//                                Toast.makeText(Content_course.this, "Lặp lại sau: "+t+" ngày "+e+" giờ", Toast.LENGTH_SHORT).show();
//                            }
//                            repeat.setText(dateFormatted);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }


        });

    }








    private void readDataWordImage(FirebaseCallbackWord firebaseCallback)
    {

        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();













                    final List<Vocabulary2> g = new ArrayList<>();
                    final List<String> word = new ArrayList<>();
                    final List<String> image = new ArrayList<>();
                    final List<Long> note = new ArrayList<>();

      Query query =   FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").orderByChild("temporary_time").startAt(0);
      query.endAt(now).addListenerForSingleValueEvent(new ValueEventListener() {
                        int  i ;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            word.clear();
                            image.clear();
                            note.clear();
                            long tam = snapshot.getChildrenCount();
                            if (snapshot.getChildrenCount()>0) {
                                try {
                                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                                    {
                                        Vocabulary2 vocabulary2 = dataSnapshot.getValue(Vocabulary2.class);
                                        g.add(vocabulary2);
                                    }

                                    for (int i = 0;i<tam;i++)
                                    {
                                        word.add(g.get(i).getWord());
                                        image.add(g.get(i).getImage());
                                        note.add(g.get(i).getNote());
                                    }

                                    firebaseCallback.onCallBackWord(word);
                                    firebaseCallback.onCallBackimage(image);
                                    firebaseCallback.onCallBackNote(note);
                                } catch (Exception e) {
                                    System.out.println(e);

                                }
                            }
                            else {
                                Notdata.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(Content_course.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });




    }



    private void readDataMean(FirebaseCallbackMean firebaseCallback)
    {

        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();






                    final List<Vocabulary2> mean = new ArrayList<>();

       FirebaseDatabase.getInstance().getReference().child("/users/"+getUid+"/course/"+key+"/Vocabulary/"+key+"learn").orderByChild("temporary_time").startAt(0).endAt(now).addListenerForSingleValueEvent(new ValueEventListener() {
                        int  i ;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mean.clear();
                            if (snapshot.getChildrenCount()>0  )
                            {

                                    long tam = snapshot.getChildrenCount();



                              try {
                                  for (DataSnapshot beaconSnapshot: snapshot.getChildren()) {
                                   Vocabulary2 mBeacon = beaconSnapshot.getValue(Vocabulary2.class);
                                    mean.add(mBeacon);
                                    }
                                   System.out.println(tam);
                                    List<String> readmean = new ArrayList<>();
                                    for (int i = 0;i<tam;i++)
                                    {
                                        readmean.add(mean.get(i).getMeans());
                                    }

                                    firebaseCallback.onCallBack(readmean);
                                }catch (Exception e)
                               {

                                    System.out.println("Không có nghĩa");
                                    System.out.println(e.getMessage());
                                }

                            }

                            else {
                                Notdata.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Content_course.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });









    }

    private interface FirebaseCallbackWord {
        void onCallBackWord(List<String> listw);
        void onCallBackimage(List<String> listi);
        void onCallBackNote(List<Long> listNote);

    }

    private interface FirebaseCallbackDataEF {
        void onCallBackEF(List<Float> listEF);


    }

    private interface FirebaseCallbackDatatemporary_time{
        void onCallBackinterval(List<Long> listTemporary_time);
    }

    private interface FirebaseCallbackDataInterval{
        void onCallBackinterval(List<Long> listInterval);
    }

    private interface FirebaseCallbackDataRepetitions{
        void onCallBackRepetitions(List<Long> listRepetitions);
    }

    private interface FirebaseCallbackMean {
        void onCallBack(List<String> list);

    }



    private void onClickabsolutely_not(View font, View back) {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long a = snapshot.child("lever").getValue(Long.class);
                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                lever.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        long b = snapshot1.child("lever"+a).getValue(Long.class);
                        long exp = snapshot.child("exp").getValue(Long.class);
                        progress = (int) exp;


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

        mSetRightOut.setTarget(font);
        mSetLeftIn.setTarget(back);
        mSetRightOut.start();
        mSetLeftIn.start();
        mIsBackVisible = true;

        back_layout.setVisibility(View.VISIBLE);
        font_layout.setVisibility(View.INVISIBLE);

    }

    private void ok(View font,View back) {
        mSetRightOut.setTarget(back);
        mSetLeftIn.setTarget(font);
        mSetRightOut.start();
        mSetLeftIn.start();
        mIsBackVisible = true;

        back_layout.setVisibility(View.INVISIBLE);
        font_layout.setVisibility(View.VISIBLE);
    }

    private void onClickUpHardtoremember(View font,View back) {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long a = snapshot.child("lever").getValue(Long.class);
                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                lever.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        long b = snapshot1.child("lever"+a).getValue(Long.class);
                        long exp = snapshot.child("exp").getValue(Long.class);
                        progress = (int) exp;
                        if (progress <progressBar.getMax())
                        {
                            progress += 1;
                            updateProgressBar();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("exp").setValue(exp+1);

                        }
                        if  (progress >= progressBar.getMax())
                        {
                            progress = 0;
                            progressBar.setProgress(0);
                            textViewProgress.setText("0%");
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("lever").setValue(a+1);
                            mDatabase.child("users").child(getUid).child("exp").setValue(0);
                        }

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

        mSetRightOut.setTarget(font);
        mSetLeftIn.setTarget(back);
        mSetRightOut.start();
        mSetLeftIn.start();
        mIsBackVisible = true;

        back_layout.setVisibility(View.VISIBLE);
        font_layout.setVisibility(View.INVISIBLE);

    }


    public void onClickUpEasy(View font,View back)
    {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long a = snapshot.child("lever").getValue(Long.class);
                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                lever.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        long b = snapshot1.child("lever"+a).getValue(Long.class);
                        long exp = snapshot.child("exp").getValue(Long.class);
                        progress = (int) exp;
                        if (progress <progressBar.getMax())
                        {
                            progress += 4;
                            updateProgressBar();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("exp").setValue(exp+4);

                        }
                        if  (progress == progressBar.getMax())
                        {
                            progress = 0;
                            progressBar.setProgress(0);
                            textViewProgress.setText("0%");
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("lever").setValue(a+1);
                            mDatabase.child("users").child(getUid).child("exp").setValue(0);
                        }

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

        mSetRightOut.setTarget(font);
        mSetLeftIn.setTarget(back);
        mSetRightOut.start();
        mSetLeftIn.start();
        mIsBackVisible = true;

        back_layout.setVisibility(View.VISIBLE);
        font_layout.setVisibility(View.INVISIBLE);

    }

    public void onClickUpperfect(View font,View back)
    {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long a = snapshot.child("lever").getValue(Long.class);
                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                lever.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        long b = snapshot1.child("lever"+a).getValue(Long.class);
                        long exp = snapshot.child("exp").getValue(Long.class);
                        progress = (int) exp;
                        if (progress <progressBar.getMax())
                        {
                            progress += 5;
                            updateProgressBar();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("exp").setValue(exp+5);

                        }
                        if  (progress == progressBar.getMax())
                        {
                            progress = 0;
                            progressBar.setProgress(0);
                            textViewProgress.setText("0%");
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("lever").setValue(a+1);
                            mDatabase.child("users").child(getUid).child("exp").setValue(0);
                        }

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

        mSetRightOut.setTarget(font);
        mSetLeftIn.setTarget(back);
        mSetRightOut.start();
        mSetLeftIn.start();
        mIsBackVisible = true;

        back_layout.setVisibility(View.VISIBLE);
        font_layout.setVisibility(View.INVISIBLE);

    }

    public void onClickconsider(View font,View back)
    {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long a = snapshot.child("lever").getValue(Long.class);
                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                lever.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        long b = snapshot1.child("lever"+a).getValue(Long.class);
                        long exp = snapshot.child("exp").getValue(Long.class);
                        progress = (int) exp;
                        if (progress <progressBar.getMax())
                        {
                            progress += 2;
                            updateProgressBar();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("exp").setValue(exp+2);

                        }
                        if  (progress == progressBar.getMax())
                        {
                            progress = 0;
                            progressBar.setProgress(0);
                            textViewProgress.setText("0%");
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("lever").setValue(a+1);
                            mDatabase.child("users").child(getUid).child("exp").setValue(0);
                        }

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

        mSetRightOut.setTarget(font);
        mSetLeftIn.setTarget(back);
        mSetRightOut.start();
        mSetLeftIn.start();
        mIsBackVisible = true;

        back_layout.setVisibility(View.VISIBLE);
        font_layout.setVisibility(View.INVISIBLE);

    }
    public void onClickDo_remember_but_long(View font,View back)
    {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long a = snapshot.child("lever").getValue(Long.class);
                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                lever.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        long b = snapshot1.child("lever"+a).getValue(Long.class);
                        long exp = snapshot.child("exp").getValue(Long.class);
                        progress = (int) exp;
                        if (progress <progressBar.getMax())
                        {
                            progress += 3;
                            updateProgressBar();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("exp").setValue(exp+3);

                        }
                        if  (progress == progressBar.getMax())
                        {
                            progress = 0;
                            progressBar.setProgress(0);
                            textViewProgress.setText("0%");
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("users").child(getUid).child("lever").setValue(a+1);
                            mDatabase.child("users").child(getUid).child("exp").setValue(0);
                        }

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

        mSetRightOut.setTarget(font);
        mSetLeftIn.setTarget(back);
        mSetRightOut.start();
        mSetLeftIn.start();
        mIsBackVisible = true;

        back_layout.setVisibility(View.VISIBLE);
        font_layout.setVisibility(View.INVISIBLE);

    }


    public void onClickLow()
    {
        if (progress >= 10)
        {
            progress -= 10;
            updateProgressBar();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có thực sự muốn thoát không? \nNếu thoát bạn sẽ bị trừ 10 kinh nghiệm")
                .setCancelable(false)
                .setPositiveButton("Tôi muốn thoát", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onClickLow();

                        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
                        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
                        DatabaseReference uid = user.child(getUid);
                        uid.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                long a = snapshot.child("lever").getValue(Long.class);
                                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                                lever.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                        long b = snapshot1.child("lever"+a).getValue(Long.class);
                                        long exp = snapshot.child("exp").getValue(Long.class);
                                        progress = (int) exp;
                                        if (progress <progressBar.getMax() && progress>=10)
                                        {
                                            progress -= 10;
                                            updateProgressBar();
                                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                            mDatabase.child("users").child(getUid).child("exp").setValue(exp-10);

                                        }
                                        if  (progress == progressBar.getMax())
                                        {
                                            progress = 0;
                                            progressBar.setProgress(0);
                                            textViewProgress.setText("0%");
                                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                            mDatabase.child("users").child(getUid).child("lever").setValue(a+1);
                                            mDatabase.child("users").child(getUid).child("exp").setValue(0);
                                        }

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

                        startActivity(new Intent(Content_course.this, Lottie_BottomBar.class));


                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void updateProgressBar()
    {





        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String getUid  = users.getUid();

//////////////get data lever//////////////////////////////////////////
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uid = user.child(getUid);
        uid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long a = snapshot.child("lever").getValue(Long.class);
                DatabaseReference lever = FirebaseDatabase.getInstance().getReference("lever");


                lever.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        long b = snapshot1.child("lever"+a).getValue(Long.class);


                        ///////////fomat hàm chỉ in ra 3 số thập phân sau dấu .//////////////////
                        Float amt = (float)progress/b*100;
                        DecimalFormat df = new DecimalFormat("#.###");
                                textViewProgress.setText(df.format(amt)+"%");



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
        progressBar.setProgress( progress);
    }


    private void changeCameraDistance(View font,View back) {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        font.setCameraDistance(scale);
        back.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }






}



