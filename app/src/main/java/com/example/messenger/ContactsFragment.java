package com.example.messenger;


import android.app.ProgressDialog;
import android.content.Context;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private DatabaseReference databaseReference;
    private RecyclerView contacts;
    private List<String> send_Contact=new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Contacts Loading...");
        progressDialog.show();
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        final View view=inflater.inflate(R.layout.fragment_contacts, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("Messenger");
        final RecyclerView rv = new RecyclerView(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                send_Contact.clear();
                List<String> id=new ArrayList<>();
                for(DataSnapshot keynode:dataSnapshot.getChildren()){
                    if(user!=null) {
                        if (!user.getUid().equals(keynode.getKey())) {
                            id.add(keynode.getKey());
                            store contact = keynode.getValue(store.class);
                            send_Contact.add(contact.getName());
                        }
                    }
                }
                rv.setAdapter(new contacts_adaptor(view.getContext(),send_Contact,id));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });
        return rv;
    }



}
