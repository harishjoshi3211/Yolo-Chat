package com.example.yolochat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class friends_frag extends Fragment {

    private RecyclerView rv;

    private ArrayList<friends_class> list;
    public friends_frag() {
        // Required empty public constructor
    }


    private DatabaseReference mDatabase,muserdatabase;
    private FirebaseUser user;
    private  ViewGroup vg;
    private friends_adapter ra;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.friends_layout, container, false);

        vg=container;
        rv=view.findViewById(R.id.rv_friends);

        list=new ArrayList<friends_class>();
        user=FirebaseAuth.getInstance().getCurrentUser();
        String user_id=user.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("friends").child(user_id);
        muserdatabase=FirebaseDatabase.getInstance().getReference().child("users");



        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        ra=new friends_adapter(vg.getContext(),list);
        return view;
    }

    @Override
    public void onStart() {

        super.onStart();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postsnapshot:snapshot.getChildren())
                {
                    if(true){


                        String uid=postsnapshot.getRef().getKey().toString();
                        friends_class fc=new friends_class();


                        fc.setUid(uid);

                        muserdatabase.child(uid).child("name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String name=snapshot.getValue().toString();
                                fc.setName(name);
                                muserdatabase.child(uid).child("status").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        String status=snapshot.getValue().toString();
                                        fc.setStatus(status);

                                        list.add(fc);
                                        ra.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });










                    }
                }


                rv.setAdapter(ra);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
