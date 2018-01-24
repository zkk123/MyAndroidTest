package com.xutils.http.body;


/**
 * Created by wyouflf on 15/8/13.
 */
public interface ProgressBody extends RequestBody {
    void setProgressHandler(com.xutils.http.ProgressHandler progressHandler);
}
