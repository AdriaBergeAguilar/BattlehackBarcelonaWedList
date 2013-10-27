package es.catmobil.wedlist.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.paypal.android.sdk.u;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.provider.base.MinionContentProvider;

/**
 * Created by Bernat on 26/10/13.
 */
public class PayersGiftProvider implements MinionContentProvider {

    @Override
    public String getBasePath() {
        return DataContract.PersonsInGiftTable.PERSONS_BY_GIFT_ITEM;
    }

    @Override
    public Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor giftCursor = db.query(DataContract.GiftTable.TABLE, null, BaseColumns._ID + "=" + uri.getLastPathSegment(), null, null, null, null);

        if (giftCursor != null && giftCursor.moveToFirst()) {

            String giftId = giftCursor.getString(giftCursor.getColumnIndex(DataContract.GiftTable.GiftColumns.SERVER_ID));

            String[] columns = new String[]{
                    DataContract.PersonTable.PersonColumns._ID,
                    DataContract.PersonTable.PersonColumns.NAME,
                    DataContract.PersonTable.PersonColumns.PROFILE_IMAGE_URL,
                    DataContract.PersonTable.PersonColumns.PROFILE_GPLUS,
                    DataContract.PersonsInGiftTable.ComplexGiftColumns.AMOUNT
            };

            MatrixCursor c = new MatrixCursor(columns);

            Cursor personsPaymentOnGift = db.query(DataContract.PersonsInGiftTable.TABLE, null, DataContract.PersonsInGiftTable.ComplexGiftColumns.GIFT + " LIKE '%" + giftId + "%'", null, null, null, null);

            if (personsPaymentOnGift != null) {
                while (personsPaymentOnGift.moveToNext()) {
                    MatrixCursor.RowBuilder newRow = c.newRow();

                    String where = DataContract.PersonTable.PersonColumns.SERVER_ID + " LIKE '%" + personsPaymentOnGift.getString(personsPaymentOnGift.getColumnIndex(DataContract.PersonsInGiftTable.ComplexGiftColumns.PAYER)) + "%'";
                    Cursor p = db.query(DataContract.PersonTable.TABLE, null, where, null, null, null, null);

                    if (p != null && p.moveToFirst()) {
                        newRow.add(p.getLong(p.getColumnIndex(DataContract.PersonTable.PersonColumns._ID)));
                        newRow.add(p.getString(p.getColumnIndex(DataContract.PersonTable.PersonColumns.NAME)));
                        newRow.add(p.getString(p.getColumnIndex(DataContract.PersonTable.PersonColumns.PROFILE_IMAGE_URL)));
                        newRow.add(p.getString(p.getColumnIndex(DataContract.PersonTable.PersonColumns.PROFILE_GPLUS)));
                    }
                    newRow.add(personsPaymentOnGift.getInt(personsPaymentOnGift.getColumnIndex(DataContract.PersonsInGiftTable.ComplexGiftColumns.AMOUNT)));
                }
            }

            return c;
        }
        return null;
    }

    @Override
    public long insert(SQLiteDatabase db, Uri uri, ContentValues contentValues) {
        return 0;
    }

    @Override
    public int delete(SQLiteDatabase db, Uri uri, String where, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(SQLiteDatabase db, Uri uri, ContentValues values, String where, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType() {
        return DataContract.PersonsInGiftTable.BASE_TYPE;
    }
}
