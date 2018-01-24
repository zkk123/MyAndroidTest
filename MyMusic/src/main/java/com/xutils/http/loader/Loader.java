package com.xutils.http.loader;


import android.text.TextUtils;

import com.xutils.http.request.UriRequest;

import java.io.InputStream;
import java.util.Date;

/**
 * Author: wyouflf
 * Time: 2014/05/26
 */
public abstract class Loader<T> {

    protected com.xutils.http.RequestParams params;
    protected com.xutils.http.ProgressHandler progressHandler;

    public void setParams(final com.xutils.http.RequestParams params) {
        this.params = params;
    }

    public void setProgressHandler(final com.xutils.http.ProgressHandler callbackHandler) {
        this.progressHandler = callbackHandler;
    }

    protected void saveStringCache(UriRequest request, String resultStr) {
        if (!TextUtils.isEmpty(resultStr)) {
            com.xutils.cache.DiskCacheEntity entity = new com.xutils.cache.DiskCacheEntity();
            entity.setKey(request.getCacheKey());
            entity.setLastAccess(System.currentTimeMillis());
            entity.setEtag(request.getETag());
            entity.setExpires(request.getExpiration());
            entity.setLastModify(new Date(request.getLastModified()));
            entity.setTextContent(resultStr);
            com.xutils.cache.LruDiskCache.getDiskCache(request.getParams().getCacheDirName()).put(entity);
        }
    }

    public abstract Loader<T> newInstance();

    public abstract T load(final InputStream in) throws Throwable;

    public abstract T load(final UriRequest request) throws Throwable;

    public abstract T loadFromCache(final com.xutils.cache.DiskCacheEntity cacheEntity) throws Throwable;

    public abstract void save2Cache(final UriRequest request);
}
