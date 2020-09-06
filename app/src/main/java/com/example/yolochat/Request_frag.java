package com.example.yolochat;

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
public class Request_frag extends Fragment{

    private RecyclerView rv;

    private ArrayList<requested_person> list;
    public Request_frag() {
        // Required empty public constructor
    }


    private DatabaseReference mDatabase,muserdatabase;
    private FirebaseUser user;
    private  ViewGroup vg;
    private requests_adapter ra;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_request_frag, container, false);

        vg=container;
        rv=view.findViewById(R.id.rv_req);

        list=new ArrayList<requested_person>();
        user=FirebaseAuth.getInstance().getCurrentUser();
        String user_id=user.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("requests").child(user_id);
        muserdatabase=FirebaseDatabase.getInstance().getReference().child("users");



        rv = view.findViewById(R.id.rv_req);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        ra=new requests_adapter(vg.getContext(),list);
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
                    if(postsnapshot.child("req_state").getValue().equals("received")){

                        String uid=postsnapshot.child("uid").getValue().toString();



                        muserdatabase.child(uid).child("name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String name=snapshot.getValue().toString();
                                requested_person person=new requested_person(name,"12.02.20",uid);

                                Toast.makeText(vg.getContext(),name,Toast.LENGTH_SHORT).show();
                                list.add(person);
                                ra.notifyDataSetChanged();

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
