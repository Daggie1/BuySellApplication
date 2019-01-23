package com.example.anonymous.tomatoesapp.UI.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.model.TomatoesModel;
import com.example.anonymous.tomatoesapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,password,phone;
    TextView backtologin;
    Button sign_Up;
    DatabaseReference dbreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbreference= FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        email=(EditText) findViewById(R.id.email_edit_text);
        phone=(EditText) findViewById(R.id.phone_edit_text);
        password=(EditText) findViewById(R.id.password_edit_text);
        backtologin=(TextView) findViewById(R.id.back_to_signuptxt);
        sign_Up=(Button) findViewById(R.id.loginbtn);
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            }
        });
        sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupnew_users();
            }
        });

    }

    public  void signupnew_users(){
        final String email=this.email.getText().toString();
        final String pwd=this.password.getText().toString();
        final String phone=this.phone.getText().toString();
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(phone)) {
            mAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String id=dbreference.push().getKey();
                                String sellerId= FirebaseAuth.getInstance().getCurrentUser().getUid();

                                User user=new User(phone,email,pwd,sellerId);
                                dbreference.child(id).setValue(user);
                                Toast.makeText(SignUpActivity.this,"account successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                            }
                        }
                    });
        }else {
            Toast.makeText(this,"blanks not allowed",Toast.LENGTH_LONG).show();}
        }
    }

