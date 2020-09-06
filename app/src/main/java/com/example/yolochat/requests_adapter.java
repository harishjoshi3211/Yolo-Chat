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

public class requests_adapter extends RecyclerView.Adapter<requests_adapter.viewHolder> {

    itemclicked_req activity;
    ArrayList<requested_person> people;
    public interface itemclicked_req
    {
        void onitemclicked2(int index,String uid);
    }


    public requests_adapter(Context context, ArrayList<requested_person> list)
    {
        activity=(itemclicked_req) context;
        people=list;
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        ImageView img1;
        TextView req_name,req_date;
        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            req_name=itemView.findViewById(R.id.req_name);
            req_date=itemView.findViewById(R.id.req_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onitemclicked2(people.indexOf((requested_person) v.getTag()),people.get(people.indexOf((requested_person) v.getTag())).getUid());
                }
            });
        }
    }
    @NonNull
    @Override
    public requests_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_layout,parent,false);

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull requests_adapter.viewHolder holder, int position) {

        holder.itemView.setTag(people.get(position));
        holder.req_name.setText(people.get(position).getName());
        holder.req_date.setText(people.get(position).getDate());

    }


    @Override
    public int getItemCount() {
        return people.size();
    }
}
