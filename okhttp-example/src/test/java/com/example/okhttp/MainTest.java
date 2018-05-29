package com.example.okhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import okhttp3.internal.http.HttpMethod;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainTest {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void mainTest() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
                .connectTimeout(3_000, TimeUnit.MILLISECONDS)
                .readTimeout(3_000, TimeUnit.MILLISECONDS)
                .writeTimeout(3_000, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(createConnectionStatsInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BODY))
                .addInterceptor(addHeaderInterceptor())
                .build();
    }

    @Test
    public void get() throws Exception {
        String url = "http://localhost:8080/hello";

        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        String body = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful() || responseCode >= 300) {
                System.out.println("error!!");
            }

            if (response.body() != null) {
                body = response.body().string();
            }
        }
        System.out.println("body: " + body);
    }

    @Test
    public void getAddHeaders() throws Exception {
        String url = "http://localhost:8080/hello";

        final Request.Builder builder = new Request.Builder();

        // set headers
        Map<String, String> httpHeaderMap = new HashMap<>();
        httpHeaderMap.put("User-Agent", "hello-agent");
        httpHeaderMap.put("x-aaa-header", "bbb");
        httpHeaderMap.forEach(builder::addHeader);

        Request request = builder
                .url(url)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        String body = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful() || responseCode >= 300) {
                System.out.println("error!!");
            }

            if (response.body() != null) {
                body = response.body().string();
            }
        }
        System.out.println("body: " + body);
    }

    @Test
    public void getAddRequestParams() throws Exception {
        String url = "http://localhost:8080/hello";

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        // set request parameters
        Map<String, String> params = new HashMap<>();
        params.put("name", "abc");
        params.put("code", "123");
        params.forEach(urlBuilder::addQueryParameter);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        String body = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful() || responseCode >= 300) {
                System.out.println("error!!");
            }

            if (response.body() != null) {
                body = response.body().string();
            }
        }
        System.out.println("body: " + body);
    }

    @Test
    public void postForm() throws Exception {
        String url = "http://localhost:8080/hello";

        Dog dog = new Dog(100, "pome");
        RequestBody requestBody = RequestBody.create(JSON, mapper.writeValueAsString(dog));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        String body = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful() || responseCode >= 300) {
                System.out.println("error!!");
            }

            if (response.body() != null) {
                body = response.body().string();
            }
        }
        System.out.println("body: " + body);

    }

    @Test
    public void postJson() {

    }

    @Test
    public void put() {

    }

    @Test
    public void delete() {

    }

    @Test
    public void connectionPool() {

    }

    @Test
    public void interceptor() {

    }

    @Test
    public void networkInterceptor() {

    }

    private Interceptor addHeaderInterceptor() {
        return chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .header("my-header", "abcde")
                    .build();
            return chain.proceed(request);
        };
    }

    private Interceptor createConnectionStatsInterceptor() {
        return chain -> {
            final Request request = chain.request();
            return chain.proceed(request);
        };
    }

    @Data
    @AllArgsConstructor
    public static class Dog {
        private int code;
        private String name;
    }
}