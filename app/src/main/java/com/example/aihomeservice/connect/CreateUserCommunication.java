package com.example.aihomeservice.connect;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 *
 * @author thangth
 */
public interface CreateUserCommunication {

    @POST("create_user")
    @Multipart
    Call<CreateUserResponse> createUser(

            @Part("name") RequestBody name,
            @Part("phone") RequestBody phone,
            @Part("email") RequestBody email
    );
}

