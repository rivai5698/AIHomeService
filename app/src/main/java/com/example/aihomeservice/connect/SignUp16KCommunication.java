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
public interface SignUp16KCommunication {

    @POST("do-enroll")
    @Multipart
    Call<SignUp16KResponse> enroll(
            @Part("speaker") RequestBody phone,
            @Part("audio_type") RequestBody audio_type,
            @Part("otp_code") RequestBody otp_code,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3
            //                                 @Part("speaker") RequestBody speaker,
            //                                 @Part("otp_code") RequestBody otp_code
    );
}

