package com.example.doan3.Database;

import android.content.Context;
import android.widget.Toast;

import com.example.doan3.Empty.Account;
import com.example.doan3.Empty.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBContext {
    public void addAccount(Account acc, Context context){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
            db.child("Account").push().setValue(acc).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(context,"Thành công",Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
    public void addChats(Chat ch){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        db.child("Account").push().setValue(ch).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful()){
                }
            }
        });

    }

    public void addAccount(Account acc){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        db.child("Account").push().setValue(acc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful()){
                }
            }
        });

    }
    public ArrayList<Account> getDataAccount()
    {
        ArrayList<Account> accounts=new ArrayList<>();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Account");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Account acc=snapshot1.getValue(Account.class);
                    accounts.add(new Account(acc.getTaikhoan(), acc.getHoten(), acc.getGioitinh(), acc.getNgaysinh(),acc.getSdt(),acc.getMatkhau(),acc.getAvatar(),acc.getKt()));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return accounts;
    }
}
