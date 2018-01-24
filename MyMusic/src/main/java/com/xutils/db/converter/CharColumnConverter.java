package com.xutils.db.converter;

import android.database.Cursor;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class CharColumnConverter implements ColumnConverter<Character> {
    @Override
    public Character getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : (char) cursor.getInt(index);
    }

    @Override
    public Object fieldValue2DbValue(Character fieldValue) {
        if (fieldValue == null) return null;
        return (int) fieldValue;
    }

    @Override
    public com.xutils.db.sqlite.ColumnDbType getColumnDbType() {
        return com.xutils.db.sqlite.ColumnDbType.INTEGER;
    }
}
