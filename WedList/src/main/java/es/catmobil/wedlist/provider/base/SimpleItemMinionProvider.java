package es.catmobil.wedlist.provider.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Bernat on 26/10/13.
 */
public class SimpleItemMinionProvider implements MinionContentProvider{

    private String table;
    private String path;
    private String type;

    public SimpleItemMinionProvider(String table, String path, String type) {

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
        return db.query(table, projection, BaseColumns._ID + "=" + uri.getLastPathSegment(), null, null, null, sortOrder);
    }

    @Override
    public long insert(SQLiteDatabase db, Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Not able to insert in a SimpleItemMinionProvider");
    }

    @Override
    public int delete(SQLiteDatabase db, Uri uri, String where, String[] selectionArgs) {
        return db.delete(table, BaseColumns._ID + "=" + uri.getLastPathSegment(), null);
    }

    @Override
    public int update(SQLiteDatabase db, Uri uri, ContentValues values, String where, String[] selectionArgs) {
        return db.update(table, values, BaseColumns._ID + "=" + uri.getLastPathSegment(), null);
    }

    @Override
    public String getType() {
        return type;
    }
}
