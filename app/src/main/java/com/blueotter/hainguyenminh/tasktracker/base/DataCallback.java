package com.blueotter.hainguyenminh.tasktracker.base;

/**
 * Created by HaiNM on 21/03/2018.
 */

public interface DataCallback<T> {

    void onSuccess(T data);

    void onError(int errorCode);
}
