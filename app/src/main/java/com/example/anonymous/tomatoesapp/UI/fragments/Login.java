package com.example.anonymous.tomatoesapp.UI.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.activities.HomeActivity;
import com.example.anonymous.tomatoesapp.UI.activities.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    private FirebaseAuth mAuth;
    EditText email,password;
    TextView backtologin;
    Button sign_Up;
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText) view.findViewById(R.id.login_emailtxt);
        password=(EditText) view.findViewById(R.id.login_pwdtxt);
        backtologin=(TextView) view.findViewById(R.id.back_to_signuptxt);
        sign_Up=(Button) view.findViewById(R.id.loginbtn);
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SignUpActivity.class));
            }
        });
        sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_users();
            }
        });
return view;
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            startActivity(new Intent(getContext(),HomeActivity.class));
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
         startActivity(new Intent(getActivity(),HomeActivity.class));
      }  else{
          Toast.makeText(getActivity(),"login failed",Toast.LENGTH_SHORT).show();
      }
    }
});

   }else{
           Toast.makeText(getActivity(),"blanks not allowed",Toast.LENGTH_SHORT).show();


       }}}

