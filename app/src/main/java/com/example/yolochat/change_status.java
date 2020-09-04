package com.example.yolochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class change_status extends AppCompatActivity {


    private Toolbar tb;

    TextView statusc;
    Button makec;

    private FirebaseUser user;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        tb=findViewById(R.id.status_bar);
        statusc=findViewById(R.id.status_change);
        makec=findViewById(R.id.make_change);

        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Change Status");

        makec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status=statusc.getText().toString();

                user= FirebaseAuth.getInstance().getCurrentUser();
                String user_uid=user.getUid();

                mdatabase= FirebaseDatabase.getInstance().getReference().child("users").child(user_uid);

                mdatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            finish();
                        }
                    }
                });

            }
        });
    }
}
