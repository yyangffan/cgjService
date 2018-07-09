package com.lhkj.cgjservice.HttpRequset;

import android.util.Log;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/22.
 */

public abstract class HttpCallBack<T> implements Callback {
    private HttpCallListener httpCallListener;
    private final String INIT = "INIT";
    private final String RESPONSE = "RESPONSE";
    private final String FAILURE = "FAILURE";
    private Class<T> cls = null;
    private String URL;

    public HttpCallBack(String URL, HttpCallListener<T> httpCallListener, Class<T> cls) {
        this.httpCallListener = httpCallListener;
        this.cls = cls;
        this.URL = URL;
//        appManager.acceptEvent(new Event(URL.hashCode()+"",URL,INIT));
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (httpCallListener != null) {
            httpCallListener.Error(URL);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (httpCallListener != null) {
            String returnString = response.body().string();
            Log.i("Http:" + URL, returnString);
            try {
                if (JsonUtil.isJson(returnString)) {
                    T t = GsonUtil.getGson().fromJson(returnString, cls);
                    httpCallListener.Success(URL, t);
                } else {
                    httpCallListener.Error(URL);
                }
            } catch (JsonSyntaxException e) {
                Log.e("gsonError"+URL,returnString);
            }
        }
    }

}
