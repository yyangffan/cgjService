package com.lhkj.cgjservice.HttpRequset;

import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/1/22.
 */

public class HttpUtil {
    private static OkHttpClient okHttpClient;
    private static HttpUtil httpUtile;

    public static HttpUtil getInstance() {
        if (httpUtile == null) {
            okHttpClient = new OkHttpClient();
            httpUtile = new HttpUtil();
        }
        return httpUtile;
    }


    public <T> void postRequest(String URL, HashMap<String, Object> param, Class<T> type,
                                HttpCallListener<T>
                                        httpCallListener) {
        if (httpCallListener != null) {
            httpCallListener.Start(URL);
        }
        Request request = new Request.Builder().url(URL).post(toPostParam(param)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new HttpCallBack<T>(URL, httpCallListener, type) {
        });
    }


    public <T> void formRequset(String URL, HashMap<String, Object> param, Class<T> type,
                                HttpCallListener<T> httpCallListener) {
        if (httpCallListener != null) {
            httpCallListener.Start(URL);
        }
        Request request = new Request.Builder().url(URL).post(toFormParam(param)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new HttpCallBack<T>(URL, httpCallListener, type) {
        });
    }


    public <T> void getRequest(String URL, HashMap<String, Object> param, Class<T> type,
                               HttpCallListener<T>
                                       httpCallListener) {
        if (httpCallListener != null) {
            httpCallListener.Start(URL);
        }
        Request request = new Request.Builder().url(URL + toGetParam(param)).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new HttpCallBack<T>(URL, httpCallListener, type) {
        });
    }


    public void downLoadFile(String URL, String filePath) {

    }

    private RequestBody toPostParam(HashMap<String, Object> param) {
        Iterator iter = param.entrySet().iterator();
        FormBody.Builder formBody = new FormBody.Builder();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            formBody.add(key, val);
        }
        Log.i("form",formBody.build().toString());
        return formBody.build();
    }

    private String toGetParam(HashMap<String, Object> param) {
        Iterator iter = param.entrySet().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            stringBuffer.append(key + "=" + val + "&");
        }
        stringBuffer.substring(0, stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    private RequestBody toFormParam(HashMap<String, Object> param) {
        Iterator iterator = param.entrySet().iterator();
        MultipartBody.Builder formBody = new MultipartBody.Builder();
        formBody.setType(MultipartBody.FORM);
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            Object val = entry.getValue();
            if (val instanceof File) {
                String fileKey="img_name";
                formBody.addFormDataPart(fileKey, ((File) val).getName(), RequestBody.create(MediaType.parse("image/png"), (File) val));
//                RequestBody fileBody = RequestBody.create(MediaType.parse
//                        ("application/octet-stream"), (File) val);
//                formBody.addPart(Headers.of(
//                        "Content-Disposition",
//                        "form-data; name=" + "\"img_name\";" +
//                                "filename =\"" + ((File) val).getName() + "\""), fileBody);
//                Log.i("aaaa",((File) val).getName());
            } else {
                formBody.addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, entry.getValue().toString()));
            }
        }
        return formBody.build();
    }
}
