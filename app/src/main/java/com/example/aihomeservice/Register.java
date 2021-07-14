package com.example.aihomeservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.aihomeservice.module.CreateUser;
import com.example.aihomeservice.module.CreateUserResult;
import com.tapadoo.alerter.Alerter;

import java.util.Timer;
import java.util.TimerTask;

import its.homeai.connect.NetworkProvider2;
import its.homeai.connect.OTPCommunication;
import its.homeai.connect.OTPResponse;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private static final String TAG =  "Register";
    Button btnSignUp,btnAdvanceSignUp;
    int chose=0;
    ImageView btnBack;
    EditText etPhone,etName,etMail;
    String phoneStr,nameStr,mailStr;
    Boolean checkProgress=false;
    String accessToken;
    CreateUser createUser = new CreateUser();
    String checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accessToken = getIntent().getStringExtra("access_token");
        phoneStr = getIntent().getStringExtra("phoneStr");
        initView();
    }
    private void initView() {
        btnSignUp = findViewById(R.id.btn_register5);
        btnAdvanceSignUp = findViewById(R.id.btn_register6);
        etPhone = findViewById(R.id.et_phone);
        etName = findViewById(R.id.et_name);
        etMail = findViewById(R.id.et_mail);
        btnBack = findViewById(R.id.im_back);
        //   btnBack2 = findViewById(R.id.btnBack2);
        etPhone.setText(phoneStr);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneStr = etPhone.getText().toString();
                nameStr = etName.getText().toString();
                mailStr = etMail.getText().toString();
                if (phoneStr.length() == 10 && nameStr.length() > 0 && mailStr.length() > 0) {
                    createUser.setPhoneString(phoneStr);
                    createUser.setNameString(nameStr);
                    createUser.setEmailString(mailStr);
                    CreateUserResult myResult = createUser.createUserResult();
                    System.out.println("result: " + myResult);
                    if (myResult != null) {
                        if(myResult.getStatus_code()!=null){
                            switch (myResult.getStatus_code()) {
                                case 0:
                                    showAlertTextOnlyError("Thất bại");
                                    checkProgress = true;
                                    break;
                                case 1:
                                    chose = 0;
                                    OTPCommunication otpCommunication = NetworkProvider2.self().getRetrofit().create(OTPCommunication.class);
                                    RequestBody speaker = RequestBody.create(MediaType.parse("multipart/form-data"),phoneStr);
                                    otpCommunication.sendOTP(speaker)
                                            .enqueue(new Callback<OTPResponse>() {
                                                @Override
                                                public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                                                    System.out.println("AAAAAAAAAAAA: "+response.body());
                                                    if(response.body().getStatus()==0){
                                                        //   Toast.makeText(SignUp.this,"Mã OTP đang được gửi tới quý khách",Toast.LENGTH_SHORT).show();
                                                        showAlertTextOnly("Mã OTP đang được gửi tới quý khách");
                                                        Log.i(TAG,"Gửi OTP thành công");
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            public void run() {
                                                                // yourMethod();
                                                                sendUserToSignUp();
                                                            }
                                                        }, 2000);


                                                    }else if(response.body().getStatus()==0){
                                                        //   Toast.makeText(SignUp.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                        showAlertTextOnlyError("Lỗi gửi mã OTP từ API");
                                                        Log.e(TAG,"Lỗi gửi mã OTP từ API");
                                                    }else {
                                                        //   Toast.makeText(SignUp.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                        showAlertTextOnlyError("Lỗi gửi mã OTP từ hệ thống");
                                                        Log.e(TAG,"Lỗi gửi mã OTP từ hệ thống");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<OTPResponse> call, Throwable t) {
                                                    //Toast.makeText(Register.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                    showAlertTextOnlyError("Lỗi khi gửi mã OTP");
                                                    Log.e(TAG,"Lỗi gửi mã OTP từ hệ thống");
                                                }
                                            });
                                    checkProgress = true;
                                    //sendUserToSignUp();
                                    break;
                                case 2:
                                    showAlertTextOnlyError("Số điện thoại chưa đúng định dạng");
                                    checkProgress = true;
                                    break;
                                case 3:
                                    chose=0;
                                    showAlertTextOnlyError("Số điện thoại đã tồn tại");
//                                    Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        public void run() {
//
//                                        }
//                                    }, 2000);
                                    OTPCommunication otpCommunication2 = NetworkProvider2.self().getRetrofit().create(OTPCommunication.class);
                                    RequestBody speaker2 = RequestBody.create(MediaType.parse("multipart/form-data"),phoneStr);
                                    otpCommunication2.sendOTP(speaker2)
                                            .enqueue(new Callback<OTPResponse>() {
                                                @Override
                                                public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                                                    System.out.println("AAAAAAAAAAAA: "+response.body());
                                                    if(response.body().getStatus()==0){
                                                        //   Toast.makeText(SignUp.this,"Mã OTP đang được gửi tới quý khách",Toast.LENGTH_SHORT).show();
                                                        //   showAlertTextOnly("Mã OTP đang được gửi tới quý khách");
                                                        Log.i(TAG,"Gửi OTP thành công");
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            public void run() {
                                                                // yourMethod();
                                                                sendUserToSignUp();
                                                            }
                                                        }, 2000);


                                                    }else if(response.body().getStatus()==0){
                                                        //   Toast.makeText(SignUp.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                        //   showAlertTextOnly("Lỗi gửi mã OTP từ API");
                                                        Log.e(TAG,"Lỗi gửi mã OTP từ API");
                                                    }else {
                                                        //   Toast.makeText(SignUp.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                        //   showAlertTextOnly("Lỗi gửi mã OTP từ hệ thống");
                                                        Log.e(TAG,"Lỗi gửi mã OTP từ hệ thống");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<OTPResponse> call, Throwable t) {
                                                    Toast.makeText(Register.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                    Log.e(TAG,"Lỗi gửi mã OTP từ hệ thống");
                                                }
                                            });
                                    checked = "true2";
                                    //sendUserToSignUp();
                                    checkProgress = true;
                                    break;
                                case 4:
                                    showAlertTextOnlyError("Email đã tồn tại");
                                    checkProgress = true;
                                    break;
                                default:
                                    showAlertTextOnlyError("Lỗi hệ thống");
                                    checkProgress = true;
                            }
                        }else {
                            showAlertTextOnly("Lỗi hệ thống");
                        }

                    }
                } else if (phoneStr.length() < 10&&phoneStr.length()>0) {
                    showAlertTextOnlyError("Vui lòng nhập đúng định dạng số điện thoại");
                    checkProgress = true;
                }
