package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class contacts_adaptor extends RecyclerView.Adapter<contacts_adaptor.contacts_viewholder> {

    private List<String> data;
    private List<String> id;
    private android.content.Context mcontext;

    public contacts_adaptor(Context context, List<String> data,List<String> id){

        this.data=data;
        this.id=id;
        mcontext=context;
    }
    @NonNull
    @Override
    public contacts_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_contacts,parent,false);
        return new contacts_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final contacts_viewholder holder, final int position) {

        String title=data.get(position);
        holder.contact_name.setText(title);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(mcontext,Message.class);
               intent.putExtra("contact_name",data.get(position));
               intent.putExtra("contact_id",id.get(position));
               mcontext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class contacts_viewholder extends RecyclerView.ViewHolder{

        public ImageView dp;
        public TextView contact_name;

        public contacts_viewholder(@NonNull View itemView) {
            super(itemView);
            dp=(ImageView)itemView.findViewById(R.id.dp);
            contact_name=(TextView)itemView.findViewById(R.id.contact_name);
        }
    }
}
