package com.tyas.test.assignyas.repository.service.server;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tyas.test.assignyas.BuildConfig;
import com.tyas.test.assignyas.repository.entity.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tyas on 7/23/16.
 */
public class GetService {

    private OkHttpClient client = new OkHttpClient();
    private Request request;

    public static GetService getInstance() {
        return new GetService();
    }

    public void getAll(final LoadPattern loadPattern) {
        request = new Request.Builder()
                .url(BuildConfig.BASE_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        loadPattern.onLoadFailed("Data not loaded");
                    }
                });
            }

            @Override public void onResponse(Call call, final Response response) throws IOException {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        if (response.isSuccessful()) {
                            try {
                                List<Data> datas = new ArrayList<>();
                                Log.d(getClass().getName(), "response : " + response.body().toString());
                                JSONArray jsonArray = new JSONArray(response.body().string());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Data data = new Data();
                                    data.setUserId(jsonObject.getInt("userId"));
                                    data.setId(jsonObject.getInt("id"));
                                    data.setTitle(jsonObject.getString("title"));
                                    data.setBody(jsonObject.getString("body"));
                                    datas.add(data);
                                }
                                if (!datas.isEmpty()) {
                                    loadPattern.onLoadSuccess(datas);
                                } else {
                                    loadPattern.onLoadFailed("Data null");
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loadPattern.onLoadFailed("Data not loaded");
                        }
                    }
                });
            }
        });
    }

    public interface LoadPattern {
        void onLoadSuccess(List<Data> datas);

        void onLoadFailed(String message);
    }

}
