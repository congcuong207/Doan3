package com.example.doan3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan3.Database.DBContext;
import com.example.doan3.Empty.Account;
import com.example.doan3.Introl;
import com.example.doan3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class EnterOtpActivity extends AppCompatActivity {
    EditText edtOtp;
    Button btnSendOtp;
    TextView tvguilaiotp;
    FirebaseAuth mAuth;
    String mPhoneNumber;
    String mVerification;
    Account ac;
    PhoneAuthProvider.ForceResendingToken mforceResendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        getDataIntent();
        Init();
        mAuth=FirebaseAuth.getInstance();

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOtp=edtOtp.getText().toString().trim();
                onClickSendOtpCode(strOtp);
            }
        });
        tvguilaiotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendOtpAgain();
            }
        });

    }
    private void getDataIntent()
    {

        mPhoneNumber=getIntent().getStringExtra("phone_number");
        mVerification=getIntent().getStringExtra("verification_id");
    }

    private void onClickSendOtpCode(String strOtp)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerification, strOtp);
        signInWithPhoneAuthCredential(credential);
    }
    private void onClickSendOtpAgain()
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setForceResendingToken(mforceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(EnterOtpActivity.this,"False",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                mVerification=s;
                                mforceResendingToken=forceResendingToken;
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void Init()
    {
        edtOtp=findViewById(R.id.edt_nhapotp);
        btnSendOtp=findViewById(R.id.btn_nhapotp);
        tvguilaiotp=findViewById(R.id.guilaiotp);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            goToMainActivity(user.getPhoneNumber());

                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(EnterOtpActivity.this,"Lỗi !!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    private void goToMainActivity(String phoneNumber)
    {
        Intent intent=new Intent(EnterOtpActivity.this, Introl.class);
        DBContext db=new DBContext();
        db.addAccount(Register.ac);
//        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
    }
}