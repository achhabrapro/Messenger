package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText edit_username,edit_password,edit_name,edit_phone;
    private String username,password,name,phone;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Messenger");
        progressDialog=new ProgressDialog(this);
    }
    private void get_references(){
        edit_username = (EditText) findViewById(R.id.username);
        edit_password = (EditText) findViewById(R.id.password);
        edit_name=(EditText)findViewById(R.id.name);
        edit_phone=(EditText)findViewById(R.id.phone);
        username = edit_username.getText().toString();
        password = edit_password.getText().toString();
        name=edit_name.getText().toString();
        phone=edit_phone.getText().toString();
    }

    private boolean check_username(){
        if(username.length()==0){ return false; }
        else{ return true; }
    }

    private boolean check_password(){
        if(password.length()==0){ return false; }
        else{ return true; }
    }
    private boolean check_name(){
        if(name.length()==0){ return false; }
        else{ return true; }
    }

    private boolean check_phone(){
        if(phone.length()!=10){ return false; }
        else{ return true; }

    }
    public void submit(View view){

        get_references();
        if(check_password()&&check_username()&&check_name()&&check_phone()){
            progressDialog.setMessage("Pls Wait");
            progressDialog.show();
            create_account();
        }
        else{ Toast.makeText(this,"Field cant be blank and Phone should have 10 digits",Toast.LENGTH_LONG).show(); }
    }

    private void create_account(){
        firebaseAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    insertdata();
                    Toast.makeText(Register.this, "Registration Done", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent2=new Intent(Register.this,Display_Contacts.class);
                    startActivity(intent2);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "Registration Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void insertdata(){
        store data=new store(name);
        String id;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            id=user.getUid();
            databaseReference.child(id).setValue(data);
        }

    }
}
