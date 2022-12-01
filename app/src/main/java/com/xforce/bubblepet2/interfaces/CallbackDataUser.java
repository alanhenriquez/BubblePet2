package com.xforce.bubblepet2.interfaces;

import com.google.firebase.database.DataSnapshot;

import java.util.Map;

public interface CallbackDataUser {
    void onReadData(DataSnapshot value);

    void onChildrenCount(int count);

    void onChildrenTotalCount(int totalCount);

    void onHashMapValue(Map<String, Object> map);

    void onHashMapValue(String key, Object value);
}
