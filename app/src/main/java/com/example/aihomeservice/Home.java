package com.example.aihomeservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import its.homeai.connect.CheckOTPCommunication;
import its.homeai.connect.CheckOTPResponse;
import its.homeai.connect.NetworkProvider2;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    public static final String TAG = "HomeActivity";
    int checked=0,chose=0;
    Button btnCtn;
    EditText etOTP;
    TextView swipeLeft;
    String phoneStr,otpStr,accessToken;
    SessionManager session;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_home);
        accessToken = getIntent().getStringExtra("access_token");
        chose = getIntent().getIntExtra("chose",0);
        phoneStr = getIntent().getStringExtra("phoneStr");
        initView();
    }

    private void initView() {
        btnCtn = findViewById(R.id.btn_register4);
        etOTP = findViewById(R.id.et_otp);
        imageView = findViewById(R.id.im_back);
        swipeLeft = findViewById(R.id.swipeLeft);
        btnCtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpStr = etOTP.getText().toString();
                if(otpStr.length()==0){
                    showAlertTextOnlyError("Vui lòng điền mã OTP");
                    //Toast.makeText(Home.this, "Vui lòng điền số điện thoại", Toast.LENGTH_SHORT).show();
                }else if(otpStr.length()!=6){
                    showAlertTextOnlyError("Vui lòng điền đúng định dạng mã OTP");
                    //Toast.makeText(Home.this, "Vui lòng điền đúng định dạng mã OTP", Toast.LENGTH_SHORT).show();
                }else {
                        CheckOTPCommunication checkOTPCommunication = NetworkProvider2.self().getRetrofit().create(CheckOTPCommunication.class);
                        RequestBody speaker = RequestBody.create(MediaType.parse("multipart/form-data"), phoneStr);
                        RequestBody otp_code = RequestBody.create(MediaType.parse("multipart/form-data"), otpStr);
                        checkOTPCommunication.checkOTP(speaker,otp_code)
                                .enqueue(new Callback<CheckOTPResponse>() {
                                    @Override
                                    public void onResponse(Call<CheckOTPResponse> call, Response<CheckOTPResponse> response) {
                                        System.out.println(response.body().toString());
                                        if(response.body().getStatus()==0){
                                            if (response.body().getMsg().equalsIgnoreCase("OTP correct")){

                                                showAlertTextOnly("Xác thực thành công");
                                                //Toast.makeText(Home.this, "Xác thực thành công", Toast.LENGTH_SHORT).show();
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        // yourMethod();
                                                        if(chose==0){
                                                            session.createLoginSession(phoneStr);
                                                            sendUserToCall();
                                                        }else {
                                                            sendUserToSignUp();
                                                        }
                                                    }
                                                }, 2000);

                                                Log.i(TAG,"Xác thực thành công");
                                            }
                                            else if(response.body().getMsg().equalsIgnoreCase("OTP has not been sent")){
                                                checked=1;
                                                showAlertTextOnlyError("Xin quý khách vui lòng thử lại sau");
                                                //Toast.makeText(Home.this,"Xin quý khách vui lòng thử lại sau",Toast.LENGTH_SHORT).show();
                                                Log.i(TAG,"User chưa có mã OTP");
                                            }else {
                                                checked=2;
                                                //Toast.makeText(Home.this,"Mã OTP sai",Toast.LENGTH_SHORT).show();
                                                showAlertTextOnlyError("Mã OTP sai");
                                                Log.i(TAG, "Mã OTP sai");
                                            }
                                        }else {
                                            checked=3;
                                           // Toast.makeText(Home.this,"Mã OTP sai",Toast.LENGTH_SHORT).show();
                                            showAlertTextOnlyError("Mã OTP sai");
                                            Log.i(TAG,"Từ chối gửi mã");
                                        }
                                        btnCtn.setEnabled(true);
                                    }

                                    @Override
                                    public void onFailure(Call<CheckOTPResponse> call, Throwable t) {
                                        checked=3;
                                        Log.e(TAG,"Lỗi API");
                                        showAlertTextOnlyError("Lỗi API");
                                        btnCtn.setEnabled(true);
                                    }
                                });






                }
            }
        });
//        switch (checked){
//            case 1:
//                showAlertTextOnlyError("Xin quý khách vui lòng thử lại sau");
//                break;
//            case 2:
//                showAlertTextOnlyError("Mã OTP sai");
//                break;
//            case 3:
//                showAlertTextOnlyError("Lỗi API");
//                break;
//            default:
//
//        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToBack();
            }
        });
        swipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToBack();
            }
        });
    }

    private void sendUserToBack() {
        Intent intent = new Intent(Home.this,SignUp.class);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("access_token",accessToken);
        intent.putExtra("otpStr",otpStr);
        intent.putExtra("chose",chose);
        startActivity(intent);
        finish();
    }

    private void sendUserToCall() {
        Intent intent = new Intent(Home.this,MakeCall.class);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("access_token",accessToken);
        intent.putExtra("otpStr",otpStr);
        intent.putExtra("chose",chose);
        startActivity(intent);
        finish();
    }

    private void sendUserToSignUp() {
        Intent intent = new Intent(Home.this,SignUpVoice.class);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("access_token",accessToken);
        intent.putExtra("otpStr",otpStr);
        intent.putExtra("chose",chose);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        sendUserToBack();
    }
}