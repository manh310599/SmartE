package com.example.learn_english_smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lib.customedittext.CustomEditText;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Sign_up_Email extends AppCompatActivity {

    private static final int MAX_LENGTH = 10;
    CustomEditText TaiKhoan,MatKhau,NhapLai;
    FirebaseAuth mAuth;
    FloatingTextButton DangKi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);

        TaiKhoan = findViewById(R.id.EditTK);
        MatKhau = findViewById(R.id.EditMK);
        NhapLai = findViewById(R.id.NhapLaiPass);
        DangKi = findViewById(R.id.dangki);

        mAuth = FirebaseAuth.getInstance();

        DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TaiKhoan.getText().trim().isEmpty() && MatKhau.getText().trim().equals(NhapLai.getText().trim()) && MatKhau.getText().trim().length() >= 6)
                {
                    mAuth.createUserWithEmailAndPassword(TaiKhoan.getText().trim(), MatKhau.getText().trim())
                            .addOnCompleteListener(Sign_up_Email.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        updateUI(null);
                                    }
                                }
                            });

                }
                else if (MatKhau.getText().trim().isEmpty() || TaiKhoan.getText().isEmpty() || NhapLai.getText().isEmpty())
                {
                    Toast.makeText(Sign_up_Email.this, "Không được để trống phần thông tin đăng kí", Toast.LENGTH_SHORT).show();
                }

                else if (!MatKhau.getText().trim().equals(NhapLai.getText().trim()))
                {
                    Toast.makeText(Sign_up_Email.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                }
                else if (MatKhau.getText().trim().length() < 6 )
                {
                    Toast.makeText(Sign_up_Email.this, "Mật khẩu phải có hơn 6 kí tự", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Sign_up_Email.this, "Vui lòng nhập đúng định dạng Gmail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void updateUI(FirebaseUser account){

        if(account != null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Name, email address, and profile photo Url

             //   int
                int random = new Random().nextInt((10000-1000)+1)+1000;

                String email = user.getEmail();
                String photoUrl = "https://sp-ao.shortpixel.ai/client/q_glossy,ret_img,w_300/https://www.webinarleads4you.com/wp-content/uploads/2017/02/no-avatar-350x350-300x300.jpg";



                boolean emailVerified = user.isEmailVerified();

                String uid = user.getUid();

                int lever = 0;

                writeNewUser(uid,"user"+random,email,photoUrl,lever);




                    Toast.makeText(this,"Chúc mừng bạn đăng kí thành công", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this,MainActivity.class));
                    this.finish();


            }



        }else {
            Toast.makeText(this,"Đăng kí không thành công",Toast.LENGTH_LONG).show();
        }

    }




    public void writeNewUser(String userId, String name, String email,String image,int lever) {





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        User user = new User(name, email,image,lever,0);


        myRef.child("users").child(userId).setValue(user);

    }

}