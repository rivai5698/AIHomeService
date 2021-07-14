package com.example.aihomeservice.connect;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModuleProvider {
    private static volatile ModuleProvider mInstance = null;
    private String BASE_URL = "https://api.nextiva.vn/voicebio/";
    private final Retrofit retrofit;
    private String tokenString;
    TokenAccessGetting tokenAccessGetting = TokenAccessGetting.getInstance();

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    private ModuleProvider() {
        tokenString = tokenAccessGetting.getToken();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + tokenString)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
    }

    public static ModuleProvider self() {
        if (mInstance == null) {
            mInstance = new ModuleProvider();
        }
        return mInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
