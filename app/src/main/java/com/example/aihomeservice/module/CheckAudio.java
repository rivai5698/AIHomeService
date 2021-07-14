package com.example.aihomeservice.module;





import com.example.aihomeservice.connect.AudioProvider;
import com.example.aihomeservice.connect.CheckAudioCommunication;
import com.example.aihomeservice.connect.CheckAudioResponse;
import com.example.aihomeservice.connect.ModuleProvider;
import com.example.aihomeservice.connect.NetworkProvider;

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
public class CheckAudio {

    File myRecordFile;

    public File getMyRecordFile() {
        return myRecordFile;
    }

    public void setMyRecordFile(File myRecordFile) {
        this.myRecordFile = myRecordFile;
    }

    public AudioCheckResultResponse solveAudioFile() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<AudioCheckResultResponse> future = threadPoll.submit(new Callable<AudioCheckResultResponse>() {
            @Override
            public AudioCheckResultResponse call() throws Exception {
                CheckAudioCommunication checkAudioCommunication = AudioProvider.self().getRetrofit().create(CheckAudioCommunication.class);

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myRecordFile);
                MultipartBody.Part file = MultipartBody.Part.createFormData("file", myRecordFile.getName(), requestFile);
                Call<CheckAudioResponse> call = checkAudioCommunication.checkAudio(file);
                Response<CheckAudioResponse> response = call.execute();
                AudioCheckResultResponse responseResult = new AudioCheckResultResponse();
                switch (response.code()) {
                    case 200:
                        System.out.println("res in: " + response.body());
                        responseResult.setCode(response.body().getCode());
                        responseResult.setFile_code(response.body().getFile_code());
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus(response.body().getStatus());
                        break;
                    case 401:
                        responseResult.setMsg("Access token is wrong or missing");

                        break;
                    case 422:
                        responseResult.setMsg("Missing params");
                    default:
                        responseResult.setMsg("System Error");
                        break;
                }
                return responseResult;
            }
        });
        AudioCheckResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new AudioCheckResultResponse();
            responseResult.setMsg("System Error");
        }
        System.out.println("Result: " + responseResult);
        return responseResult;

    }

}
