package its.homeai.connect;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkProvider2 {
    private static volatile NetworkProvider2 mInstance = null;
    private String BASE_URL = "http://103.141.140.189:8899/";
    private Retrofit retrofit;
    private NetworkProvider2() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient.Builder().build())
               .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
    }
    public static NetworkProvider2 self() {
        if (mInstance == null)
            mInstance = new NetworkProvider2();
        return mInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
