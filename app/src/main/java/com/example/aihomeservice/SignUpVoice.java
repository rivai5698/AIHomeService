package com.example.aihomeservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aihomeservice.init.RecordFunction;
import com.example.aihomeservice.module.AudioCheckResultResponse;
import com.example.aihomeservice.module.CheckAudio;
import com.example.aihomeservice.module.SignUp16KResultResponse;
import com.example.aihomeservice.module.SignUp16KVoiceId;
import com.mmdev.loadingviewlib.LoadingView;
import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class SignUpVoice extends AppCompatActivity {

    String otpStr, accessToken, phoneStr, pathSavePCM1, pathSaveWAV1, pathSavePCM2, pathSaveWAV2, pathSavePCM3, pathSaveWAV3;
    Button btnRecord1, btnRecord2, btnRecord3, btnPlay1, btnPlay2, btnPlay3, btnCheck1, btnCheck2, btnCheck3, btnStopRecord1, btnStopRecord2, btnStopRecord3, btnSignUp;
    RecordFunction recordFunction1, recordFunction2, recordFunction3;
    Boolean checked1 = false, checked2 = false, checked3 = false;
    File file1, file2, file3;
    TextView editText6, popUp;
    SignUp16KVoiceId signUp16KVoiceId;
    ImageView btnBack;
    LoadingView loadingView;
    int chose=1;
    SignUp16KResultResponse resultResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_voice);
        signUp16KVoiceId = new SignUp16KVoiceId();
        accessToken = getIntent().getStringExtra("access_token");
        phoneStr = getIntent().getStringExtra("phoneStr");
        otpStr =  getIntent().getStringExtra("otpStr");
        //chose = getIntent().getIntExtra("chose",1);
//        checked = getIntent().getStringExtra("checked");
//        System.out.println("Checked SignUp: " + checked);
//        if (checked == null) {
//            showAlertTextOnly("Hãy đăng ký người dùng trước đó");
//            checked = null;
//        } else if (checked.equalsIgnoreCase("true")) {
//            showAlertTextOnly("Đăng ký người dùng thành công");
//            checked = "true1";
//        } else if (checked.equalsIgnoreCase("true2")) {
//            showAlertTextOnly("Số điện thoại này đã được đăng ký");
//            checked = "true1";
//        } else {
//            checked = "true1";
//        }
        recordFunction1 = new RecordFunction();
        recordFunction2 = new RecordFunction();
        recordFunction3 = new RecordFunction();
        initView();
    }

    private void initView() {
        btnCheck1 = findViewById(R.id.btn_check1);
        btnCheck2 = findViewById(R.id.btn_check2);
        btnCheck3 = findViewById(R.id.btn_check3);
        btnRecord1 = findViewById(R.id.btn_record1);
        btnRecord2 = findViewById(R.id.btn_record2);
        btnRecord3 = findViewById(R.id.btn_record3);
        btnStopRecord1 = findViewById(R.id.btn_stopRecord1);
        btnStopRecord2 = findViewById(R.id.btn_stopRecord2);
        btnStopRecord3 = findViewById(R.id.btn_stopRecord3);
        btnPlay1 = findViewById(R.id.btn_play1);
        btnPlay2 = findViewById(R.id.btn_play2);
        btnPlay3 = findViewById(R.id.btn_play3);
        btnSignUp = findViewById(R.id.btn_signUp);
        editText6 = findViewById(R.id.et6);
        popUp = findViewById(R.id.pop_up);
        loadingView = findViewById(R.id.loadingView);
        //  loadingView.setVisibility(View.INVISIBLE);
        //     btnSignUp.setEnabled(false);
        btnPlay1.setEnabled(false);
        btnCheck1.setEnabled(false);
        btnPlay2.setEnabled(false);
        btnCheck2.setEnabled(false);
        btnPlay3.setEnabled(false);
        btnCheck3.setEnabled(false);
//        editText6 = findViewById(R.id.et6);
        btnBack = findViewById(R.id.im_back2);

        editText6.setText(phoneStr);
        popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        SignUpVoice.this);

// Setting Dialog Title
                alertDialog2.setTitle("Yêu cầu");

