package com.example.retrofit;

import com.example.retrofit.client.MyService;
import com.example.retrofit.model.Dog;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainTest {
    private MyService myService;

    @Before
    public void before() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .build();

        myService = retrofit.create(MyService.class);
    }

    @Test
    public void getTest() {
        Call<Dog> dogCall = myService.get();

    }
}