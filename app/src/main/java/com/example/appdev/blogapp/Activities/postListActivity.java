package com.example.appdev.blogapp.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.appdev.blogapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class postListActivity extends AppCompatActivity {
    private DatabaseReference mdbRef;
    private FirebaseDatabase mfirebaseDb;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mfirebaseDb = FirebaseDatabase.getInstance();
        mdbRef = mfirebaseDb.getReference().child("MBlog");
        mdbRef.keepSynced(true);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add :
                if(mUser != null && mAuth != null){
                    startActivity(new Intent(postListActivity.this, AddPostActivity.class));
                    finish();
                }
                break;
            case R.id.action_signout:
                if(mUser != null && mAuth != null){
                    mAuth.signOut();
                    startActivity(new Intent(postListActivity.this,LoginActivity.class));
                    finish();
                }

        }
        return super.onOptionsItemSelected(item);
    }
}
