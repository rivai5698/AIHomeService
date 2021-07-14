package com.example.aihomeservice.connect;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 *
 * @author thangth
 */
public interface CheckAudioCommunication {
    @POST("check-audio")
    @Multipart
    Call<CheckAudioResponse> checkAudio(
            @Part MultipartBody.Part file
    );
}
