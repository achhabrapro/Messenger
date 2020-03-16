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

public class MainActivity extends AppCompatActivity {

    private EditText edit_username,edit_password;
    private String username,password;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            Intent intent=new Intent(this,Display_Contacts.class);
            startActivity(intent);
        }
        progressDialog=new ProgressDialog(this);
    }
    public void Register(View view){
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }
    private void get_references(){
        edit_username = (EditText) findViewById(R.id.username);
        edit_password = (EditText) findViewById(R.id.password);
        username = edit_username.getText().toString();
        password = edit_password.getText().toString();
    }

    private boolean check_username(){
        if(username.length()==0){ return false; }
        else{ return true; }
    }

    private boolean check_password(){
        if(password.length()==0){ return false; }
        else{ return true; }
    }
    public void login(View view){

        get_references();
        if(check_password()&&check_username()){
            progressDialog.setMessage("Pls Wait");
            progressDialog.show();
            sign_in();
        }
        else{ Toast.makeText(this,"Field cant be blank",Toast.LENGTH_LONG).show(); }
    }
    private void sign_in(){
        firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Done", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent2=new Intent(MainActivity.this,Display_Contacts.class);
                    startActivity(intent2);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Failed",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}

