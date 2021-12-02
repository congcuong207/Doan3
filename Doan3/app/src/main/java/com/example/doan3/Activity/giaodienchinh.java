package com.example.doan3.Activity;

import static com.example.doan3.Introl.next;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.doan3.Introl.information;

import com.example.doan3.Adapter.DanhbaAdapter;
import com.example.doan3.Empty.Account;
import com.example.doan3.Empty.Chat;
import com.example.doan3.R;
import com.example.doan3.Trangcanhan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;

public class giaodienchinh extends AppCompatActivity {
    ImageView tmv_avatar;
    BottomNavigationView bottomNavigationView;
    int REQUEST_CODE=123;
    ArrayList<Account> arrayList2;
    ArrayList<String> uerList;
    FirebaseUser firebaseUser;
    //public static Account information;
    ImageView exittologin, camera;
    ListView lv;
    SharedPreferences sharedPreferences;
    DatabaseReference db;
    DanhbaAdapter adapter;
    public static String IdNguoinhan = "";
    public static String AvtNguoinhan = "";
    public static String HotenNguoinhan = "";
    public static int ktvideo = 0;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giaodienchinh);
        Init();
        arrayList2 = new ArrayList<>();
        uerList = new ArrayList<>();
        adapter = new DanhbaAdapter(giaodienchinh.this, R.layout.danhbacuttom, arrayList2);
        lv.setAdapter(adapter);
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        if (next != null) {
            Picasso.get().load(Uri.parse(information.getAvatar())).into(tmv_avatar);
        } else {
            Picasso.get().load(Uri.parse(information.getAvatar())).into(tmv_avatar);

        }
        db = FirebaseDatabase.getInstance().getReference("Chats");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                uerList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Chat acc = snapshot1.getValue(Chat.class);
                    if (acc.getNguoigui().equalsIgnoreCase(information.getTaikhoan())) {
                        uerList.add(acc.getNguoinhan());
                    } else if (acc.getNguoinhan().equalsIgnoreCase(information.getTaikhoan())) {
                        uerList.add(acc.getNguoigui());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IdNguoinhan = arrayList2.get(position).getTaikhoan();
                AvtNguoinhan = arrayList2.get(position).getAvatar();
                HotenNguoinhan = arrayList2.get(position).getHoten();
                ktvideo=arrayList2.get(position).getKt();
                startActivity(new Intent(giaodienchinh.this, MainActivity.class));
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        tmv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(giaodienchinh.this, Trangcanhan.class));
            }
        });
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_danhba:
                        startActivity(new Intent(giaodienchinh.this, danhbaactivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){
            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            Uri imageFileUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
            try {
                OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 98, imageFileOS);
            } catch (FileNotFoundException e) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ấn thêm 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    private void Init() {
        lv = findViewById(R.id.lv_account_chat);
        camera = findViewById(R.id.imv_camera);
        tmv_avatar = findViewById(R.id.imvAvatar);

    }
    boolean kt=true;
    private void readChats() {
        db = FirebaseDatabase.getInstance().getReference("Account");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList2.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Account acc = snapshot1.getValue(Account.class);
                    kt=true;
                    for (int j = 0; j < uerList.size(); j++) {
                        if (acc.getTaikhoan().equalsIgnoreCase(uerList.get(j))) {
                            if (arrayList2.size() != 0) {
                                for (int i = 0; i < arrayList2.size(); i++) {
                                    if (acc.getTaikhoan().trim().equalsIgnoreCase(arrayList2.get(i).getTaikhoan().trim())) {
                                        kt=false;
                                    }
                                }
                                if(kt)
                                {
                                    arrayList2.add(acc);
                                }
                            } else {
                                arrayList2.add(acc);
                            }

                        }

                    }

                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}