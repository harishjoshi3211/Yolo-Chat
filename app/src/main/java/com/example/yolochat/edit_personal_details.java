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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_personal_details extends AppCompatActivity {


    EditText edit_loc,edit_prof;
    Button update_loc,update_prof,btp;

    private DatabaseReference mDatabase;
    private FirebaseUser curr_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_details);

        edit_loc=findViewById(R.id.edit_loc);
        edit_prof=findViewById(R.id.edit_prof);
        update_loc=findViewById(R.id.update_loc);
        update_prof=findViewById(R.id.update_prof);
        btp=findViewById(R.id.btp);
        curr_user= FirebaseAuth.getInstance().getCurrentUser();

        String uid=curr_user.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        update_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loc=edit_loc.getText().toString().trim();

                mDatabase.child("location").setValue(loc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(edit_personal_details.this,"Location updated successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(edit_personal_details.this,"Unable to update location data",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        update_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loc=edit_prof.getText().toString().trim();

                mDatabase.child("profession").setValue(loc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(edit_personal_details.this,"Profession updated successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(edit_personal_details.this,"Unable to update Profession",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        btp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(edit_personal_details.this,profile.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
