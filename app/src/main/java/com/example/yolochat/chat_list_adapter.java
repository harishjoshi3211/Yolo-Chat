package com.example.yolochat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chat_list_adapter extends RecyclerView.Adapter<chat_list_adapter.viewHolder> {

    chat_list_adapter.itemclicked activity;
    ArrayList<users> people;
    public interface itemclicked
    {
        void onitemclicked_chat(String uid);
    }


    public chat_list_adapter(Context context, ArrayList<users> list)
    {
        activity=(chat_list_adapter.itemclicked)context;
        people=list;
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        ImageView img1;
        TextView chat_name;
        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            chat_name=itemView.findViewById(R.id.chat_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onitemclicked_chat(people.get(people.indexOf((users) v.getTag())).getUid());
                }
            });
        }
    }
    @NonNull
    @Override
    public chat_list_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_rv,parent,false);

        return new chat_list_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull chat_list_adapter.viewHolder holder, int position) {

        holder.itemView.setTag(people.get(position));
        holder.chat_name.setText(people.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return people.size();
    }
}
