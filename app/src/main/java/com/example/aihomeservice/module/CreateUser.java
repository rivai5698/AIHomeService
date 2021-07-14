package com.example.aihomeservice.module;


import com.example.aihomeservice.connect.CreateUserCommunication;
import com.example.aihomeservice.connect.CreateUserResponse;
import com.example.aihomeservice.connect.ModuleProvider;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * @author thangth
 */
public class CreateUser {

    String nameString, phoneString, emailString;

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public CreateUserResult createUserResult() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<CreateUserResult> future = threadPoll.submit(new Callable<CreateUserResult>() {
            @Override
            public CreateUserResult call() throws Exception {
                CreateUserCommunication createUserCommunication = ModuleProvider.self().getRetrofit().create(CreateUserCommunication.class);

                RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), nameString);
                RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), phoneString);
                RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), emailString);

                Call<CreateUserResponse> call = createUserCommunication.createUser(name, phone, email);
                Response<CreateUserResponse> response = call.execute();
                CreateUserResult responseResult = new CreateUserResult();
                switch (response.code()) {
                    case 200:
                        //  System.out.println("res in: " + response.body());
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus_code(response.body().getStatus_code());
                        break;
                    case 401:
                        responseResult.setMsg("Access token is wrong or missing");
                        break;
                    case 422:
                        responseResult.setMsg("Missing params");
                        break;
                    default:
                        responseResult.setMsg("System Error");
                        break;
                }

                return responseResult;

            }
        });
        CreateUserResult responseResult = null;
        try {
            responseResult = future.get();
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new CreateUserResult();
        }
        // System.out.println("Result: " + responseResult);
        return responseResult;
    }
}
