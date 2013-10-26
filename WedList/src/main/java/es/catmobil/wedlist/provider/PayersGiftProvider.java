package es.catmobil.wedlist.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.provider.base.MinionContentProvider;

/**
 * Created by Bernat on 26/10/13.
 */
public class PayersGiftProvider implements MinionContentProvider {

    @Override
    public String getBasePath() {
        return DataContract.PersonsInGiftTable.PERSONS_BY_GIFT;
    }

    @Override
    public Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String[] columns = new String[]{
                DataContract.PersonTable.PersonColumns._ID,
                DataContract.PersonTable.PersonColumns.NAME,
                DataContract.PersonTable.PersonColumns.PROFILE_IMAGE_URL,
                DataContract.PersonTable.PersonColumns.PROFILE_GPLUS,
                DataContract.PersonsInGiftTable.ComplexGiftColumns.AMOUNT
        };

        MatrixCursor c = new MatrixCursor(columns);

        Cursor personsID = db.query(DataContract.PersonsInGiftTable.TABLE, null, DataContract.PersonsInGiftTable.ComplexGiftColumns.GIFT + "=" + ContentUris.parseId(uri), null, null, null, null);

        if (personsID != null) {
            int i = 0;
            while (personsID.moveToNext()) {
                MatrixCursor.RowBuilder newRow = c.newRow();

                String where = BaseColumns._ID + "=" + c.getLong(c.getColumnIndex(DataContract.PersonsInGiftTable.ComplexGiftColumns.PAYER));
                Cursor p = db.query(DataContract.PersonTable.TABLE, null, where, null, null, null, null);

                if (p != null && p.moveToFirst()) {
                    newRow.add(p.getLong(p.getColumnIndex(DataContract.PersonTable.PersonColumns._ID)));
                    newRow.add(p.getString(p.getColumnIndex(DataContract.PersonTable.PersonColumns.NAME)));
                    newRow.add(p.getString(p.getColumnIndex(DataContract.PersonTable.PersonColumns.PROFILE_IMAGE_URL)));
                    newRow.add(p.getString(p.getColumnIndex(DataContract.PersonTable.PersonColumns.PROFILE_GPLUS)));
                }
                newRow.add(personsID.getInt(personsID.getColumnIndex(DataContract.PersonsInGiftTable.ComplexGiftColumns.AMOUNT)));
            }
        }

        return c;
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
