package com.example.appdev.blogapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdev.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private Button createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField = (EditText) findViewById(R.id.emailId);
        passwordField = (EditText) findViewById(R.id.passwordId);
        loginButton = (Button) findViewById(R.id.loginId);
        createAccountButton = (Button) findViewById(R.id.createAccountId);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if(mUser != null){
                    Toast.makeText(LoginActivity.this,"Signed In!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, postListActivity.class));
                    finish();
                } else{
                    Toast.makeText(LoginActivity.this,"Not signed In!!",Toast.LENGTH_LONG).show();
                }
            }
        };
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(emailField.getText().toString()) && !TextUtils.isEmpty(passwordField.getText().toString()) ){
                    String email = emailField.getText().toString();
                    String password = passwordField.getText().toString();
                    Login(email, password);
                }
                else{

                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
   }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_signout){
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void Login(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   Toast.makeText(LoginActivity.this,"SIGNED IN",Toast.LENGTH_LONG).show();
                   startActivity(new Intent(LoginActivity.this, postListActivity.class));
                   finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,"NOT SIGNED IN",Toast.LENGTH_LONG).show();
                }
            }
        });
   }
}
