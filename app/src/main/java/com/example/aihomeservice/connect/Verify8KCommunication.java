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
public interface Verify8KCommunication {
    @POST("do_verify_8k")
    @Multipart
    Call<Verify8KResponse> verify (
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part file

    );
}
