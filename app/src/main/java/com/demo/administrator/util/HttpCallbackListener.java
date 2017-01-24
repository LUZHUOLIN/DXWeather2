package com.demo.administrator.util;


public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);

}
