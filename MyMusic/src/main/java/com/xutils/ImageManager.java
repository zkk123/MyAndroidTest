package com.xutils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by wyouflf on 15/6/17.
 * 图片绑定接口
 */
public interface ImageManager {

    void bind(ImageView view, String url);

    void bind(ImageView view, String url, com.xutils.image.ImageOptions options);

    void bind(ImageView view, String url, com.xutils.common.Callback.CommonCallback<Drawable> callback);

    void bind(ImageView view, String url, com.xutils.image.ImageOptions options, com.xutils.common.Callback.CommonCallback<Drawable> callback);

    com.xutils.common.Callback.Cancelable loadDrawable(String url, com.xutils.image.ImageOptions options, com.xutils.common.Callback.CommonCallback<Drawable> callback);

    com.xutils.common.Callback.Cancelable loadFile(String url, com.xutils.image.ImageOptions options, com.xutils.common.Callback.CacheCallback<File> callback);

    void clearMemCache();

    void clearCacheFiles();
}
