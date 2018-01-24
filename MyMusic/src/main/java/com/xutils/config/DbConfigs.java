package com.xutils.config;

/**
 * Created by wyouflf on 15/7/31.
 * 全局db配置
 */
public enum DbConfigs {
    HTTP(new com.xutils.DbManager.DaoConfig()
            .setDbName("xUtils_http_cache.db")
            .setDbVersion(1)
            .setDbOpenListener(new com.xutils.DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(com.xutils.DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new com.xutils.DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(com.xutils.DbManager db, int oldVersion, int newVersion) {
                    try {
                        db.dropDb(); // 默认删除所有表
                    } catch (com.xutils.ex.DbException ex) {
                        com.xutils.common.util.LogUtil.e(ex.getMessage(), ex);
                    }
                }
            })),

    COOKIE(new com.xutils.DbManager.DaoConfig()
            .setDbName("xUtils_http_cookie.db")
            .setDbVersion(1)
            .setDbOpenListener(new com.xutils.DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(com.xutils.DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new com.xutils.DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(com.xutils.DbManager db, int oldVersion, int newVersion) {
                    try {
                        db.dropDb(); // 默认删除所有表
                    } catch (com.xutils.ex.DbException ex) {
                        com.xutils.common.util.LogUtil.e(ex.getMessage(), ex);
                    }
                }
            }));

    private com.xutils.DbManager.DaoConfig config;

    DbConfigs(com.xutils.DbManager.DaoConfig config) {
        this.config = config;
    }

    public com.xutils.DbManager.DaoConfig getConfig() {
        return config;
    }
}
