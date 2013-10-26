package es.catmobil.wedlist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.catmobil.wedlist.database.contract.DataContract;

/**
 * Created by Bernat on 26/10/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "wedding.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 0 && newVersion == 1) {
            createTableWeedings(db);
        }
    }

    private void createTableWeedings(SQLiteDatabase db) {
        db.execSQL(DataContract.ProjectTable.createTable());
        db.execSQL(DataContract.GiftTable.createTable());
        db.execSQL(DataContract.PersonTable.createTable());
        db.execSQL(DataContract.ComplexGiftTable.createTable());
    }
}
