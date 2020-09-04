package com.example.yolochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class login extends AppCompatActivity {


    TextView edusermail,edpass;
    TextView tvforgot;
    Button btnlogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edusermail=findViewById(R.id.edusermail);
        edpass=findViewById(R.id.edpass);
        tvforgot=findViewById(R.id.tvforgot);
        btnlogin=findViewById(R.id.btnlogin);
        mAuth=FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edusermail.getText().toString().isEmpty()||edpass.getText().toString().isEmpty())
                {
                    Toast.makeText(login.this,"enter email & password carefully",Toast.LENGTH_SHORT).show();
                }
                else {
                    String email = edusermail.getText().toString();
                    String password = edpass.getText().toString();
                    login_user(email,password);
                }

            }
        });


    }
    private void login_user(String email ,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Intent intent=new Intent(login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(login.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
