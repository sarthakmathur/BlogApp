package com.example.appdev.blogapp.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appdev.blogapp.Model.Blog;
import com.example.appdev.blogapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPostActivity extends AppCompatActivity {
    private ImageButton mPostImage;
    private EditText mPostTitle;
    private EditText mPostDescription;
    private Button mSubmitButton;
    private DatabaseReference mPostDb;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog mProgressDialog;
    private static final int GALLERY_CODE = 1;
    private Uri mImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mPostDb = FirebaseDatabase.getInstance().getReference().child("MBlog");
        mPostTitle = (EditText) findViewById(R.id.addPostTitleId);
        mPostDescription = (EditText) findViewById(R.id.addPostDescId);
        mPostImage = (ImageButton) findViewById(R.id.imageButton);
        mSubmitButton = (Button) findViewById(R.id.submitButtonId);

        mProgressDialog = new ProgressDialog(this);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //now we start posting
               startPosting();
            }
        });

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,GALLERY_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK){
            mImageUri = data.getData();
            mPostImage.setImageURI(mImageUri);
        }
    }

    private void startPosting() {
        mProgressDialog.setMessage("uploading to blog...");
        mProgressDialog.show();
        String titleVal = mPostTitle.getText().toString().trim();
        String descVal = mPostDescription.getText().toString().trim();
        if(!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal)){
            //we are good to go , start uploading
            Blog blog = new Blog("title","desc","imageUrl","timestamp","userid");
            mPostDb.setValue(blog).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Item added!!",Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            });
        }
    }
}
