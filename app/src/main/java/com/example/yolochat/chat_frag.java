package com.example.yolochat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class chat_frag extends Fragment {

    private ArrayList<users> chat_users;
    private RecyclerView chat_list_rv;
    private chat_list_adapter ra;

    private DatabaseReference mdatabase,userdatabase;
    private FirebaseUser curr_user;

    public chat_frag() {
        // Required empty public constructor
    }


    ViewGroup vg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chat_frag, container, false);
        vg=container;
        chat_users=new ArrayList<>();
        chat_list_rv=view.findViewById(R.id.rv_chat);
        chat_list_rv.setHasFixedSize(true);
        chat_list_rv.setLayoutManager(new LinearLayoutManager(getContext()));


        curr_user= FirebaseAuth.getInstance().getCurrentUser();
        String curr_uid=curr_user.getUid().toString();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("chat").child(curr_uid);
        userdatabase=FirebaseDatabase.getInstance().getReference().child("users");
        ra=new chat_list_adapter(vg.getContext(),chat_users);
        chat_list_rv.setAdapter(ra);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot post:snapshot.getChildren()){

                    final String uid=post.getRef().getKey().toString();
                    userdatabase.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String name=snapshot.child("name").getValue().toString();

                            users chat_user=new users();
                            chat_user.setName(name);
                            chat_user.setUid(uid);
                            chat_users.add(chat_user);
                            ra.notifyDataSetChanged();


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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }


}
