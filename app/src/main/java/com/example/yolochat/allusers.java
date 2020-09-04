package com.example.yolochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class allusers extends AppCompatActivity {

    private Toolbar tb,tb2;
    private RecyclerView rv;
    private DatabaseReference mdatabase;
    private TextView username,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allusers);

        rv=findViewById(R.id.rv);
        username=findViewById(R.id.username);
        status=findViewById(R.id.status);
        tb=findViewById(R.id.all_users_tool);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("YOLO Chats");

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mdatabase= FirebaseDatabase.getInstance().getReference().child("users");




    }
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerAdapter<users,userviewholder> frbrcadapter=new FirebaseRecyclerAdapter<users, userviewholder>(users.class,R.layout.users_layout,userviewholder.class,mdatabase) {
            @Override
            protected void populateViewHolder(userviewholder userviewholder, users users, int i) {

                userviewholder.setDisplayname(users.getName(),users.getStatus());
                final String usr_id=getRef(i).getKey();

                userviewholder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(allusers.this,person_profile.class);
                        intent.putExtra("uid",usr_id);
                        startActivity(intent);
                    }
                });
            }
        };

        rv.setAdapter(frbrcadapter);

    }

    public static class userviewholder extends RecyclerView.ViewHolder{

        View mview;
        public userviewholder(@NonNull View itemView) {
            super(itemView);

            mview=itemView;
        }

        public void setDisplayname(String naam,String status)
        {
            TextView usrname=mview.findViewById(R.id.username);
            usrname.setText(naam);
            TextView stats=mview.findViewById(R.id.status);
            stats.setText(status);

        }
    }
}
