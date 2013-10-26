package es.catmobil.wedlist.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.provider.base.MinionContentProvider;

/**
 * Created by Bernat on 26/10/13.
 */
public class GiftsByProjectProvider implements MinionContentProvider {

    @Override
    public String getBasePath() {
        return DataContract.GiftTable.BASE_PROJECT_PATH;
    }

    @Override
    public Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return db.query(DataContract.GiftTable.TABLE, projection, DataContract.GiftTable.GiftColumns.PROJECT + "=" + uri.getLastPathSegment(), null, null, null, sortOrder);
    }

    @Override
    public long insert(SQLiteDatabase db, Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(SQLiteDatabase db, Uri uri, String where, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(SQLiteDatabase db, Uri uri, ContentValues values, String where, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType() {
        return DataContract.GiftTable.BASE_TYPE;
    }
}
