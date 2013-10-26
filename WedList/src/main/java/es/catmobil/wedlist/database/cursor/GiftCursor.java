package es.catmobil.wedlist.database.cursor;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.base.BaseCursor;
import es.catmobil.wedlist.database.cursor.base.CursorUtils;
import es.catmobil.wedlist.model.Gift;

/**
 * Created by Bernat on 26/10/13.
 */
public class GiftCursor extends BaseCursor<Gift> {

    private Context context;

    public GiftCursor(Context context) {
        this.context = context;
    }

    @Override
    public ContentValues setValues(Gift gift) {
        ContentValues values = new ContentValues();

        values.put(DataContract.GiftTable.GiftColumns.NAME, gift.getName());
        values.put(DataContract.GiftTable.GiftColumns.PICTURE_URL, gift.getPicturePath());
        values.put(DataContract.GiftTable.GiftColumns.PRICE, gift.getPrice());
        values.put(DataContract.GiftTable.GiftColumns.DESCRIPTION, gift.getDescription());
        values.put(DataContract.GiftTable.GiftColumns.BOUGHT, gift.isBought() ? "true" : "false");

        return values;
    }

    @Override
    public Gift readValues(Cursor cursor) {
        Gift gift = new Gift();

        if (cursor != null) {
            CursorUtils cursorUtils = new CursorUtils(cursor);

            gift.setName(cursorUtils.getString(DataContract.GiftTable.GiftColumns.NAME));
            gift.setDescription(cursorUtils.getString(DataContract.GiftTable.GiftColumns.DESCRIPTION));
            gift.setPrice(Float.parseFloat(cursorUtils.getString(DataContract.GiftTable.GiftColumns.PRICE)));
            gift.setPicturePath(cursorUtils.getString(DataContract.GiftTable.GiftColumns.PICTURE_URL));
            gift.setBought(Boolean.parseBoolean(cursorUtils.getString(DataContract.GiftTable.GiftColumns.DESCRIPTION)));

            Uri uri = ContentUris.withAppendedId(DataContract.ComplexGiftTable.CONTENT_URI, cursor.getLong(cursor.getColumnIndex(BaseColumns._ID)));

            context.getContentResolver().query(uri, null, null, null, null);
        }

        return gift;
    }
}