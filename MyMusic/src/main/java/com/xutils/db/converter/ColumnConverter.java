package com.xutils.db.converter;

import android.database.Cursor;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午8:57
 */
public interface ColumnConverter<T> {

    T getFieldValue(final Cursor cursor, int index);

    Object fieldValue2DbValue(T fieldValue);

    com.xutils.db.sqlite.ColumnDbType getColumnDbType();
}
