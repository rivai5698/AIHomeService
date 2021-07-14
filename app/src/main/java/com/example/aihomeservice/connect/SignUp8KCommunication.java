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
public interface SignUp8KCommunication {

    @POST("do_enroll_8k")
    @Multipart
    Call<SignUp8KResponse> enroll(
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3
    );
}