package com.example.yolochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class message extends AppCompatActivity {

    private RecyclerView rv;
    private TextView user_name;


    private Toolbar tb;
    private EditText send_msg;
    private Button sent;

    private ArrayList<my_message> list;

    private DatabaseReference mdatabase,user_database;
    private FirebaseUser curr_user;

    private String fr_name,my_name;
    private my_message_adapter ra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rv=findViewById(R.id.rv);
        user_name=findViewById(R.id.user_name);
        send_msg=findViewById(R.id.send_msg);
        sent=findViewById(R.id.sent);


        tb=findViewById(R.id.message_tool);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("YOLO Chats");
        list=new ArrayList<my_message>();
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ra=new my_message_adapter(this,list);

        rv.setAdapter(ra);
        curr_user= FirebaseAuth.getInstance().getCurrentUser();
        String curr_uid=curr_user.getUid().toString();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("chat");
        String uid= getIntent().getStringExtra("uid");




        user_database=FirebaseDatabase.getInstance().getReference().child("users");

        fr_name="";
        my_name="";

        user_database.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                user_name.setText(snapshot.child("name").getValue().toString());
                fr_name=snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user_database.child(curr_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                my_name=snapshot.child("name").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mdatabase.child(curr_uid).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot!=null){

                    for(DataSnapshot post:snapshot.getChildren()){

                        String uidx=post.child("uid").getValue().toString();
                        String msg=post.child("message").getValue().toString();


                        my_message msg_sent=new my_message();


                        msg_sent.setMessage(msg);
                        if(uidx.equals(curr_uid)){

                            msg_sent.setName(my_name);

                        }
                        else{
                            msg_sent.setName(fr_name);
                        }


                        list.add(msg_sent);
                        ra.notifyDataSetChanged();



                    }


                }
                else{

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg=send_msg.getText().toString();
                if(!msg.equals("")){

                    DatabaseReference user_push=mdatabase.child(uid).push();
                    String push_id=user_push.getKey();

                    Map msg_map=new HashMap();
                    msg_map.put("message",msg);
                    msg_map.put("time", ServerValue.TIMESTAMP);
                    msg_map.put("uid",curr_uid);
                    mdatabase.child(curr_uid).child(uid).child(push_id).setValue(msg_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mdatabase.child(uid).child(curr_uid).child(push_id).setValue(msg_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            send_msg.setText("");

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
