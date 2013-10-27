package es.catmobil.wedlist.database.cursor;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.base.BaseCursor;
import es.catmobil.wedlist.database.cursor.base.CursorUtils;
import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Person;

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
        values.put(DataContract.GiftTable.GiftColumns.SERVER_ID, gift.getServerId());
        values.put(DataContract.GiftTable.GiftColumns.PROJECT, gift.getProject());
        values.put(DataContract.GiftTable.GiftColumns.PROJECT_ID, gift.getProjectId());
        values.put(DataContract.GiftTable.GiftColumns.BOUGHT, gift.isBought() ? "true" : "false");
        values.put(DataContract.GiftTable.GiftColumns.COMPLEX, gift.isComplex() ? "true" : "false");

        return values;
    }

    @Override
    public Gift readValues(Cursor cursor) {
        Gift gift = new Gift();

        if (cursor != null) {
            CursorUtils cursorUtils = new CursorUtils(cursor);

            gift.setName(cursorUtils.getString(DataContract.GiftTable.GiftColumns.NAME));
            gift.setDescription(cursorUtils.getString(DataContract.GiftTable.GiftColumns.DESCRIPTION));
            gift.setPicturePath(cursorUtils.getString(DataContract.GiftTable.GiftColumns.PICTURE_URL));
            gift.setServerId(cursorUtils.getString(DataContract.GiftTable.GiftColumns.SERVER_ID));
            gift.setProject(cursorUtils.getString(DataContract.GiftTable.GiftColumns.PROJECT));
            gift.setProjectId(cursorUtils.getLong(DataContract.GiftTable.GiftColumns.PROJECT_ID));
            gift.setBought(Boolean.parseBoolean(cursorUtils.getString(DataContract.GiftTable.GiftColumns.BOUGHT)));
            gift.setComplex(Boolean.parseBoolean(cursorUtils.getString(DataContract.GiftTable.GiftColumns.COMPLEX)));
            gift.setPrice(Float.parseFloat(cursorUtils.getString(DataContract.GiftTable.GiftColumns.PRICE)));
            List<Person> persons = new ArrayList<Person>();
            gift.setBuyers(persons);


            Uri uri2 = Uri.parse("content://es.catmobil.wedlist.provider/complexGift/" + cursor.getString(cursor.getColumnIndex(BaseColumns._ID)));

            Log.i("GIFT-CU", "Uri to request persons: " + uri2.toString());

            if (uri2 != null) {
                Cursor personsIdCursor = context.getContentResolver().query(uri2, null, null, null, null);
                if (personsIdCursor != null) {
                    while (personsIdCursor.moveToNext()) {
                        Uri personUri = Uri.withAppendedPath(DataContract.PersonTable.CONTENT_ITEM_URI,
                                personsIdCursor.getString(
                                        personsIdCursor.getColumnIndex(
                                                DataContract.PersonsInGiftTable.ComplexGiftColumns.PAYER)));

                        Cursor person = context.getContentResolver().query(personUri, null, null, null, null);
                        if (person != null && person.moveToFirst()) {
                            Person p = new PersonCursor().readValues(cursor);
                            persons.add(p);
                        }
                    }
                    gift.setBuyers(persons);
                }
            }
        }

        return gift;
    }
}
