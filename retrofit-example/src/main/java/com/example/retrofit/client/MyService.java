package com.example.retrofit.client;

import com.example.retrofit.model.Dog;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface MyService {
    @GET("dog")
    Call<Dog> getDog();

    @GET("dogs")
    Call<List<Dog>> getDogList();

    @GET("dog?aaa=bbb")
    Call<Dog> getDogWithParam();

    @GET("dog")
    Call<Dog> getDogWithParam(@Query("aaa") String value);

    @GET("dog")
    Call<Dog> getDogWithParams(@QueryMap Map<String, String> params);

    @Headers({
            "User-Agent: my-service:0.1",
            "my-header: zzz"
    })
    @GET("dog")
    Call<Dog> getDogWithHeaders();

    @GET("dog")
    Call<Dog> getDogWithDynamicHeader(@Header("User-Agent") String userAgent);

    @GET("dogs/{id}")
    Call<Dog> getDogById(@Path("id") int id);

    @POST("dogs")
    Call<Void> create(@Body Dog dog);

    @FormUrlEncoded
    @POST("dogs/form")
    Call<Void> form(@Field("id") int id,
                    @Field("name") String name);

    @PUT("dogs/{id}")
    Call<Void> update(@Path("id") int id,
                      @Body Dog dog);

    @DELETE("dogs/{id}")
    Call<Void> delete(@Path("id") int id);
}
