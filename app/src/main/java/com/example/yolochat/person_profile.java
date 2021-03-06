package com.example.yolochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class person_profile extends AppCompatActivity {

    TextView name,status,per_loc,per_prof;
    Button snd_req,unfriend_btn,message_btn;
    private DatabaseReference mdatabase,mDatabaserequest,mDatabasefriend;
    private FirebaseUser curr_user;
    private String sent_or_not;
    private LinearLayout req_layout,fri_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);

        name=findViewById(R.id.name);
        snd_req=findViewById(R.id.snd_req);
        status=findViewById(R.id.status);
        per_loc=findViewById(R.id.per_loc);
        per_prof=findViewById(R.id.per_prof);
        req_layout=findViewById(R.id.req_layout);
        fri_layout=findViewById(R.id.fri_layout);

        unfriend_btn=findViewById(R.id.unfriend_btn);
        message_btn=findViewById(R.id.message_btn);

        curr_user= FirebaseAuth.getInstance().getCurrentUser();
        final String curr_user_id=curr_user.getUid();
        sent_or_not="not";

        req_layout.setVisibility(View.GONE);
        fri_layout.setVisibility(View.GONE);
        
        final String uid=getIntent().getStringExtra("uid");
        mDatabaserequest=FirebaseDatabase.getInstance().getReference().child("requests");
        mDatabasefriend=FirebaseDatabase.getInstance().getReference().child("friends");

        mdatabase= FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name2=snapshot.child("name").getValue().toString();
                String status2=snapshot.child("status").getValue().toString();

                name.setText(name2);
                status.setText(status2);
                per_loc.setText(snapshot.child("location").getValue().toString());
                per_prof.setText(snapshot.child("profession").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaserequest.child(curr_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.hasChild(uid)) {

                    Toast.makeText(person_profile.this, snapshot.child(uid).child("req_state").getValue().toString(), Toast.LENGTH_SHORT).show();
                    if (snapshot.child(uid).child("req_state").getValue().toString().equals("sent")) {
                        sent_or_not = "yes";
                        snd_req.setText("Cancel friend request");
                    } else if (snapshot.child(uid).child("req_state").getValue().toString().equals("received")) {
                        sent_or_not = "req_received";
                        snd_req.setText("confirm friend request");
                    }
                    req_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    mDatabasefriend.child(curr_user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(uid)){
                                fri_layout.setVisibility(View.VISIBLE);
                            }
                            else{
                                req_layout.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        snd_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sent_or_not.equals("not")) {

                    mDatabaserequest.child(curr_user_id).child(uid).child("req_state").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(person_profile.this, "sent friend request", Toast.LENGTH_LONG).show();
                                mDatabaserequest.child(uid).child(curr_user_id).child("req_state").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            mDatabaserequest.child(uid).child(curr_user_id).child("uid").setValue((curr_user_id)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){
                                                        sent_or_not="yes";
                                                        snd_req.setText("cancel sent request");

                                                    }
                                                }
                                            });

                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                else if(sent_or_not.equals("yes"))
                {
                    mDatabaserequest.child(curr_user_id).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(person_profile.this,"Cancelled sent friend request successfully",Toast.LENGTH_SHORT).show();

                                mDatabaserequest.child(uid).child(curr_user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())
                                        {

                                            snd_req.setText("Send friend request");
                                            sent_or_not="not";

                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(person_profile.this,"Unable to cancel sent friend request",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else if(sent_or_not.equals("req_received"))
                {
                    mDatabasefriend.child(curr_user_id).child(uid).setValue("yes").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                mDatabasefriend.child(uid).child(curr_user_id).setValue("yes").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())
                                        {
                                            mDatabaserequest.child(curr_user_id).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful())
                                                    {
                                                        mDatabaserequest.child(uid).child(curr_user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if(task.isSuccessful())
                                                                {
                                                                    Toast.makeText(person_profile.this,"friends now",Toast.LENGTH_SHORT).show();
                                                                    snd_req.setVisibility(View.GONE);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        unfriend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabasefriend.child(curr_user_id).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mDatabasefriend.child(uid).child(curr_user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        req_layout.setVisibility(View.VISIBLE);
                                        fri_layout.setVisibility(View.GONE);


                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(person_profile.this,message.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

    }
}
