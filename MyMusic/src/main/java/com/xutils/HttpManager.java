package com.xutils;

/**
 * Created by wyouflf on 15/6/17.
 * http请求接口
 */
public interface HttpManager {

    /**
     * 异步GET请求
     *
     * @param entity
     * @param callback
     * @param <T>
     * @return
     */
    <T> com.xutils.common.Callback.Cancelable get(com.xutils.http.RequestParams entity, com.xutils.common.Callback.CommonCallback<T> callback);

    /**
     * 异步POST请求
     *
     * @param entity
     * @param callback
     * @param <T>
     * @return
     */
    <T> com.xutils.common.Callback.Cancelable post(com.xutils.http.RequestParams entity, com.xutils.common.Callback.CommonCallback<T> callback);

    /**
     * 异步请求
     *
     * @param method
     * @param entity
     * @param callback
     * @param <T>
     * @return
     */
    <T> com.xutils.common.Callback.Cancelable request(com.xutils.http.HttpMethod method, com.xutils.http.RequestParams entity, com.xutils.common.Callback.CommonCallback<T> callback);


    /**
     * 同步GET请求
     *
     * @param entity
     * @param resultType
     * @param <T>
     * @return
     * @throws Throwable
     */
    <T> T getSync(com.xutils.http.RequestParams entity, Class<T> resultType) throws Throwable;

    /**
     * 同步POST请求
     *
     * @param entity
     * @param resultType
     * @param <T>
     * @return
     * @throws Throwable
     */
    <T> T postSync(com.xutils.http.RequestParams entity, Class<T> resultType) throws Throwable;

    /**
     * 同步请求
     *
     * @param method
     * @param entity
     * @param resultType
     * @param <T>
     * @return
     * @throws Throwable
     */
    <T> T requestSync(com.xutils.http.HttpMethod method, com.xutils.http.RequestParams entity, Class<T> resultType) throws Throwable;

    /**
     * 同步请求
     *
     * @param method
     * @param entity
     * @param callback
     * @param <T>
     * @return
     * @throws Throwable
     */
    <T> T requestSync(com.xutils.http.HttpMethod method, com.xutils.http.RequestParams entity, com.xutils.common.Callback.TypedCallback<T> callback) throws Throwable;
}
