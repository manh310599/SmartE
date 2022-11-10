package com.example.learn_english_smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.lib.customedittext.CustomEditText;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Forgot_Pass extends AppCompatActivity {

    CustomEditText TK;
    FloatingTextButton Change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        TK = findViewById(R.id.EditTK);


        Change = findViewById(R.id.Chage);

        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                try {
                    auth.sendPasswordResetEmail(TK.getText())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Forgot_Pass.this, "Hãy đổi mật khẩu theo đường link trong mail của bạn", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Forgot_Pass.this,MainActivity.class));
                                        Forgot_Pass.this.finish();
                                    }
                                }
                            });
                }catch (Exception e)
                {
                    Toast.makeText(Forgot_Pass.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}