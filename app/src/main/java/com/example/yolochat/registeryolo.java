package com.example.yolochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registeryolo extends AppCompatActivity {


    EditText edname,edpass,edrepass,edemail;
    Button btnregister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeryolo);

        edname=findViewById(R.id.edusermail);
        edpass=findViewById(R.id.edpass);
        edrepass=findViewById(R.id.edrepass);
        edemail=findViewById(R.id.edemail);
        btnregister=findViewById(R.id.btnregister);
        mAuth=FirebaseAuth.getInstance();
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edname.getText().toString().isEmpty()||edpass.getText().toString().isEmpty()||edrepass.getText().toString().isEmpty()||edemail.getText().toString().isEmpty())
                {
                    Toast.makeText(registeryolo.this,"please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String email=edemail.getText().toString();
                    String name=edname.getText().toString();
                    if(edpass.getText().toString().equals(edrepass.getText().toString()))
                    {
                        String password=edpass.getText().toString();
                        register_new(email,name,password);

                    }
                    else
                    {
                        Toast.makeText(registeryolo.this,"password did not match",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void register_new(String email, final String name, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {

                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    String uid=user.getUid();

                    mDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                    HashMap<String,String> mp=new HashMap<>();
                    mp.put("name",name);
                    mp.put("status","hi using yolo");

                    mDatabase.setValue(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent=new Intent(registeryolo.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });


                }
                else
                {
                    Toast.makeText(registeryolo.this,"error while registering",Toast.LENGTH_LONG).show();

                }
                if(!task.isSuccessful()){
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(registeryolo.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    //message.hide();
                    return;
                }
            }
        });

    }
}
