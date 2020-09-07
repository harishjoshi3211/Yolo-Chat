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

public class my_message_adapter extends RecyclerView.Adapter<my_message_adapter.viewHolder> {

    ArrayList<my_message> messages;



    public my_message_adapter(Context context, ArrayList<my_message> list)
    {
        messages=list;
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView friends_name,friend_msg;
        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            friends_name=itemView.findViewById(R.id.friend_name);
            friend_msg=itemView.findViewById(R.id.friend_msg);
        }
    }
    @NonNull
    @Override
    public my_message_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,parent,false);

        return new my_message_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull my_message_adapter.viewHolder holder, int position) {

        holder.itemView.setTag(messages.get(position));
        holder.friends_name.setText(messages.get(position).getName());
        holder.friend_msg.setText(messages.get(position).getMessage());

    }


    @Override
    public int getItemCount() {
        return messages.size();
    }
}
