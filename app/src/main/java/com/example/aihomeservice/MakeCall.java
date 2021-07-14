package com.example.aihomeservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmdev.loadingviewlib.LoadingView;
import com.tapadoo.alerter.Alerter;

import java.util.HashMap;

import its.homeai.connect.AppCommunication;
import its.homeai.connect.AppResponse;
import its.homeai.connect.Customer;
import its.homeai.connect.NetworkProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeCall extends AppCompatActivity {
    String phoneStr,name;
    Button btnCall;
    SessionManager sessionManager;
    TextView etPhone,swipeLeft2;
    ImageView imageView;
    LoadingView loadingView;

    private NetworkProvider networkProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_call);
        phoneStr = getIntent().getStringExtra("phoneStr");
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();

        name = user.get(SessionManager.KEY_NAME);

        System.out.println("???????????"+name);
        initView();
    }

    private void initView() {
        btnCall = findViewById(R.id.btn_call);
        etPhone = findViewById(R.id.et_phone2);
        imageView = findViewById(R.id.im_back2);
        String phoneStr2 = name;
        swipeLeft2 = findViewById(R.id.swipeLeft2);
        etPhone.setText(name);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCall.setEnabled(false);
                Object obj = new Object();
                Customer customer = new Customer("cnsB0UJTt5idqQptw6kZlZiJpV58oVh4","119","0366568956",phoneStr2, obj);
                AppCommunication appCommunication =  NetworkProvider.self().getRetrofit().create(AppCommunication.class);

                appCommunication.makeCall(customer).enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {

                        System.out.println(response.code());
                        System.out.println(response.body().toString());
                        if (response.body().getStatus()!=null){
                            //Toast.makeText(MakeCall.this, "Cuộc gọi đang được thực hiện vui lòng chờ trong giây lát", Toast.LENGTH_SHORT).show();
                            showAlertTextOnly("Cuộc gọi đang được thực hiện vui lòng chờ trong giây lát");
                        }else {
                            Toast.makeText(MakeCall.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            showAlertTextOnly("Lỗi");
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // yourMethod();
                                btnCall.setEnabled(true);
                            }
                        }, 300000);

                    }

                    @Override
                    public void onFailure(Call<AppResponse> call, Throwable t) {
                        System.out.println("!!!!!!!!!!!!"+t.getMessage());
                        Toast.makeText(MakeCall.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




            //    makeCallIntent();
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:0859685545"));
//                if (ActivityCompat.checkSelfPermission(MakeCall.this,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                startActivity(callIntent);

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        MakeCall.this);

// Setting Dialog Title
                alertDialog2.setTitle("");

// Setting Dialog Message
                alertDialog2.setMessage("Bạn có muốn đăng xuất...");

// Setting Icon to Dialog
//                alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                //showAlertTextOnly("Bạn đã đăng xuất");
                                Toast.makeText(MakeCall.this, "Bạn đã đăng xuất", Toast.LENGTH_SHORT).show();
                                sessionManager.logoutUser();
                                sendUserToSignUp();
                            }
                        });
// Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog

                                dialog.cancel();
                            }
                        });

// Showing Alert Dialog
                alertDialog2.show();



            }
        });
        swipeLeft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        MakeCall.this);

// Setting Dialog Title
                alertDialog2.setTitle("");

// Setting Dialog Message
                alertDialog2.setMessage("Bạn có muốn đăng xuất...");

// Setting Icon to Dialog
//                alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                //showAlertTextOnly("Bạn đã đăng xuất");
                                Toast.makeText(MakeCall.this, "Bạn đã đăng xuất", Toast.LENGTH_SHORT).show();
                                sessionManager.logoutUser();
                                sendUserToSignUp();
                            }
                        });
// Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog

                                dialog.cancel();
                            }
                        });

// Showing Alert Dialog
                alertDialog2.show();



            }
        });
    }

    private void sendUserToSignUp() {
        Intent intent = new Intent(MakeCall.this,SignUp.class);
        startActivity(intent);
        finish();
    }

    private void makeCallIntent() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel: 0859685545"));
        startActivity(callIntent);
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
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                MakeCall.this);

// Setting Dialog Title
        alertDialog2.setTitle("");

// Setting Dialog Message
        alertDialog2.setMessage("Bạn có muốn đăng xuất...");

// Setting Icon to Dialog
//                alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        //showAlertTextOnly("Bạn đã đăng xuất");
                        Toast.makeText(MakeCall.this, "Bạn đã đăng xuất", Toast.LENGTH_SHORT).show();
                        sessionManager.logoutUser();
                        sendUserToSignUp();
                    }
                });
// Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog

                        dialog.cancel();
                    }
                });

// Showing Alert Dialog
        alertDialog2.show();
    }
}