package com.lhkj.cgjservice.HttpRequset;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/1/23.
 */

public interface HttpCallListener<T> {
    void Start(String URL);

    void Success(String URL, @NonNull T bean);

    void Error(String URL);
}
