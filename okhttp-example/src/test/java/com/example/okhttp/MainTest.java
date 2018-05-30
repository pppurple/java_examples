package com.example.okhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.junit.Test;

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
                .addInterceptor(headerInterceptor())
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

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                String body = response.body().string();
                System.out.println("body: " + body);
                Dog dog = mapper.readValue(body, Dog.class);
                System.out.println("deserialized: " + dog);
            }
        }
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

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
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

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }

    @Test
    public void postForm() throws Exception {
        String url = "http://localhost:8080/hello";

        Map<String, String> formParamMap = new HashMap<>();
        formParamMap.put("name", "abc");
        formParamMap.put("code", "123");

        // Names and values will be url encoded
        final FormBody.Builder formBuilder = new FormBody.Builder();
        formParamMap.forEach(formBuilder::add);
        RequestBody requestBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }

    @Test
    public void postJson() throws Exception {
        String url = "http://localhost:8080/dog_json";

        Dog dog = new Dog(100, "pome");
        RequestBody requestBody = RequestBody.create(JSON, mapper.writeValueAsString(dog));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }

    @Test
    public void put() throws Exception {
        String url = "http://localhost:8080/dog_json";

        Dog dog = new Dog(100, "pome");
        RequestBody requestBody = RequestBody.create(JSON, mapper.writeValueAsString(dog));

        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }

    @Test
    public void delete() throws Exception {
        String url = "http://localhost:8080/hello";

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }

    @Test
    public void connectionPool() throws Exception {
        String url = "http://localhost:8080/hello";

        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }

    @Test
    public void interceptor() throws Exception {
        String url = "http://localhost:8080/hello";

        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor())
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }

    @Test
    public void networkInterceptor() throws Exception {
        String url = "http://localhost:8080/redirect";

        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BODY))
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(Level.BODY))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }

    private Interceptor headerInterceptor() {
        return chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .header("my-header", "abcde")
                    .build();
            return chain.proceed(request);
        };
    }

    private Interceptor connectionStatsInterceptor() {
        return chain -> {
            final Request request = chain.request();
            return chain.proceed(request);
        };
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Dog {
        private int id;
        private String name;
    }
}