// Setting Dialog Message
                alertDialog2.setMessage("1: Nói không rõ từ, yêu cầu nói rõ  hơn\n" +
                        "2: Nói quá nhỏ, yêu cầu nói to hơn \n" +
                        "3: Môi trường nhiễu, yêu cầu ghi âm ở môi trường yên tĩnh hơn\n" +
                        "4: Độ dài audio không đủ 2s, yêu cầu ghi âm dài hơn \n" +
                        "5: Nói không đủ số từ tối thiểu, yêu cầu nói nhiều từ hơn \n" +
                        "6: Độ dài audio quá dài (hơn 20p), yêu cầu nói ngắn lại");

// Setting Icon to Dialog
//                alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
//                alertDialog2.setPositiveButton("Có",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Write your code here to execute after dialog
//                                Toast.makeText(getApplicationContext(),
//                                        "Bạn đã đăng xuất", Toast.LENGTH_SHORT)
//                                        .show();
//                                sendUserToLogin();
//                            }
//                        });
// Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("Đóng",
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
        btnRecord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // dir = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/"+FolderName );
                    pathSavePCM1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + UUID.randomUUID().toString() + "_audio_record.pcm";
                    pathSaveWAV1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + UUID.randomUUID().toString() + "_audio_record.wav";

//                        file1 = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/" +UUID.randomUUID().toString()+ "_audio_record.wav");
//                        filePCM1 = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/" +UUID.randomUUID().toString() + "_audio_record.pcm");
                    recordFunction1.startRecording2(pathSavePCM1, pathSaveWAV1);
                } else {
                    pathSavePCM1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.pcm";
                    pathSaveWAV1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.wav";
                    recordFunction1.startRecording(pathSavePCM1, pathSaveWAV1);
                }
                btnRecord2.setEnabled(false);
                btnRecord3.setEnabled(false);
                btnCheck1.setText("Check");
                btnRecord1.setVisibility(View.INVISIBLE);
                btnStopRecord1.setVisibility(View.VISIBLE);
                btnStopRecord1.setEnabled(false);
                Toast.makeText(SignUpVoice.this, "Đang ghi âm...", Toast.LENGTH_SHORT).show();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnRecord1.setEnabled(true);
                            }
                        });
                    }
                }, 500);
                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        btnStopRecord1.setEnabled(true);
                    }

                }.start();
            }
        });

        btnRecord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // dir = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/"+FolderName );
                    pathSavePCM2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + UUID.randomUUID().toString() + "_audio_record.pcm";
                    pathSaveWAV2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + UUID.randomUUID().toString() + "_audio_record.wav";

//                        file1 = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/" +UUID.randomUUID().toString()+ "_audio_record.wav");
//                        filePCM1 = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/" +UUID.randomUUID().toString() + "_audio_record.pcm");
                    recordFunction2.startRecording2(pathSavePCM2, pathSaveWAV2);
                } else {
                    pathSavePCM2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.pcm";
                    pathSaveWAV2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.wav";
                    recordFunction2.startRecording(pathSavePCM2, pathSaveWAV2);
                }
                btnRecord1.setEnabled(false);
                btnRecord3.setEnabled(false);
                btnCheck2.setText("Check");
                btnRecord2.setVisibility(View.INVISIBLE);
                btnStopRecord2.setVisibility(View.VISIBLE);
                btnStopRecord2.setEnabled(false);

                Toast.makeText(SignUpVoice.this, "Đang ghi âm...", Toast.LENGTH_SHORT).show();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnRecord2.setEnabled(true);
                            }
                        });
                    }
                }, 500);
                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        btnStopRecord2.setEnabled(true);
                    }

                }.start();
            }
        });
        btnRecord3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // dir = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/"+FolderName );
                    pathSavePCM3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + UUID.randomUUID().toString() + "_audio_record.pcm";
                    pathSaveWAV3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + UUID.randomUUID().toString() + "_audio_record.wav";

