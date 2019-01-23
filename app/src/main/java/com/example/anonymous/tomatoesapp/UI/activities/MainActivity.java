package com.example.anonymous.tomatoesapp.UI.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.tomatoesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,password;
    TextView backtologin;
    Button sign_Up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText) findViewById(R.id.email_edit_text);
        password=(EditText) findViewById(R.id.password_edit_text);
        backtologin=(TextView) findViewById(R.id.back_to_signuptxt);
        sign_Up=(Button) findViewById(R.id.loginbtn);
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });
        sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_users();
            }
        });
        /*
        This activity is created and opened when SplashScreen finishes its animations.
        To ensure a smooth transition between activities, the activity creation animation
        is removed.
        RelativeLayout with EditTexts and Button is animated with a default fade in.
         */

        overridePendingTransition(0,0);
        View relativeLayout=findViewById(R.id.login_container);
        Animation animation=AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        relativeLayout.startAnimation(animation);
    }



    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
    }
    public void  login_users(){
        String email=this.email.getText().toString();
        String pwd=this.password.getText().toString();
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pwd)){

            mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    }  else{
                        Toast.makeText(MainActivity.this,"login failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(MainActivity.this,"blanks not allowed",Toast.LENGTH_SHORT).show();


        }}
}
