package com.xutils.http;

import com.xutils.HttpManager;

import com.xutils.x;

import java.lang.reflect.Type;

/**
 * Created by wyouflf on 15/7/23.
 * HttpManager实现
 */
public final class HttpManagerImpl implements HttpManager {

    private static final Object lock = new Object();
    private static volatile HttpManagerImpl instance;

    private HttpManagerImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new HttpManagerImpl();
                }
            }
        }
        x.Ext.setHttpManager(instance);
    }

    @Override
    public <T> com.xutils.common.Callback.Cancelable get(RequestParams entity, com.xutils.common.Callback.CommonCallback<T> callback) {
        return request(HttpMethod.GET, entity, callback);
    }

    @Override
    public <T> com.xutils.common.Callback.Cancelable post(RequestParams entity, com.xutils.common.Callback.CommonCallback<T> callback) {
        return request(HttpMethod.POST, entity, callback);
    }

    @Override
    public <T> com.xutils.common.Callback.Cancelable request(HttpMethod method, RequestParams entity, com.xutils.common.Callback.CommonCallback<T> callback) {
        entity.setMethod(method);
        com.xutils.common.Callback.Cancelable cancelable = null;
        if (callback instanceof com.xutils.common.Callback.Cancelable) {
            cancelable = (com.xutils.common.Callback.Cancelable) callback;
        }
        HttpTask<T> task = new HttpTask<T>(entity, cancelable, callback);
        return x.task().start(task);
    }

    @Override
    public <T> T getSync(RequestParams entity, Class<T> resultType) throws Throwable {
        return requestSync(HttpMethod.GET, entity, resultType);
    }

    @Override
    public <T> T postSync(RequestParams entity, Class<T> resultType) throws Throwable {
        return requestSync(HttpMethod.POST, entity, resultType);
    }

    @Override
    public <T> T requestSync(HttpMethod method, RequestParams entity, Class<T> resultType) throws Throwable {
        DefaultSyncCallback<T> callback = new DefaultSyncCallback<T>(resultType);
        return requestSync(method, entity, callback);
    }

    @Override
    public <T> T requestSync(HttpMethod method, RequestParams entity, com.xutils.common.Callback.TypedCallback<T> callback) throws Throwable {
        entity.setMethod(method);
        HttpTask<T> task = new HttpTask<T>(entity, null, callback);
        return x.task().startSync(task);
    }

    private class DefaultSyncCallback<T> implements com.xutils.common.Callback.TypedCallback<T> {

        private final Class<T> resultType;

        public DefaultSyncCallback(Class<T> resultType) {
            this.resultType = resultType;
        }

        @Override
        public Type getLoadType() {
            return resultType;
        }

        @Override
        public void onSuccess(T result) {

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }
}
