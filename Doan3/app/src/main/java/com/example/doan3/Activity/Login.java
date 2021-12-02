package com.example.doan3.Activity;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan3.AccountCommon;
import com.example.doan3.Database.DBContext;
import com.example.doan3.Empty.Account;
import com.example.doan3.Empty.Chat;
import com.example.doan3.R;
import com.example.doan3.ValidateData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import static com.example.doan3.Introl.next;

public class Login extends AppCompatActivity {
    TextView tv_dangky;
    TextInputEditText edt_account, edt_password;
    TextInputLayout edt_noti_account,edt_noti_password;
    TextView  btn_forgot, btn_login;
    FirebaseUser firebaseUser;
    SharedPreferences sharedPreferences;
    DatabaseReference reference;

    ArrayList<Account> accounts;
    public static DBContext dbContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences=getSharedPreferences("dataLogin",MODE_PRIVATE);
        initView();
        dbContext=new DBContext();
        accounts= dbContext.getDataAccount();
        tv_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
        onClick();
    }
    private void initView() {
        edt_account = findViewById(R.id.edt_account_login);
        edt_password = findViewById(R.id.edt_password_login);
        tv_dangky=findViewById(R.id.btn_register_login);
        btn_forgot = findViewById(R.id.btn_forgot_login);
        btn_login = findViewById(R.id.btn_login_login);
        edt_noti_account = findViewById(R.id.edt_noti_account_login);
        edt_noti_password = findViewById(R.id.edt_noti_password_login);
    }
    private void onClick() {

        //open RegisterActivity
        tv_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_noti_account.setError("");
                edt_noti_password.setError("");
                if(!ValidateData.checkEmpty(edt_account.getText().toString())){
                    edt_noti_account.setError("Tài khoản không được để trống");
                }
                if(!ValidateData.checkEmpty(edt_password.getText().toString())){
                    edt_noti_password.setError("Mật khẩu không được để trống");
                }

                if(ValidateData.checkEmpty(edt_account.getText().toString())&&ValidateData.checkEmpty(edt_password.getText().toString())){
                    int i = AccountCommon.Login(accounts,edt_account.getText().toString(),edt_password.getText().toString());

                    switch (i){
                        case -1:
                            edt_noti_account.setError("Tài khoản không chính xác");
                            break;
                        case 0:
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("phonenumber",AccountCommon.SDT);
                            editor.commit();
                            startActivity(new Intent(Login.this, giaodienchinh.class));
                            break;
                        case 2:
                            edt_noti_password.setError("Mật khẩu không chính xác");
                            break;
                    }
                }

            }
        });

    }
}