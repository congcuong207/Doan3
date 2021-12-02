package com.example.doan3.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

import com.example.doan3.Adapter.DanhbaAdapter;
import com.example.doan3.Database.DBContext;
import com.example.doan3.Empty.Account;
import com.example.doan3.R;
import com.example.doan3.Trangcanhan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.doan3.Introl.information;
import static com.example.doan3.Activity.giaodienchinh.IdNguoinhan;
import static com.example.doan3.Activity.giaodienchinh.AvtNguoinhan;
import static com.example.doan3.Activity.giaodienchinh.HotenNguoinhan;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;

public class danhbaactivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ListView lv;
    DanhbaAdapter adapter;
    ArrayList<Account>accounts;
    ImageView imvAvt,camera;
    DBContext dbContext;
    int REQUEST_CODE=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhbaactivity);
        dbContext=new DBContext();
        lv=findViewById(R.id.lv_danhba);
        imvAvt=findViewById(R.id.imvAvatardanhba);
        camera=findViewById(R.id.imv_camera);

        Picasso.get().load(Uri.parse(information.getAvatar())).into(imvAvt);
        imvAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(danhbaactivity.this, Trangcanhan.class));
            }
        });
        accounts=new ArrayList<>();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Account");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Account acc=snapshot1.getValue(Account.class);
                    if(!acc.getTaikhoan().equalsIgnoreCase(information.getTaikhoan())) {
                        accounts.add(new Account(acc.getTaikhoan(), acc.getHoten(), acc.getGioitinh(), acc.getNgaysinh(), acc.getSdt(), acc.getMatkhau(), acc.getAvatar(),acc.getKt()));
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        onData();
        bottomNavigationView=findViewById(R.id.bottom_navigation_danhba);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.menu_mess:
                        startActivity(new Intent(danhbaactivity.this,giaodienchinh.class));
                        finish();
                        break;
                }
                return false;
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE);
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
    public void onData()
    {
            adapter=new DanhbaAdapter(danhbaactivity.this,R.layout.danhbacuttom,accounts);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    IdNguoinhan=accounts.get(position).getTaikhoan();
                    AvtNguoinhan=accounts.get(position).getAvatar();
                    HotenNguoinhan=accounts.get(position).getHoten();
                    startActivity(new Intent(danhbaactivity.this,MainActivity.class));
                }
            });

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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}