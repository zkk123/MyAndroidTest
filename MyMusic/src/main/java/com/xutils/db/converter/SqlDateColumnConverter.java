package com.xutils.db.converter;

import android.database.Cursor;

import java.sql.Date;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class SqlDateColumnConverter implements ColumnConverter<Date> {
    @Override
    public java.sql.Date getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : new java.sql.Date(cursor.getLong(index));
    }

    @Override
    public Object fieldValue2DbValue(java.sql.Date fieldValue) {
        if (fieldValue == null) return null;
        return fieldValue.getTime();
    }

    @Override
    public com.xutils.db.sqlite.ColumnDbType getColumnDbType() {
        return com.xutils.db.sqlite.ColumnDbType.INTEGER;
    }
}
