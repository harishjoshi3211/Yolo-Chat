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

public class friends_adapter extends RecyclerView.Adapter<friends_adapter.viewHolder> {

    itemclicked activity;
    ArrayList<friends_class> people;
    public interface itemclicked
    {
        void onitemclicked(String uid);
    }


    public friends_adapter(Context context, ArrayList<friends_class> list)
    {
        activity=(itemclicked)context;
        people=list;
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        ImageView img1;
        TextView friends_name,friends_status;
        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            friends_name=itemView.findViewById(R.id.friends_name);
            friends_status=itemView.findViewById(R.id.friends_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onitemclicked(people.get(people.indexOf((friends_class) v.getTag())).getUid());
                }
            });
        }
    }
    @NonNull
    @Override
    public friends_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_rv_layout,parent,false);

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull friends_adapter.viewHolder holder, int position) {

        holder.itemView.setTag(people.get(position));
        holder.friends_name.setText(people.get(position).getName());
        holder.friends_status.setText(people.get(position).getStatus());

    }


    @Override
    public int getItemCount() {
        return people.size();
    }
}
