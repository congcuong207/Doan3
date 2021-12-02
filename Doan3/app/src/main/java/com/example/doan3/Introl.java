package com.example.doan3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.doan3.Activity.Login;
import com.example.doan3.Activity.giaodienchinh;
import com.example.doan3.Empty.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Introl extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public  static Account information;
    DatabaseReference reference;
    public static String next=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introl);
        sharedPreferences=getSharedPreferences("dataLogin",MODE_PRIVATE);
        next=sharedPreferences.getString("phonenumber",null);
        if(next!=null)
        {
            reference= FirebaseDatabase.getInstance().getReference("Account");
            reference.addValueEventListener(new ValueEventListener() {
                Account acc1;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        acc1=snapshot1.getValue(Account.class);
                        if(("+84"+acc1.getSdt()).equalsIgnoreCase(next))
                        {
                            information=acc1;
                            break;
                        }

                    }

                    Intent intent=new Intent(Introl.this, giaodienchinh.class);
                    startActivity(intent);
                    //finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            Intent intent=new Intent(Introl.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}