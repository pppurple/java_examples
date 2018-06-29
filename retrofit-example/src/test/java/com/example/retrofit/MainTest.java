package com.example.retrofit;

import com.example.retrofit.client.MyService;
import com.example.retrofit.model.Dog;
import okhttp3.OkHttpClient;
import okhttp3.internal.http.HttpMethod;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainTest {
    private MyService myService;

    @Before
    public void before() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
//                .client(new OkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        myService = retrofit.create(MyService.class);
    }

    @Test
    public void getTest() throws Exception {
        Response<Dog> response = myService.getDog().execute();
        if (!response.isSuccessful()) {
            System.out.println("error!!" + response.errorBody());
            String url = response.raw().request().url().toString();
            System.out.println("url=" + url + ". ");
        }
        Dog dog = response.body();
        System.out.println("dog: " + dog);
    }
}