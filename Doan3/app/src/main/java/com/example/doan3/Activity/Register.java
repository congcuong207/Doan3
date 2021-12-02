package com.example.doan3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doan3.Empty.Account;
import com.example.doan3.Empty.User;
import com.example.doan3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    Button btn;
    TextInputEditText taikhoan,matkhau,hoten,gioitinh,ngaysinh,sdt;
    FirebaseAuth mAuth;
    public static Account ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Init();
        mAuth=FirebaseAuth.getInstance();
        btn=findViewById(R.id.btn_dangky);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strPhoneNumber=sdt.getText().toString().trim();
                onClickVerifyPhonwNumber("+84"+strPhoneNumber);
            }
        });
    }
    private void onClickVerifyPhonwNumber(String strPhoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Register.this,"False",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                goToEnterOtpActivity(strPhoneNumber,s);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToMainActivity(user.getPhoneNumber());

                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    private void goToMainActivity(String phoneNumber)
    {
        Intent intent=new Intent(this,giaodienchinh.class);
        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
    }
    private void goToEnterOtpActivity(String strPhoneNumber,String verificationId)
    {
        Intent intent=new Intent(this,EnterOtpActivity.class);
        intent.putExtra("phone_number",strPhoneNumber);
        intent.putExtra("verification_id",verificationId);
        ac=new Account(taikhoan.getText().toString().trim(),hoten.getText().toString().trim(),gioitinh.getText().toString().trim(),ngaysinh.getText().toString().trim(),sdt.getText().toString().trim(),matkhau.getText().toString().trim(),"https://thelifetank.com/wp-content/uploads/2018/08/avatar-default-icon.png",0);
        startActivity(intent);
    }
    public void Init()
    {
        taikhoan=findViewById(R.id.edt_taikhoan);
        matkhau=findViewById(R.id.edt_matkhau);
        hoten=findViewById(R.id.edt_hoten);
        gioitinh=findViewById(R.id.edt_gioitinh);
        ngaysinh=findViewById(R.id.edt_ngaysinh);
        sdt=findViewById(R.id.edt_sodienthoai);
    }
}