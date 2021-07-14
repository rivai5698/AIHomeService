package its.homeai.connect;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AppCommunication {
    @POST("partner/aihome/api/create_and_call")
    Call<AppResponse> makeCall(@Body Customer customer);
}
