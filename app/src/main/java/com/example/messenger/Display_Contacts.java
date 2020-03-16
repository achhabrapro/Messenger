package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Display_Contacts extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String id;
    public Toolbar toolbar;
    private TabLayout tabLayout;
    private TabItem tabchat,tabcontacts;
    private ViewPager viewPager;
    private PageAdaptor pageAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__contacts);
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            id=user.getUid();
        }
        tab_layout();



    }
    public void logout(){
        firebaseAuth.signOut();
        finish();
        Intent intent3=new Intent(Display_Contacts.this,MainActivity.class);
        startActivity(intent3);
    }
    private void tab_layout(){
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout=findViewById(R.id.tab_layout);
        tabchat=findViewById(R.id.tabChats);
        tabcontacts=findViewById(R.id.tabContacts);
        viewPager=findViewById(R.id.viewpager);
        pageAdaptor=new PageAdaptor(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdaptor);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.log){
            logout();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }

    }


}
