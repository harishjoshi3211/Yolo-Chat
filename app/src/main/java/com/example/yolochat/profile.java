package com.example.yolochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class profile extends AppCompatActivity {


    TextView name,status,profile_loc,profile_prof;
    Button statusbtn,upload_btn,epi;
    ImageView image_upload;
    final int PICK_Image=10;
    private DatabaseReference mDatabase;
    private Uri filePath=null;
    private FirebaseUser user;
    private StorageReference storageReference,fordisplayref;
    private DatabaseReference databaseReferencefordp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.name);
        status=findViewById(R.id.status);
        statusbtn=findViewById(R.id.statusbtn);
        upload_btn=findViewById(R.id.upload_btn);
        image_upload=findViewById(R.id.image_upload);
        profile_loc=findViewById(R.id.profile_loc);
        profile_prof=findViewById(R.id.profile_prof);
        epi=findViewById(R.id.epi);

        user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        storageReference=FirebaseStorage.getInstance().getReference().child("users").child(uid);
        databaseReferencefordp=FirebaseDatabase.getInstance().getReference().child(uid);


        epi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(profile.this,edit_personal_details.class);
                startActivity(intent);
            }
        });
        /*databaseReferencefordp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot!=null) {

                    //fordisplayref = storageReference.child(uid).child(snapshot.getValue().toString());
                    //Toast.makeText(profile.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                    //show_image(fordisplayref);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



        mDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name.setText(snapshot.child("name").getValue().toString());
                status.setText(snapshot.child("status").getValue().toString());
                profile_loc.setText(snapshot.child("location").getValue().toString());
                profile_prof.setText(snapshot.child("profession").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        statusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(profile.this,change_status.class);
                startActivity(intent);
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
                uploadImage();
            }

        });

    }

    private void show_image(StorageReference ref)
    {
        GlideApp.with(profile.this)
                .load(ref)
                .into(image_upload);
    }

    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_Image);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_Image
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            //Toast.makeText(profile.this,filePath.toString(),Toast.LENGTH_SHORT).show();

            // Get the Uri of data
            filePath = data.getData();
            Toast.makeText(profile.this,filePath.toString(),Toast.LENGTH_SHORT).show();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                int width=Math.round(Math.min((float)400,bitmap.getWidth()));
                int height=Math.round(Math.min((float)400,bitmap.getWidth()));
                Bitmap bm=getResizedBitmap(bitmap,width,height);
                image_upload.setImageBitmap(bm);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading


            // Defining the child of storageReference
            String z="images"+UUID.randomUUID().toString();

            StorageReference ref
                    = storageReference
                    .child(z);

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    databaseReferencefordp.setValue(z);



                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    //progressDialog.dismiss();
                                    Toast
                                            .makeText(profile.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            //progressDialog.dismiss();
                            Toast
                                    .makeText(profile.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    /*progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");*/
                                }
                            });
        }
    }




        public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// create a matrix for the manipulation

        Matrix matrix = new Matrix();

// resize the bit map

        matrix.postScale(scaleWidth, scaleHeight);

// recreate the new Bitmap

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }

}
