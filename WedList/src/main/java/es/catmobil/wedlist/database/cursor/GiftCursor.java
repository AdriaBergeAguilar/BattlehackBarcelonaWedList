package es.catmobil.wedlist.database.cursor;

import android.content.ContentValues;
import android.database.Cursor;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.base.BaseCursor;
import es.catmobil.wedlist.database.cursor.base.CursorUtils;
import es.catmobil.wedlist.model.Gift;

/**
 * Created by Bernat on 26/10/13.
 */
public class GiftCursor extends BaseCursor<Gift> {
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
        }

        return gift;
    }
}
