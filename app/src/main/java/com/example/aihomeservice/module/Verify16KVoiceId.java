package com.example.aihomeservice.module;


import com.example.aihomeservice.connect.AudioProvider;
import com.example.aihomeservice.connect.ModuleProvider;
import com.example.aihomeservice.connect.Verify16KCommunication;
import com.example.aihomeservice.connect.Verify16KResponse;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * @author thangth
 */
public class Verify16KVoiceId {

    Verify16KResultResponse responseResult = new Verify16KResultResponse();
    String phoneStr;
    File myFileRecorder;

    public String getPhoneStr() {
        return phoneStr;
    }

    public void setPhoneStr(String phoneStr) {
        this.phoneStr = phoneStr;
    }

    public File getMyFileRecorder() {
        return myFileRecorder;
    }

    public void setMyFileRecorder(File myFileRecorder) {
        this.myFileRecorder = myFileRecorder;
    }

    public Verify16KResultResponse verify16KResultResponse() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<Verify16KResultResponse> future = threadPoll.submit(new Callable<Verify16KResultResponse>() {
            @Override
            public Verify16KResultResponse call() throws Exception {
                Verify16KCommunication verifyService = AudioProvider.self().getRetrofit().create(Verify16KCommunication.class);
                RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), phoneStr);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFileRecorder);
                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file", myFileRecorder.getName(), requestFile);
                Call<Verify16KResponse> call = verifyService.verify(phone, reqFile1);
                Response<Verify16KResponse> response = call.execute();
                System.out.println("res in: " + response.body());
                switch (response.code()) {
                    case 200:

                        if (response.body().getStatus() == 0) {
                            Double score = Math.round(response.body().getScore() * 100.0) / 100.0;
                            responseResult.setScore(score);
                            responseResult.setMsg(response.body().getMsg());
                            responseResult.setStatus(response.body().getStatus());
                            responseResult.setRaw_score(response.body().getRaw_score());
                            responseResult.setResult(response.body().getResult());
                        } else {
                            Double score = 0.0;
                            responseResult.setScore(score);
                            responseResult.setMsg(response.body().getMsg());
                            responseResult.setStatus(response.body().getStatus());
                            responseResult.setResult(response.body().getResult());
                            responseResult.setRaw_score(response.body().getRaw_score());
                        }
                        break;
                    case 401:
                        responseResult.setMsg("Access token is wrong or missing");
                        responseResult.setScore(0.0);
                        break;
                    case 422:
                        responseResult.setMsg("Missing params");
                        responseResult.setScore(0.0);
                    default:
                        responseResult.setMsg("System Error");
                        responseResult.setScore(0.0);
                        break;
                }
                return responseResult;
            }
        });
        Verify16KResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new Verify16KResultResponse();
            responseResult.setMsg("System Error");
            responseResult.setScore(0.0);
        }
        //    System.out.println("Result: " + responseResult);
        return responseResult;
    }
}

