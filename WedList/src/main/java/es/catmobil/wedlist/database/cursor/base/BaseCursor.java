package es.catmobil.wedlist.database.cursor.base;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * Created by alorma on 19/05/13.
 */
public abstract class BaseCursor<K> {

    public abstract ContentValues setValues(K k);

    public ContentValues[] setValuesArray(List<K> ks) {
        ContentValues[] values = new ContentValues[ks.size()];
        for (int i = 0; i < ks.size(); i++) {
            values[i] = setValues(ks.get(i));
        }
        return values;
    }

    public abstract K readValues(Cursor cursor);

}
