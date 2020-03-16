package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Message extends AppCompatActivity {
    private String name;
    private String id_receiver;
    private String id_sender;
    private EditText edit_msg;
    private String msg;
    FirebaseAuth firebaseAuth;
    FirebaseUser current_user;
    Message_Adaptor message_adaptor;
    List<Chat> mchat;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        firebaseAuth=FirebaseAuth.getInstance();
        current_user=firebaseAuth.getCurrentUser();
        id_sender=current_user.getUid();
        name=getIntent().getStringExtra("contact_name");
        id_receiver=getIntent().getStringExtra("contact_id");
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        toolbar.setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        readmsg();
    }

    public void send_message(View view){
        id_sender=current_user.getUid();
        edit_msg=(EditText) findViewById(R.id.write_msg);
        id_receiver=getIntent().getStringExtra("contact_id");
        msg=edit_msg.getText().toString();
        if(msg.length()==0){
            Toast.makeText(this,"Enter a msg",Toast.LENGTH_LONG).show();
        }
        else{
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("sender",id_sender);
            hashMap.put("receiver",id_receiver);
            hashMap.put("chat",msg);
            databaseReference.child("Chats").push().setValue(hashMap);
            readmsg();
        }
        edit_msg.setText("");

    }

    private void readmsg(){
        final String sender=current_user.getUid();
        final String receiver=getIntent().getStringExtra("contact_id");
        mchat=new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);
                    if((chat.getSender().equals(sender)&&chat.getReceiver().equals(receiver))||
                            (chat.getSender().equals(receiver)&&chat.getReceiver().equals(sender))){
                        mchat.add(chat);
                    }

                }
                message_adaptor=new Message_Adaptor(Message.this,mchat);
                recyclerView.setAdapter(message_adaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
