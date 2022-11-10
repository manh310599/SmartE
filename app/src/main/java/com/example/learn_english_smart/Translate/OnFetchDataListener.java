package com.example.learn_english_smart.Translate;

import com.example.learn_english_smart.Model.APIResponse;

public interface OnFetchDataListener {
    void onFetchData(APIResponse apiResponse,String message);
    void onError(String message);
}
