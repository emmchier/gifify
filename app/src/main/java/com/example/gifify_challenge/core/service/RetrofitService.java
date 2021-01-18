package com.example.gifify_challenge.core.service;
import com.example.gifify_challenge.utils.Const;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
 * conexión con la API mediante Retrofit2
 */
public class RetrofitService {

    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            retrofit = retrofitBuilder.client(httpclient.build()).build();
        }
        return retrofit;
    }
}
