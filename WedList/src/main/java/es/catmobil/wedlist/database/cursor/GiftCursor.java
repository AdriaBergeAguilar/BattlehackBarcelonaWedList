package es.catmobil.wedlist.database.cursor;

import android.content.ContentValues;
import android.database.Cursor;

import es.catmobil.wedlist.database.cursor.base.BaseCursor;
import es.catmobil.wedlist.model.Gift;

/**
 * Created by Bernat on 26/10/13.
 */
public class GiftCursor extends BaseCursor<Gift> {
    @Override
    public ContentValues setValues(Gift gift) {
        return null;
    }

    @Override
    public Gift readValues(Cursor cursor) {
        return null;
    }
}
