package com.example.aihomeservice.module;



import com.example.aihomeservice.connect.ModuleProvider;
import com.example.aihomeservice.connect.SignUp8KCommunication;
import com.example.aihomeservice.connect.SignUp8KResponse;

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
public class SignUp8KVoiceId {
    SignUp8KResultResponse responseResult = new SignUp8KResultResponse();
    String phoneString;
    File file1, file2, file3;

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    public File getFile1() {
        return file1;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public File getFile2() {
        return file2;
    }

    public void setFile2(File file2) {
        this.file2 = file2;
    }

    public File getFile3() {
        return file3;
    }

    public void setFile3(File file3) {
        this.file3 = file3;
    }

    public SignUp8KResultResponse signUp8KVoiceId() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<SignUp8KResultResponse> future = threadPoll.submit(new Callable<SignUp8KResultResponse>() {
            @Override
            public SignUp8KResultResponse call() throws Exception {
                SignUp8KCommunication signUpService = ModuleProvider.self().getRetrofit().create(SignUp8KCommunication.class);
                RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), phoneString);

                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file1", file1.getName(), requestFile1);

                RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
                MultipartBody.Part reqFile2 = MultipartBody.Part.createFormData("file2", file2.getName(), requestFile2);

                RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
                MultipartBody.Part reqFile3 = MultipartBody.Part.createFormData("file3", file3.getName(), requestFile3);

                Call<SignUp8KResponse> call = signUpService.enroll(phone, reqFile1, reqFile2, reqFile3);
                Response<SignUp8KResponse> response = call.execute();
                //            System.out.println("res in: " + response.code() + " " + response.body().toString());
                switch (response.code()) {
                    case 200:
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus_code(response.body().getStatus_code());
                        responseResult.setCode(response.body().getCode());
                        responseResult.setError(response.body().getError());
                        break;
                    case 401:
                        responseResult.setMsg("Access token is wrong or missing");
                        break;
                    case 422:
                        System.out.println("res in: " + response.code() + " " + new String(response.errorBody().bytes()));
                        responseResult.setMsg("Missing params");
                        break;
                    default:
                        responseResult.setMsg("System Error");
                        break;
                }
                return responseResult;
            }
        });
        SignUp8KResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new SignUp8KResultResponse();
            responseResult.setMsg("System Error");
        }
        //     System.out.println("Result: " + responseResult);
        return responseResult;
    }

}

