package com.example.doan3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.doan3.Activity.giaodienchinh;
import com.example.doan3.Empty.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import static com.example.doan3.Trangcanhan.namePicture;
import static com.example.doan3.Introl.information;


import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class ChooseAvatar extends AppCompatActivity {
ImageView imageView;
String name;
    int REQUEST_CODE_IMAGE=123;
    Uri mImageUri;
Button btn_luu,btn_chonlai;
    FirebaseStorage storage;StorageReference storageRef;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_avatar);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        imageView=findViewById(R.id.imvAvatar);
        btn_luu=findViewById(R.id.btn_luu);
        btn_luu.setEnabled(true);
        btn_chonlai=findViewById(R.id.btn_chonlai);
        Picasso.get().load(Uri.parse(namePicture)).into(imageView);
        btn_chonlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_luu.setEnabled(false);
                Calendar calendar=Calendar.getInstance();
                StorageReference mountainsRef=storageRef.child("image"+calendar.getTimeInMillis()+".png");
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                name= uri.toString();
                                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Account");
                                db.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded( DataSnapshot snapshot,  String previousChildName) {
                                        if(snapshot.getValue(Account.class).getTaikhoan().equalsIgnoreCase(information.getTaikhoan()))
                                        {
                                            snapshot.getRef().child("avatar").setValue(name, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete( DatabaseError error, DatabaseReference ref) {
                                                    if(error==null)
                                                    {
                                                        Intent intent=new Intent(ChooseAvatar.this, Introl.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onChildChanged( DataSnapshot snapshot,  String previousChildName) {

                                    }

                                    @Override
                                    public void onChildRemoved( DataSnapshot snapshot) {

                                    }

                                    @Override
                                    public void onChildMoved( DataSnapshot snapshot,  String previousChildName) {

                                    }

                                    @Override
                                    public void onCancelled( DatabaseError error) {

                                    }
                                });
                            }
                        });
                    }
                });

            }

        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            mImageUri=data.getData();
            Picasso.get().load(mImageUri).into(imageView);
            // Get the data from an ImageView as bytes
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}