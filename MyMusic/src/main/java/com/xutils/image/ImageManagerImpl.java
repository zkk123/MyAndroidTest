package com.xutils.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.xutils.ImageManager;

import com.xutils.x;

import java.io.File;

/**
 * Created by wyouflf on 15/10/9.
 */
public final class ImageManagerImpl implements ImageManager {

    private static final Object lock = new Object();
    private static volatile ImageManagerImpl instance;

    private ImageManagerImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ImageManagerImpl();
                }
            }
        }
        x.Ext.setImageManager(instance);
    }


    @Override
    public void bind(final ImageView view, final String url) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                ImageLoader.doBind(view, url, null, null);
            }
        });
    }

    @Override
    public void bind(final ImageView view, final String url, final ImageOptions options) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                ImageLoader.doBind(view, url, options, null);
            }
        });
    }

    @Override
    public void bind(final ImageView view, final String url, final com.xutils.common.Callback.CommonCallback<Drawable> callback) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                ImageLoader.doBind(view, url, null, callback);
            }
        });
    }

    @Override
    public void bind(final ImageView view, final String url, final ImageOptions options, final com.xutils.common.Callback.CommonCallback<Drawable> callback) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                ImageLoader.doBind(view, url, options, callback);
            }
        });
    }

    @Override
    public com.xutils.common.Callback.Cancelable loadDrawable(String url, ImageOptions options, com.xutils.common.Callback.CommonCallback<Drawable> callback) {
        return ImageLoader.doLoadDrawable(url, options, callback);
    }

    @Override
    public com.xutils.common.Callback.Cancelable loadFile(String url, ImageOptions options, com.xutils.common.Callback.CacheCallback<File> callback) {
        return ImageLoader.doLoadFile(url, options, callback);
    }

    @Override
    public void clearMemCache() {
        ImageLoader.clearMemCache();
    }

    @Override
    public void clearCacheFiles() {
        ImageLoader.clearCacheFiles();
        ImageDecoder.clearCacheFiles();
    }
}
