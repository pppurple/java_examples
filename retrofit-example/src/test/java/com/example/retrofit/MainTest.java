package com.example.retrofit;

import com.example.retrofit.client.MyService;
import com.example.retrofit.model.Dog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static okhttp3.logging.HttpLoggingInterceptor.*;

public class MainTest {
    private MyService myService;

    @Before
    public void before() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        myService = retrofit.create(MyService.class);
    }

    @Test
    public void getDogTest() throws Exception {
        Response<Dog> response = myService.getDog().execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
        Dog dog = response.body();
        System.out.println("dog: " + dog);
    }

    @Test
    public void getDogList() throws Exception {
        Response<List<Dog>> response = myService.getDogList().execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
        List<Dog> dogs = response.body();
        System.out.println("dogs: " + dogs);
    }

    @Test
    public void getDogWithParamTest() throws Exception {
        Response<Dog> response = myService.getDogWithParam().execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
        Dog dog = response.body();
        System.out.println("dog: " + dog);
    }

    @Test
    public void getDogWithParamQueryTest() throws Exception {
        Response<Dog> response = myService.getDogWithParam("ccc").execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
        Dog dog = response.body();
        System.out.println("dog: " + dog);
    }

    @Test
    public void getDogWithParamsTest() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("aaa", "bbb");
        params.put("size", "100");
        Response<Dog> response = myService.getDogWithParams(params).execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
        Dog dog = response.body();
        System.out.println("dog: " + dog);
    }

    @Test
    public void getDogWithHeadersTest() throws Exception {
        Response<Dog> response = myService.getDogWithHeaders().execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
        Dog dog = response.body();
        System.out.println("dog: " + dog);
    }

    @Test
    public void getDogWithDynamicHeaderTest() throws Exception {
        Response<Dog> response = myService.getDogWithDynamicHeader("my-header:0.2").execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
        Dog dog = response.body();
        System.out.println("dog: " + dog);
    }

    @Test
    public void getDogByIdTest() throws Exception {
        Response<Dog> response = myService.getDogById(1).execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
        Dog dog = response.body();
        System.out.println("dog: " + dog);
    }

    @Test
    public void createTest() throws Exception {
        Dog dog = new Dog(100, "pudding");
        Response<Void> response = myService.create(dog).execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
    }

    @Test
    public void formTest() throws Exception {
        Response<Void> response = myService.form(200, "太郎").execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
    }

    @Test
    public void updateTest() throws Exception {
        Dog dog = new Dog(300, "santa");
        Response<Void> response = myService.update(2, dog).execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
    }

    @Test
    public void deleteTest() throws Exception {
        Response<Void> response = myService.delete(3).execute();
        if (!response.isSuccessful()) {
            String url = response.raw().request().url().toString();
            System.out.println("error!!" + response.errorBody() + " url=" + url);
        }
    }
}