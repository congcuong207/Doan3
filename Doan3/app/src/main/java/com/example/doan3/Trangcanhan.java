package com.example.doan3;

import static com.example.doan3.Introl.information;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan3.Activity.Login;
import com.example.doan3.Activity.giaodienchinh;
import com.example.doan3.Empty.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class Trangcanhan extends AppCompatActivity {
    LinearLayout ln_logout,lnlout_doiavatar;
    SharedPreferences sharedPreferences;
    int REQUEST_CODE_IMAGE=123;
    ImageView imageView;
    TextView nameAcc;
    public static String namePicture="";
    Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangcanhan);
        nameAcc=findViewById(R.id.nameAcc);
        ln_logout=findViewById(R.id.lnlout_logout);
        lnlout_doiavatar=findViewById(R.id.lnlout_doiavatar);
        imageView=findViewById(R.id.imvAvatar_accct);
        Picasso.get().load(Uri.parse(information.getAvatar())).into(imageView);
        nameAcc.setText(information.getHoten());

        lnlout_doiavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        ln_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("phonenumber");
                editor.commit();
                startActivity(new Intent(Trangcanhan.this, Login.class));
                finish();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            mImageUri=data.getData();
            namePicture=mImageUri+"";
            startActivity(new Intent(Trangcanhan.this,ChooseAvatar.class));
            // Get the data from an ImageView as bytes
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}