//                        file1 = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/" +UUID.randomUUID().toString()+ "_audio_record.wav");
//                        filePCM1 = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/" +UUID.randomUUID().toString() + "_audio_record.pcm");
                    recordFunction3.startRecording2(pathSavePCM3, pathSaveWAV3);
                } else {
                    pathSavePCM3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.pcm";
                    pathSaveWAV3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.wav";
                    recordFunction3.startRecording(pathSavePCM3, pathSaveWAV3);
                }
                btnRecord3.setVisibility(View.INVISIBLE);
                btnStopRecord3.setVisibility(View.VISIBLE);
                btnStopRecord3.setEnabled(false);
                btnRecord1.setEnabled(false);
                btnRecord2.setEnabled(false);
                btnCheck3.setText("Check");
                Toast.makeText(SignUpVoice.this, "Đang ghi âm...", Toast.LENGTH_SHORT).show();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnRecord3.setEnabled(true);
                            }
                        });
                    }
                }, 500);
                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        btnStopRecord3.setEnabled(true);
                    }

                }.start();
            }
        });

        btnStopRecord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    recordFunction1.stopRecording(pathSavePCM1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //mediaRecorder.stop();
                btnRecord1.setVisibility(View.VISIBLE);
                btnStopRecord1.setVisibility(View.INVISIBLE);


                btnRecord2.setEnabled(true);
                btnRecord3.setEnabled(true);

//                btnPlay1.setEnabled(true);
//                btnPlay2.setEnabled(true);
//                btnPlay3.setEnabled(true);
//                btnStop1.setEnabled(true);
//                btnStop2.setEnabled(true);
//                btnStop3.setEnabled(true);

                file1 = new File(pathSaveWAV1);
//                filePCM1 = new File(pathSavePCM1);

                btnPlay1.setEnabled(true);
                btnCheck1.setEnabled(true);
                System.out.println("file path: -----------" + file1.getAbsolutePath());
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnStopRecord1.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });

        btnStopRecord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    recordFunction2.stopRecording(pathSavePCM2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //mediaRecorder.stop();
                btnRecord2.setVisibility(View.VISIBLE);
                btnStopRecord2.setVisibility(View.INVISIBLE);


                btnRecord1.setEnabled(true);
                btnRecord3.setEnabled(true);

//                btnPlay1.setEnabled(true);
//                btnPlay2.setEnabled(true);
//                btnPlay3.setEnabled(true);
//                btnStop1.setEnabled(true);
//                btnStop2.setEnabled(true);
//                btnStop3.setEnabled(true);

                file2 = new File(pathSaveWAV2);
//                filePCM1 = new File(pathSavePCM1);

                btnPlay2.setEnabled(true);
                btnCheck2.setEnabled(true);
                System.out.println("file path: -----------" + file2.getAbsolutePath());
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnStopRecord2.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });


        btnStopRecord3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    recordFunction3.stopRecording(pathSavePCM3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //mediaRecorder.stop();
                btnRecord3.setVisibility(View.VISIBLE);
                btnStopRecord3.setVisibility(View.INVISIBLE);


                btnRecord2.setEnabled(true);
                btnRecord1.setEnabled(true);

//                btnPlay1.setEnabled(true);
//                btnPlay2.setEnabled(true);
//                btnPlay3.setEnabled(true);
//                btnStop1.setEnabled(true);
//                btnStop2.setEnabled(true);
//                btnStop3.setEnabled(true);

                file3 = new File(pathSaveWAV3);
//                filePCM1 = new File(pathSavePCM1);

                btnPlay3.setEnabled(true);
                btnCheck3.setEnabled(true);
                System.out.println("file path: -----------" + file3.getAbsolutePath());
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnStopRecord3.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });

        btnPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay1.setText("Pause");
                if (btnPlay1.getText().toString().equalsIgnoreCase("Pause")) {
                    btnRecord1.setEnabled(true);
                    btnCheck1.setEnabled(true);
                } else if (btnPlay1.getText().toString().equalsIgnoreCase("Play")) {
                    btnRecord1.setEnabled(false);
                    btnRecord1.setVisibility(View.VISIBLE);
                    btnStopRecord1.setEnabled(false);
                    //btnPlay1.setText("Play");
                    btnCheck1.setEnabled(false);
                } else {
                    btnRecord1.setEnabled(true);
                    btnCheck1.setEnabled(true);
                }
                recordFunction1.setMediaPlayer(pathSaveWAV1, btnPlay1);
                Toast.makeText(SignUpVoice.this, "Playing...", Toast.LENGTH_SHORT).show();
            }
        });
        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay2.setText("Pause");
                if (btnPlay2.getText().toString().equalsIgnoreCase("Pause")) {
                    btnRecord2.setEnabled(true);
                    btnCheck2.setEnabled(true);
                } else if (btnPlay2.getText().toString().equalsIgnoreCase("Play")) {
                    btnRecord2.setEnabled(false);
                    btnRecord2.setVisibility(View.VISIBLE);
                    btnStopRecord2.setEnabled(false);
                    //btnPlay1.setText("Play");
                    btnCheck2.setEnabled(false);
                } else {
                    btnRecord2.setEnabled(true);
                    btnCheck2.setEnabled(true);
                }
                recordFunction2.setMediaPlayer(pathSaveWAV2, btnPlay2);
                Toast.makeText(SignUpVoice.this, "Playing...", Toast.LENGTH_SHORT).show();
            }
        });
        btnPlay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay3.setText("Pause");
                if (btnPlay3.getText().toString().equalsIgnoreCase("Pause")) {
                    btnRecord3.setEnabled(true);
                    btnCheck3.setEnabled(true);
                } else if (btnPlay3.getText().toString().equalsIgnoreCase("Play")) {
                    btnRecord3.setEnabled(false);
                    btnRecord3.setVisibility(View.VISIBLE);
                    btnStopRecord3.setEnabled(false);
                    //btnPlay1.setText("Play");
                    btnCheck3.setEnabled(false);
                } else {
                    btnRecord3.setEnabled(true);
                    btnCheck3.setEnabled(true);
                }
                recordFunction3.setMediaPlayer(pathSaveWAV3, btnPlay3);
                Toast.makeText(SignUpVoice.this, "Playing...", Toast.LENGTH_SHORT).show();
            }
        });

        btnCheck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopRecord1.setEnabled(true);
                btnRecord1.setEnabled(true);
                btnCheck1.setEnabled(false);
                btnPlay1.setEnabled(true);

                CheckAudio checkAudio = new CheckAudio();
                if (file1 != null) {
                    if (file1.isFile()) {
                        System.out.println("isFile");
                    } else {
                        System.out.println("notFile");
                    }
                } else {
                    System.out.println("nullFile");
                }
                checkAudio.setMyRecordFile(file1);
                AudioCheckResultResponse aR = checkAudio.solveAudioFile();
                System.out.println("aR1: " + aR);

