package com.example.aihomeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aihomeservice.module.CheckAuth;
import com.tapadoo.alerter.Alerter;

import its.homeai.connect.CheckUserCommunication;
import its.homeai.connect.CheckUserResponse;
import its.homeai.connect.NetworkProvider2;
import its.homeai.connect.OTPCommunication;
import its.homeai.connect.OTPResponse;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    Button btnSignUp,btnLogin;
    EditText etPhone;
    int chose=0;
    String phoneStr,accessToken;
    SessionManager sessionManager;
    CheckAuth checkAuth = new CheckAuth();
    String checked;
    final int REQUEST_PERMISSION_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        phoneStr = getIntent().getStringExtra("phoneStr");
        checkAuth.setApi_keyString("b432193c-57e6-41a6-968f-375c60b69fdd");
        checked = getIntent().getStringExtra("checked");
        chose = getIntent().getIntExtra("chose",0);
        System.out.println("Check: "+checked);
        //System.out.println("Access-token: " + checkAuth.checkAuthResponse());
        accessToken = checkAuth.checkAuthResponse().getAccess_token();

        if (!checkPermissionFromDevice())
            requestPermission();
        initView();
    }

    private void initView() {
        btnLogin = findViewById(R.id.btn_register3);
        btnSignUp = findViewById(R.id.btn_register4);
        etPhone = findViewById(R.id.et_phone);
        etPhone.setText(phoneStr);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chose = 1;
                phoneStr =  etPhone.getText().toString();
                sendUserToRegister();
//                phoneStr=  etPhone.getText().toString();
//                if(phoneStr.length()==0){
//                    //Toast.makeText(SignUp.this, "Vui l??ng ??i???n s??? ??i???n tho???i", Toast.LENGTH_SHORT).show();
//                    showAlertTextOnlyError("Vui l??ng ??i???n s??? ??i???n tho???i");
//                }else if(phoneStr.length()!=10){
//                    showAlertTextOnlyError("Vui l??ng ??i???n ????ng ?????nh d???ng s??? ??i???n tho???i");
//                    //Toast.makeText(SignUp.this, "Vui l??ng ??i???n ????ng ?????nh d???ng s??? ??i???n tho???i", Toast.LENGTH_SHORT).show();
//                }else {
//                    showAlertTextOnly("??ang g???i m?? OTP t???i ??i???n tho???i c???a qu?? kh??ch");
////                    Toast.makeText(SignUp.this, "??ang g???i m?? OTP t???i ??i???n tho???i c???a qu?? kh??ch", Toast.LENGTH_SHORT).show();
////                    sendUserToHome();
//
//                    OTPCommunication otpCommunication = NetworkProvider2.self().getRetrofit().create(OTPCommunication.class);
//                    RequestBody speaker = RequestBody.create(MediaType.parse("multipart/form-data"),phoneStr);
//                    otpCommunication.sendOTP(speaker)
//                            .enqueue(new Callback<OTPResponse>() {
//                                @Override
//                                public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
//                                    System.out.println("AAAAAAAAAAAA: "+response.body());
//                                    if(response.body().getStatus()==0){
//                                        //   Toast.makeText(SignUp.this,"M?? OTP ??ang ???????c g???i t???i qu?? kh??ch",Toast.LENGTH_SHORT).show();
//                                        //   showAlertTextOnly("M?? OTP ??ang ???????c g???i t???i qu?? kh??ch");
//                                        Log.i(TAG,"G???i OTP th??nh c??ng");
//                                        Handler handler = new Handler();
//                                        handler.postDelayed(new Runnable() {
//                                            public void run() {
//                                                // yourMethod();
//                                                sendUserToHome();
//                                            }
//                                        }, 2000);
//
//
//                                    }else if(response.body().getStatus()==0){
//                                        //   Toast.makeText(SignUp.this,"L???i khi g???i m?? OTP",Toast.LENGTH_SHORT).show();
//                                        //   showAlertTextOnly("L???i g???i m?? OTP t??? API");
//                                        Log.e(TAG,"L???i g???i m?? OTP t??? API");
//                                    }else {
//                                        //   Toast.makeText(SignUp.this,"L???i khi g???i m?? OTP",Toast.LENGTH_SHORT).show();
//                                        //   showAlertTextOnly("L???i g???i m?? OTP t??? h??? th???ng");
//                                        Log.e(TAG,"L???i g???i m?? OTP t??? h??? th???ng");
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<OTPResponse> call, Throwable t) {
//                                    Toast.makeText(SignUp.this,"L???i khi g???i m?? OTP",Toast.LENGTH_SHORT).show();
//                                    Log.e(TAG,"L???i g???i m?? OTP t??? h??? th???ng");
//                                }
//                            });
//
//                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chose=0;
                phoneStr=  etPhone.getText().toString();
                if(phoneStr.length()==0){
                    //Toast.makeText(SignUp.this, "Vui l??ng ??i???n s??? ??i???n tho???i", Toast.LENGTH_SHORT).show();
                    showAlertTextOnlyError("Vui l??ng ??i???n s??? ??i???n tho???i");
                }else if(phoneStr.length()!=10){
                    showAlertTextOnlyError("Vui l??ng ??i???n ????ng ?????nh d???ng s??? ??i???n tho???i");
                    //Toast.makeText(SignUp.this, "Vui l??ng ??i???n ????ng ?????nh d???ng s??? ??i???n tho???i", Toast.LENGTH_SHORT).show();
                }else {
                      showAlertTextOnly("??ang g???i m?? OTP t???i ??i???n tho???i c???a qu?? kh??ch");
//                    Toast.makeText(SignUp.this, "??ang g???i m?? OTP t???i ??i???n tho???i c???a qu?? kh??ch", Toast.LENGTH_SHORT).show();
//                    sendUserToHome();

                    OTPCommunication otpCommunication = NetworkProvider2.self().getRetrofit().create(OTPCommunication.class);
                    RequestBody speaker = RequestBody.create(MediaType.parse("multipart/form-data"),phoneStr);
                    otpCommunication.sendOTP(speaker)
                            .enqueue(new Callback<OTPResponse>() {
                                @Override
                                public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                                    System.out.println("AAAAAAAAAAAA: "+response.body());
                                    if(response.body().getStatus()==0){
                                     //   Toast.makeText(SignUp.this,"M?? OTP ??ang ???????c g???i t???i qu?? kh??ch",Toast.LENGTH_SHORT).show();
                                     //   showAlertTextOnly("M?? OTP ??ang ???????c g???i t???i qu?? kh??ch");
                                        Log.i(TAG,"G???i OTP th??nh c??ng");
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                // yourMethod();
                                                sendUserToHome();
                                            }
                                        }, 2000);


                                    }else if(response.body().getStatus()==0){
                                     //   Toast.makeText(SignUp.this,"L???i khi g???i m?? OTP",Toast.LENGTH_SHORT).show();
                                     //   showAlertTextOnly("L???i g???i m?? OTP t??? API");
                                        Log.e(TAG,"L???i g???i m?? OTP t??? API");
                                    }else {
                                     //   Toast.makeText(SignUp.this,"L???i khi g???i m?? OTP",Toast.LENGTH_SHORT).show();
                                     //   showAlertTextOnly("L???i g???i m?? OTP t??? h??? th???ng");
                                        Log.e(TAG,"L???i g???i m?? OTP t??? h??? th???ng");
                                    }
                                }

                                @Override
                                public void onFailure(Call<OTPResponse> call, Throwable t) {
                                    Toast.makeText(SignUp.this,"L???i khi g???i m?? OTP",Toast.LENGTH_SHORT).show();
                                    Log.e(TAG,"L???i g???i m?? OTP t??? h??? th???ng");
                                }
                            });

                }
            }
        });

    }

    private void sendUserToRegister() {
        Intent intent = new Intent(SignUp.this,Register.class);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("chose",chose);
        intent.putExtra("access_token",accessToken);
        startActivity(intent);
        finish();
    }

    private void sendUserToHome() {
        Intent intent = new Intent(SignUp.this,Home.class);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("chose",chose);
        intent.putExtra("access_token",accessToken);
        startActivity(intent);
        finish();
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE, android.Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                   // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                else
                  //  showAlertTextOnlyError("C???p quy???n th???t b???i");
                 Toast.makeText(this, "C???p quy???n th???t b???i", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int call_phone = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED && call_phone == PackageManager.PERMISSION_GRANTED&&internet==PackageManager.PERMISSION_GRANTED;
    }

    private void showAlertTextOnly(String msg){
        Alerter.create(this).setBackgroundColorRes(R.color.colorAccent)
                .setText(msg)
                .show();
    }

    private void showAlertTextOnlyError(String msg){
        Alerter.create(this).setBackgroundColorRes(R.color.redColor)
                .setText(msg)
                .show();
    }
}