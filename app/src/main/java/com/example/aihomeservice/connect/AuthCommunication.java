package com.example.aihomeservice.connect;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthCommunication {
    @POST("auth")
    @Multipart
    Call<CheckAuthResponse> checkAuth(
            @Part("api_key") RequestBody api_key
    );
}