//                    int code = aR.getCode();
//                    int status = aR.getStatus();
//                    String msg = aR.getMsg();




                if (aR.getCode() == null) {
                    checked1=false;
                    showAlertTextOnlyError("Lỗi hệ thống");
                } else {
                    int mycode = Integer.parseInt(aR.getCode());
                    switch (mycode) {
                        case 0:
                            checked1=true;
 //                           if (aR.getCode() != null) {
                                showAlertTextOnly("File đạt chuẩn");
//                            } else {
//                                showAlertTextOnlyError("Lỗi khác");
//                            }
                            break;
                        case 1:
                            checked1 = false;
                            showAlertTextOnlyError("Nói không rõ từ, yêu cầu nói rõ hơn");
                            break;
                        case 2:
                            checked1 = false;
                            showAlertTextOnlyError("Nói quá nhỏ, yêu cầu nói to hơn");
                            break;
                        case 3:
                            checked1 = false;
                            showAlertTextOnlyError("Môi trường nhiễu, yêu cầu ghi âm ở môi trường yên tĩnh hơn");
                            break;
                        case 4:
                            checked1 = false;
                            showAlertTextOnlyError("Độ dài audio không đủ 2s, yêu cầu ghi âm dài hơn");
                            break;
                        case 5:
                            checked1 = false;
                            showAlertTextOnlyError("Nói không đủ số từ tối thiểu, yêu cầu ghi âm dài hơn");
                            break;
                        case 6:
                            checked1 = false;
                            showAlertTextOnlyError("Độ dài audio quá dài, yêu cầu nói ngắn lại");
                            break;

                        default:
                            showAlertTextOnlyError("Lỗi khác");
                    }
                }

                if (checked1) {
                    btnCheck1.setText("Ok");
                    btnCheck1.setBackgroundResource(R.drawable.mybuttonsuccess);

                } else {
                    btnCheck1.setText("Fail");
                    btnCheck1.setBackgroundResource(R.drawable.mybuttonfailed);
                }
                if (checked1 && checked2 && checked3)
                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {

                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            loadingView.setEnabled(true);
                            btnSignUp.setEnabled(true);
                        }

                    }.start();

