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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Message_Adaptor extends RecyclerView.Adapter<Message_Adaptor.message_viewholder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private List<Chat> mchat;
    private android.content.Context mcontext;

    FirebaseUser user;

    public Message_Adaptor(Context context, List<Chat> chat){

        this.mchat=chat;
        mcontext=context;
    }
    @NonNull
    @Override
    public Message_Adaptor.message_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        if(viewType==MSG_TYPE_RIGHT) {
            View view = inflater.inflate(R.layout.chat_item_right, parent, false);
            return new Message_Adaptor.message_viewholder(view);
        }else{
            View view = inflater.inflate(R.layout.chat_item_left, parent, false);
            return new Message_Adaptor.message_viewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final Message_Adaptor.message_viewholder holder, final int position) {

        Chat chat=mchat.get(position);
        holder.show_msg.setText(chat.getChat());


    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }

    public class message_viewholder extends RecyclerView.ViewHolder{

        public TextView show_msg;

        public message_viewholder(@NonNull View itemView) {
            super(itemView);
            show_msg=(TextView)itemView.findViewById(R.id.show_msg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(mchat.get(position).getSender().equals(user.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}


