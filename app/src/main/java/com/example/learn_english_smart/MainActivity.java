package com.example.learn_english_smart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learn_english_smart.databinding.ActivityMainBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lib.customedittext.CustomEditText;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class MainActivity extends AppCompatActivity {

    private static final int REQ_ONE_TAP = 102;
    private static final String EMAIL = "email";

    TextView ForgotPass;
    LoginButton Fb;
    CallbackManager mCallbackManager;

    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    FloatingTextButton DangKi,DangNhap;
     FirebaseAuth mAuth;
     private static final String TAG = "GOOGLE_SIGN_TAG";
     AuthResult authResult;
    CustomEditText EditTk,EditMk;
    private SignInClient oneTapClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    Fb = findViewById(R.id.login_button_fb);

    DangKi = findViewById(R.id.dangki);
    DangNhap = findViewById(R.id.DangNhap);
    EditTk = findViewById(R.id.EditTK);
    EditMk = findViewById(R.id.EditMK);
    ForgotPass = findViewById(R.id.ForgotPass);

        Fb.setPermissions (Collections.singletonList(EMAIL));

        mAuth = FirebaseAuth.getInstance();

//////////////////////Đăng nhập với facebook//////////////////////////
        mCallbackManager = CallbackManager.Factory.create();

        Fb.setPermissions("email", "public_profile");
        Fb.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onError(@NonNull FacebookException e) {

            }

            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            private void handleFacebookAccessToken(AccessToken accessToken) {


                AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                                    if(isNewUser){
                                        updateUINew(user);
                                    }

                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }



            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }


        });




    /////////////////////Đăng nhập google////////////////////////

        //////////////////////////// Đăng kí bằng Gmail//////////////////////////////
        DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Sign_up_Email.class));
            }
        });
        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

try {
    mAuth.signInWithEmailAndPassword(EditTk.getText().trim(), EditMk.getText().trim())
            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {


                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {


                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {


                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
}catch (Exception e)
{
    if (EditMk.getText().equals("") || EditTk.getText().equals(""))
    {
        Toast.makeText(MainActivity.this, "Tài khoản và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
    }
    else {
        Toast.makeText(MainActivity.this, "Mail và mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
    }
}


            }
        });



        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Forgot_Pass.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = googleCredential.getGoogleIdToken();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                        mAuth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithCredential:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                                            updateUI(null);
                                        }
                                    }
                                });
                    }
                } catch (ApiException e) {
                    // ...
                }
                break;
        }
    }



    
    /////////////////////////////////////////////////////
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser account){

        if(account != null){


            Toast.makeText(this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Lottie_BottomBar.class));


        }else {
            Toast.makeText(this,"Xin vui lòng đăng nhập để sử dụng dịch vụ",Toast.LENGTH_LONG).show();
        }

    }

    private void updateUINew(FirebaseUser account) {

        if(account != null){


            Toast.makeText(this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Lottie_BottomBar.class));

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {

                String name = user.getDisplayName();
                String image = Objects.requireNonNull(user.getPhotoUrl()).toString();
                String uid = user.getUid();
                int lever = 0;
                writeNewUser(uid,name,null,image,lever);
            }


        }else {
            Toast.makeText(this,"Xin vui lòng đăng nhập để sử dụng dịch vụ",Toast.LENGTH_LONG).show();
        }

    }

    public void writeNewUser(String userId, String name, String email,String image,int lever) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        User user = new User(name, email,image,lever,0);


        myRef.child("users").child(userId).setValue(user);

    }
}