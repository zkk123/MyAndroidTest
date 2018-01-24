package com.xutils.http.request;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by wyouflf on 15/7/23.
 * Uri请求发送和数据接收
 */
public abstract class UriRequest implements Closeable {

    protected final String queryUrl;
    protected final com.xutils.http.RequestParams params;
    protected final com.xutils.http.loader.Loader<?> loader;

    protected ClassLoader callingClassLoader = null;
    protected com.xutils.http.ProgressHandler progressHandler = null;
    protected com.xutils.http.app.RequestInterceptListener requestInterceptListener = null;

    /*package*/ UriRequest(com.xutils.http.RequestParams params, Type loadType) throws Throwable {
        this.params = params;
        this.queryUrl = buildQueryUrl(params);
        this.loader = com.xutils.http.loader.LoaderFactory.getLoader(loadType, params);
    }

    // build query
    protected String buildQueryUrl(com.xutils.http.RequestParams params) {
        return params.getUri();
    }

    public void setProgressHandler(com.xutils.http.ProgressHandler progressHandler) {
        this.progressHandler = progressHandler;
        this.loader.setProgressHandler(progressHandler);
    }

    public void setCallingClassLoader(ClassLoader callingClassLoader) {
        this.callingClassLoader = callingClassLoader;
    }

    public void setRequestInterceptListener(com.xutils.http.app.RequestInterceptListener requestInterceptListener) {
        this.requestInterceptListener = requestInterceptListener;
    }

    public com.xutils.http.RequestParams getParams() {
        return params;
    }

    public String getRequestUri() {
        return queryUrl;
    }

    /**
     * invoke via Loader
     *
     * @throws IOException
     */
    public abstract void sendRequest() throws Throwable;

    public abstract boolean isLoading();

    public abstract String getCacheKey();

    /**
     * 由loader发起请求, 拿到结果.
     *
     * @return
     * @throws Throwable
     */
    public Object loadResult() throws Throwable {
        return this.loader.load(this);
    }

    /**
     * 尝试从缓存获取结果, 并为请求头加入缓存控制参数.
     *
     * @return
     * @throws Throwable
     */
    public abstract Object loadResultFromCache() throws Throwable;

    public abstract void clearCacheHeader();

    public void save2Cache() {
        com.xutils.x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    loader.save2Cache(UriRequest.this);
                } catch (Throwable ex) {
                    com.xutils.common.util.LogUtil.e(ex.getMessage(), ex);
                }
            }
        });
    }

    public abstract InputStream getInputStream() throws IOException;

    @Override
    public abstract void close() throws IOException;

    public abstract long getContentLength();

    public abstract int getResponseCode() throws IOException;

    public abstract String getResponseMessage() throws IOException;

    public abstract long getExpiration();

    public abstract long getLastModified();

    public abstract String getETag();

    public abstract String getResponseHeader(String name);

    public abstract Map<String, List<String>> getResponseHeaders();

    public abstract long getHeaderFieldDate(String name, long defaultValue);

    @Override
    public String toString() {
        return getRequestUri();
    }
}