//                else if(phoneStr.length() ==0) {
//                    showAlertTextOnly("Vui lòng nhập đúng định dạng số điện thoại");
//                    checkProgress = true;
//                }
                else
                {
                    showAlertTextOnlyError("Vui lòng điền đầy đủ thông tin");
                    checkProgress = true;
                }

                if (checkProgress) {

                    checkProgress = false;
                }
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnAdvanceSignUp.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });
        btnAdvanceSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // progressBar.setVisibility(View.VISIBLE);

                phoneStr = etPhone.getText().toString();
                nameStr = etName.getText().toString();
                mailStr = etMail.getText().toString();
                if (phoneStr.length() == 10 && nameStr.length() > 0 && mailStr.length() > 0) {
                    createUser.setPhoneString(phoneStr);
                    createUser.setNameString(nameStr);
                    createUser.setEmailString(mailStr);
                    CreateUserResult myResult = createUser.createUserResult();
                    System.out.println("result: " + myResult);
                    if (myResult != null) {
                        if(myResult.getStatus_code()!=null){
                            switch (myResult.getStatus_code()) {
                                case 0:
                                    showAlertTextOnlyError("Thất bại");
                                    checkProgress = true;
                                    break;
                                case 1:
                                    chose = 1;
                                    OTPCommunication otpCommunication = NetworkProvider2.self().getRetrofit().create(OTPCommunication.class);
                                    RequestBody speaker = RequestBody.create(MediaType.parse("multipart/form-data"),phoneStr);
                                    otpCommunication.sendOTP(speaker)
                                            .enqueue(new Callback<OTPResponse>() {
                                                @Override
                                                public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                                                    System.out.println("AAAAAAAAAAAA: "+response.body());
                                                    if(response.body().getStatus()==0){
                                                        //   Toast.makeText(SignUp.this,"Mã OTP đang được gửi tới quý khách",Toast.LENGTH_SHORT).show();
                                                           showAlertTextOnly("Mã OTP đang được gửi tới quý khách");
                                                        Log.i(TAG,"Gửi OTP thành công");
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            public void run() {
                                                                // yourMethod();
                                                                sendUserToSignUp();
                                                            }
                                                        }, 2000);


                                                    }else if(response.body().getStatus()==0){
                                                        //   Toast.makeText(SignUp.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                           showAlertTextOnlyError("Lỗi gửi mã OTP từ API");
                                                        Log.e(TAG,"Lỗi gửi mã OTP từ API");
                                                    }else {
                                                        //   Toast.makeText(SignUp.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                        showAlertTextOnlyError("Lỗi gửi mã OTP từ hệ thống");
                                                        Log.e(TAG,"Lỗi gửi mã OTP từ hệ thống");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<OTPResponse> call, Throwable t) {
                                                    //Toast.makeText(Register.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                    showAlertTextOnlyError("Lỗi khi gửi mã OTP");
                                                    Log.e(TAG,"Lỗi gửi mã OTP từ hệ thống");
                                                }
                                            });
                                    checkProgress = true;
                                    //sendUserToSignUp();
                                    break;
                                case 2:
                                    showAlertTextOnlyError("Số điện thoại chưa đúng định dạng");
                                    checkProgress = true;
                                    break;
                                case 3:
                                    chose=1;
                                    showAlertTextOnlyError("Số điện thoại đã tồn tại");
//                                    Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        public void run() {
//
//                                        }
//                                    }, 2000);
                                    OTPCommunication otpCommunication2 = NetworkProvider2.self().getRetrofit().create(OTPCommunication.class);
                                    RequestBody speaker2 = RequestBody.create(MediaType.parse("multipart/form-data"),phoneStr);
                                    otpCommunication2.sendOTP(speaker2)
                                            .enqueue(new Callback<OTPResponse>() {
                                                @Override
                                                public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                                                    System.out.println("AAAAAAAAAAAA: "+response.body());
                                                    if(response.body().getStatus()==0){
                                                        //   Toast.makeText(SignUp.this,"Mã OTP đang được gửi tới quý khách",Toast.LENGTH_SHORT).show();
                                                        //   showAlertTextOnly("Mã OTP đang được gửi tới quý khách");
                                                        Log.i(TAG,"Gửi OTP thành công");
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            public void run() {
                                                                // yourMethod();
                                                                sendUserToSignUp();
                                                            }
                                                        }, 2000);


                                                    }else if(response.body().getStatus()==0){
                                                        //   Toast.makeText(SignUp.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                        //   showAlertTextOnly("Lỗi gửi mã OTP từ API");
                                                        Log.e(TAG,"Lỗi gửi mã OTP từ API");
                                                    }else {
                                                        //   Toast.makeText(SignUp.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                        //   showAlertTextOnly("Lỗi gửi mã OTP từ hệ thống");
                                                        Log.e(TAG,"Lỗi gửi mã OTP từ hệ thống");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<OTPResponse> call, Throwable t) {
                                                    Toast.makeText(Register.this,"Lỗi khi gửi mã OTP",Toast.LENGTH_SHORT).show();
                                                    Log.e(TAG,"Lỗi gửi mã OTP từ hệ thống");
                                                }
                                            });
                                    checked = "true2";
                                    //sendUserToSignUp();
                                    checkProgress = true;
                                    break;
                                case 4:
                                    showAlertTextOnlyError("Email đã tồn tại");
                                    checkProgress = true;
                                    break;
                                default:
                                    showAlertTextOnlyError("Lỗi hệ thống");
                                    checkProgress = true;
                            }
                        }else {
                            showAlertTextOnly("Lỗi hệ thống");
                        }

                    }
                } else if (phoneStr.length() < 10&&phoneStr.length()>0) {
                    showAlertTextOnlyError("Vui lòng nhập đúng định dạng số điện thoại");
                    checkProgress = true;
                }
//                else if(phoneStr.length() ==0) {
//                    showAlertTextOnly("Vui lòng nhập đúng định dạng số điện thoại");
//                    checkProgress = true;
//                }
                else
                {
                    showAlertTextOnlyError("Vui lòng điền đầy đủ thông tin");
                    checkProgress = true;
                }

                if (checkProgress) {

                    checkProgress = false;
                }
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnAdvanceSignUp.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUsertoMain();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnBack.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });

    }
    private void sendUsertoMain() {

        Intent mainIntent = new Intent(Register.this, SignUp.class);
        mainIntent.putExtra("access_token",accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
        mainIntent.putExtra("chose",chose);
        startActivity(mainIntent);
        finish();
    }

    private void sendUserToSignUp() {
        Intent intent = new Intent(Register.this,Home.class);
        intent.putExtra("access_token",accessToken);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("chose",chose);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // your code.

        Intent mainIntent = new Intent(Register.this, SignUp.class);
        mainIntent.putExtra("access_token",accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
        mainIntent.putExtra("chose",chose);
        startActivity(mainIntent);
        finish();
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