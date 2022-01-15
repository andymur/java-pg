package com.andymur.pg.java.rest;

import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class SimpleClientRunner {
    public static void main(String[] args) throws IOException {

        EventListener listener = new EventListener() {
            @Override
            public void callStart(@NotNull Call call) {
                super.callStart(call);
                System.out.println("Hey I'm started! " + System.currentTimeMillis() + " " + call.hashCode());
            }

            @Override
            public void callEnd(@NotNull Call call) {
                super.callEnd(call);
                System.out.println("Hey I'm done! " + System.currentTimeMillis() + " " + call.hashCode());
            }

            @Override
            public void callFailed(@NotNull Call call, @NotNull IOException ioe) {
                super.callFailed(call, ioe);
                System.out.println("Hey I'm failed! " + System.currentTimeMillis());
            }
        };

        OkHttpClient client = new OkHttpClient(new OkHttpClient.Builder().eventListener(listener));


        Request request = new Request.Builder()
                .url("https://google.com")
                .build();

        for (int i = 0; i < 10; i++) {
            Objects.requireNonNull(client.newCall(request).execute().body()).close();
        }
    }
}