//                if (mediaPlayer != null) {
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                }
//


                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnCheck1.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }

        });

        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopRecord2.setEnabled(true);
                btnRecord2.setEnabled(true);
                btnCheck2.setEnabled(false);
                btnPlay2.setEnabled(true);

                CheckAudio checkAudio = new CheckAudio();
                if (file1 != null) {
                    if (file1.isFile()) {
                        System.out.println("isFile");
                    } else {
                        System.out.println("notFile");
                    }
                } else {
                    System.out.println("nullFile");
                }
                checkAudio.setMyRecordFile(file2);
                AudioCheckResultResponse aR = checkAudio.solveAudioFile();
                System.out.println("aR2: " + aR);

//                    int code = aR.getCode();
//                    int status = aR.getStatus();
//                    String msg = aR.getMsg();
                if (aR.getCode() == null) {
                    checked2=false;
                    showAlertTextOnlyError("Lỗi hệ thống");
                } else {
                    int mycode = Integer.parseInt(aR.getCode());
                    switch (mycode) {
                        case 0:
                            //                           if (aR.getCode() != null) {
                            showAlertTextOnly("File đạt chuẩn");
                            checked2=true;
//                            } else {
//                                showAlertTextOnlyError("Lỗi khác");
//                            }
                            break;
                        case 1:
                            checked2 = false;
                            showAlertTextOnlyError("Nói không rõ từ, yêu cầu nói rõ hơn");
                            break;
                        case 2:
                            checked2 = false;
                            showAlertTextOnlyError("Nói quá nhỏ, yêu cầu nói to hơn");
                            break;
                        case 3:
                            checked2 = false;
                            showAlertTextOnlyError("Môi trường nhiễu, yêu cầu ghi âm ở môi trường yên tĩnh hơn");
                            break;
                        case 4:
                            checked2 = false;
                            showAlertTextOnlyError("Độ dài audio không đủ 2s, yêu cầu ghi âm dài hơn");
                            break;
                        case 5:
                            checked2 = false;
                            showAlertTextOnlyError("Nói không đủ số từ tối thiểu, yêu cầu ghi âm dài hơn");
                            break;
                        case 6:
                            checked2 = false;
                            showAlertTextOnlyError("Độ dài audio quá dài, yêu cầu nói ngắn lại");
                            break;

                        default:
                            showAlertTextOnlyError("Lỗi khác");
                    }
                }

                if (checked2) {
                    btnCheck2.setText("Ok");
                    btnCheck2.setBackgroundResource(R.drawable.mybuttonsuccess);

                } else {
                    btnCheck2.setText("Fail");
                    btnCheck2.setBackgroundResource(R.drawable.mybuttonfailed);
                }
                if (checked1 && checked2 && checked3)
                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {

                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            loadingView.setEnabled(true);
                            btnSignUp.setEnabled(true);
                        }

                    }.start();

//                if (mediaPlayer != null) {
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                }
//


                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnCheck2.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }

        });

        btnCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopRecord3.setEnabled(true);
                btnRecord3.setEnabled(true);
                btnCheck3.setEnabled(false);
                btnPlay3.setEnabled(true);
                CheckAudio checkAudio = new CheckAudio();
                if (file1 != null) {
                    if (file1.isFile()) {
                        System.out.println("isFile");
                    } else {
                        System.out.println("notFile");
                    }
                } else {
                    System.out.println("nullFile");
                }
                checkAudio.setMyRecordFile(file3);
                AudioCheckResultResponse aR = checkAudio.solveAudioFile();
                System.out.println("aR3: " + aR);
                if (aR.getCode() == null) {
                    showAlertTextOnlyError("Lỗi hệ thống");
                    checked3 = false;
                } else {
                    int mycode = Integer.parseInt(aR.getCode());
                    switch (mycode) {
                        case 0:
                            //                           if (aR.getCode() != null) {
                            showAlertTextOnly("File đạt chuẩn");
                            checked3= true;
//                            } else {
//                                showAlertTextOnlyError("Lỗi khác");
//                            }
                            break;
                        case 1:
                            checked3 = false;
                            showAlertTextOnlyError("Nói không rõ từ, yêu cầu nói rõ hơn");
                            break;
                        case 2:
                            checked3 = false;
                            showAlertTextOnlyError("Nói quá nhỏ, yêu cầu nói to hơn");
                            break;
                        case 3:
                            checked3 = false;
                            showAlertTextOnlyError("Môi trường nhiễu, yêu cầu ghi âm ở môi trường yên tĩnh hơn");
                            break;
                        case 4:
                            checked3 = false;
                            showAlertTextOnlyError("Độ dài audio không đủ 2s, yêu cầu ghi âm dài hơn");
                            break;
                        case 5:
                            checked3 = false;
                            showAlertTextOnlyError("Nói không đủ số từ tối thiểu, yêu cầu ghi âm dài hơn");
                            break;
                        case 6:
                            checked3 = false;
                            showAlertTextOnlyError("Độ dài audio quá dài, yêu cầu nói ngắn lại");
                            break;

                        default:
                            showAlertTextOnlyError("Lỗi khác");
                    }
                }

                if (checked3) {
                    btnCheck3.setText("Ok");
                    btnCheck3.setBackgroundResource(R.drawable.mybuttonsuccess);

                } else {
                    btnCheck3.setText("Fail");
                    btnCheck3.setBackgroundResource(R.drawable.mybuttonfailed);
                }
                if (checked1 && checked2 && checked3)
                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {

                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            btnSignUp.setEnabled(true);
                        }

                    }.start();

