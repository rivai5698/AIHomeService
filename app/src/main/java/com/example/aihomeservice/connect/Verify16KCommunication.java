package com.example.aihomeservice.connect;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 *
 * @author thangth
 */
public interface Verify16KCommunication {

    @POST("do-speaker-verification")
    @Multipart
    Call<Verify16KResponse> verify(
            @Part("speaker") RequestBody speaker,
            @Part MultipartBody.Part file
            // @Part("verify_type") RequestBody verify_type
    );
}

