package es.catmobil.wedlist.provider.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.paypal.android.sdk.s;

/**
 * Created by Bernat on 26/10/13.
 */
public class SimpleMinionProvider implements MinionContentProvider {

    private String table;
    private String path;
    private String type;

    public SimpleMinionProvider(String table, String path, String type) {

        this.table = table;
        this.path = path;
        this.type = type;
    }

    @Override
    public String getBasePath() {
        return path;
    }

    @Override
    public Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return db.query(table, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public long insert(SQLiteDatabase db, Uri uri, ContentValues contentValues) {
        return db.insert(table, null, contentValues);
    }

    @Override
    public int delete(SQLiteDatabase db, Uri uri, String where, String[] selectionArgs) {
        return db.delete(table, where, selectionArgs);
    }

    @Override
    public int update(SQLiteDatabase db, Uri uri, ContentValues values, String where, String[] selectionArgs) {
        return db.update(table, values, where, selectionArgs);
    }

    @Override
    public String getType() {
        return type;
    }
}