//                if (mediaPlayer != null) {
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                }
//


                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnCheck3.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }

        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chose=1;
                loadingView.setVisibility(View.VISIBLE);
                btnSignUp.setEnabled(false);
//                if (checked3 && checked2 && checked1)
//                    btnSignUp.setEnabled(true);

                signUp16KVoiceId.setFile1(file1);
                signUp16KVoiceId.setFile2(file2);
                signUp16KVoiceId.setFile3(file3);
                signUp16KVoiceId.setPhoneString(phoneStr);
                signUp16KVoiceId.setAudioTypeString("GT_16k");
                signUp16KVoiceId.setOtpString(otpStr);
                System.out.println("OTP ?????: "+otpStr);
                if (file1 != null && file2 != null && file3 != null) {
                    resultResponse = signUp16KVoiceId.signUp16KVoiceId();
                    System.out.println("???????  " + resultResponse);
                    if (resultResponse.getStatus() == null) {
                        showAlertTextOnlyError("Lỗi hệ thống");
                    } else if (resultResponse.getStatus() == 400) {
                        showAlertTextOnlyError("Đăng ký thất bại");
                    } else if (resultResponse.getStatus() == 0) {

                        Timer buttonTimer = new Timer();
                        buttonTimer.schedule(new TimerTask() {

                            @Override
                            public void run() {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //checked = "true";
                                        sendUserToVerify();
                                    }
                                });
                            }
                        }, 2000);
                    } else {
                        showAlertTextOnlyError("Lỗi hệ thống");
                    }
                } else {
                    showAlertTextOnlyError("Vui lòng nhập đủ 3 file ghi âm");
                }


                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnSignUp.setEnabled(true);
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
        Intent mainIntent = new Intent(SignUpVoice.this, Home.class);
        mainIntent.putExtra("access_token", accessToken);
        mainIntent.putExtra("phoneStr", phoneStr);
        mainIntent.putExtra("otpStr", otpStr);
        mainIntent.putExtra("chose",chose);
        startActivity(mainIntent);
        finish();
    }

    private void sendUserToVerify() {
        Intent intent = new Intent(SignUpVoice.this, VerifyVoice.class);
        intent.putExtra("access_token", accessToken);
        intent.putExtra("phoneStr", phoneStr);
        intent.putExtra("otpStr", otpStr);
        intent.putExtra("chose",chose);
      //  intent.putExtra("checked", checked);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // your code.
        Intent mainIntent = new Intent(SignUpVoice.this, Home.class);
        mainIntent.putExtra("access_token", accessToken);
        mainIntent.putExtra("phoneStr", phoneStr);
        mainIntent.putExtra("otpStr", otpStr);
        mainIntent.putExtra("chose",chose);
    //    mainIntent.putExtra("checked", checked);
        startActivity(mainIntent);
        finish();
    }

    private void showAlertTextOnly(String msg) {
        Alerter.create(this).setBackgroundColorRes(R.color.colorAccent)
                .setText(msg)
                .show();
    }

    private void showAlertTextOnlyError(String msg) {
        Alerter.create(this).setBackgroundColorRes(R.color.redColor)
                .setText(msg)
                .show();
    }


}