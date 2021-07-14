package com.example.aihomeservice.module;




import com.example.aihomeservice.connect.AuthCommunication;
import com.example.aihomeservice.connect.CheckAuthResponse;
import com.example.aihomeservice.connect.NetworkProvider;
import com.example.aihomeservice.connect.TokenAccessGetting;

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
public class CheckAuth {

    TokenAccessGetting tokenAccessGetting = TokenAccessGetting.getInstance();
    String api_keyString;

    public String getApi_keyString() {
        return api_keyString;
    }

    public void setApi_keyString(String api_keyString) {
        this.api_keyString = api_keyString;
    }

    public CheckAuthResponseResult checkAuthResponse() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<CheckAuthResponseResult> future = threadPoll.submit(new Callable<CheckAuthResponseResult>() {
            @Override
            public CheckAuthResponseResult call() throws Exception {
                if (api_keyString != null) {
                    AuthCommunication authCommunication = NetworkProvider.self().getRetrofit().create(AuthCommunication.class);
                    RequestBody api_key = RequestBody.create(MediaType.parse("multipart/form-data"), api_keyString);
                    Call<CheckAuthResponse> call = authCommunication.checkAuth(api_key);
                    Response<CheckAuthResponse> response = call.execute();
                    CheckAuthResponseResult responseResult = new CheckAuthResponseResult();
                    switch (response.code()) {
                        case 200:
                            responseResult.setResult("success");
                            responseResult.setAccess_token(response.body().getAccess_token());
                            responseResult.setToken_type(response.body().getToken_type());
                            tokenAccessGetting.setToken(response.body().getAccess_token());
                            break;
                        case 401:
                            responseResult.setResult("Incorrect api key");
                            break;
                        default:
                            responseResult.setResult("failed");
                            break;
                    }
                    return responseResult;
                }else{
                    CheckAuthResponseResult responseResult = new CheckAuthResponseResult();
                    responseResult.setResult("Api key is missing");
                    return responseResult;
                }
            }
        });
        CheckAuthResponseResult responseResult = null;
        try {
            responseResult = future.get();
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new CheckAuthResponseResult();
        }
        //System.out.println("Result: " + responseResult);
        return responseResult;
    }

}

