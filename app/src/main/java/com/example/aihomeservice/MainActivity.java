package com.example.aihomeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihomeservice.module.CheckAuth;
import com.tapadoo.alerter.Alerter;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Session Manager Class
    SessionManager session;

    // Button Logout
    Button btnLogout;
    final int REQUEST_PERMISSION_CODE = 1000;
    CheckAuth checkAuth = new CheckAuth();
    String accessToken,phoneStr;
    String checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        checkAuth.setApi_keyString("b432193c-57e6-41a6-968f-375c60b69fdd");
        checked = getIntent().getStringExtra("checked");
        System.out.println("Check: "+checked);
        //System.out.println("Access-token: " + checkAuth.checkAuthResponse());
        accessToken = checkAuth.checkAuthResponse().getAccess_token();
        phoneStr = getIntent().getStringExtra("phoneStr");
        TextView lblName = findViewById(R.id.lblName);
        btnLogout = findViewById(R.id.btnLogout);
        //showAlertTextOnly("Login: " + session.isLoggedIn());
        //Toast.makeText(this, "Login: " + session.isLoggedIn(), Toast.LENGTH_SHORT).show();
        session.checkLogin();
        if(session.isLoggedIn()){
            showAlertTextOnly("Đã đăng nhập");
        }
        if(session.isLoggedIn()){
            sendUserToMakeCall();
        }else {
            sendUserToSignUp();
        }

        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));

        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
            }
        });
        if (!checkPermissionFromDevice())
            requestPermission();

    }

    private void sendUserToMakeCall() {
        Intent intent = new Intent(MainActivity.this,MakeCall.class);
        startActivity(intent);
        finish();
    }

    private void sendUserToSignUp() {
        Intent intent = new Intent(MainActivity.this,SignUp.class);
        intent.putExtra("access_token",accessToken);
        startActivity(intent);
        finish();
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                  //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                else
                   // showAlertTextOnlyError("Cấp quyền thất bại");
                Toast.makeText(this, "Cấp quyền thất bại", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
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