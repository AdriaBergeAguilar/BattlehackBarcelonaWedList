package es.catmobil.wedlist.database.contract;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import es.catmobil.wedlist.BuildConfig;

/**
 * Created by Bernat on 26/10/13.
 */
public class DataContract {

    private static final Uri uri = Uri.parse("content://" + BuildConfig.AUTHORITY);

    public static class ProjectTable {

        public static final String TABLE = "projects";

        public static final String BASE_PATH = TABLE;
        public static final String BASE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd." + BASE_PATH;
        public static final Uri CONTENT_URI = Uri.withAppendedPath(uri, BASE_PATH);

        public static final String BASE_ITEM_PATH = BASE_PATH + "/#";
        public static final String BASE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "vnd." + BASE_PATH;
        public static final Uri CONTENT_ITEM_URI = Uri.withAppendedPath(uri, BASE_ITEM_PATH);

        public static class ProjectColumns implements BaseColumns {
            public static final String TITLE = "TITLE";
            public static final String DATE = "DATE";
            public static final String NAME = "NAME";
            public static final String DESCRIPTION = "DESCRIPTION";
            public static final String IMAGE = "IMAGE";
            public static final String THANKS_TXT = "THANKS_TXT";
            public static final String PAYPAL_ACCOUNT = "PAYPAL_ACCOUNT";
            public static final String REMMAINING = "REMMAINING";
        }

        public static String createTable() {

            StringBuffer stringBuffer = new StringBuffer("CREATE TABLE ");
            stringBuffer.append(TABLE);
            stringBuffer.append(" (" + ProjectColumns._ID + " integer primarykey autoincrement");
            stringBuffer.append(" " + ProjectColumns.DATE + " TEXT");
            stringBuffer.append(" " + ProjectColumns.TITLE + " TEXT");
            stringBuffer.append(" " + ProjectColumns.NAME + " TEXT");
            stringBuffer.append(" " + ProjectColumns.DESCRIPTION + " TEXT");
            stringBuffer.append(" " + ProjectColumns.IMAGE + " TEXT");
            stringBuffer.append(" " + ProjectColumns.THANKS_TXT + " TEXT");
            stringBuffer.append(" " + ProjectColumns.PAYPAL_ACCOUNT + " TEXT");
            stringBuffer.append(" " + ProjectColumns.REMMAINING + " TEXT");
            stringBuffer.append(");");


            return stringBuffer.toString();
        }
    }

    public static class GiftTable {

        public static final String TABLE = "gifts";
        public static final String BASE_PATH = TABLE;
        public static final String BASE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd." + BASE_PATH;
        public static final Uri CONTENT_URI = Uri.withAppendedPath(uri, BASE_PATH);

        public static final String BASE_ITEM_PATH = BASE_PATH + "/#";
        public static final String BASE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "vnd." + BASE_PATH;
        public static final Uri CONTENT_ITEM_URI = Uri.withAppendedPath(uri, BASE_ITEM_PATH);

        public static class GiftColumns implements BaseColumns {
            public static final String NAME = "NAME";
            public static final String PICTURE_URL = "PICTURE_URL";
            public static final String PRICE = "PRICE";
            public static final String PAYER = "PAYER";
            public static final String WEDDING = "WEDDING";
        }

        public static String createTable() {

            StringBuffer stringBuffer = new StringBuffer("CREATE TABLE ");
            stringBuffer.append(TABLE);
            stringBuffer.append(" (" + GiftColumns._ID + " integer primarykey autoincrement");
            stringBuffer.append(" " + GiftColumns.NAME + " TEXT");
            stringBuffer.append(" " + GiftColumns.PICTURE_URL + " TEXT");
            stringBuffer.append(" " + GiftColumns.PRICE + " TEXT");
            stringBuffer.append(" " + GiftColumns.PAYER + " TEXT");
            stringBuffer.append(" " + GiftColumns.WEDDING + " TEXT");
            stringBuffer.append(");");


            return stringBuffer.toString();
        }
    }

    public static class PersonTable {

        public static final String TABLE = "persons";
        public static final String BASE_PATH = TABLE;
        public static final String BASE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd." + BASE_PATH;
        public static final Uri CONTENT_URI = Uri.withAppendedPath(uri, BASE_PATH);

        public static final String BASE_ITEM_PATH = BASE_PATH + "/#";
        public static final String BASE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "vnd." + BASE_PATH;
        public static final Uri CONTENT_ITEM_URI = Uri.withAppendedPath(uri, BASE_ITEM_PATH);

        public static class PersonColumns implements BaseColumns {
            public static final String NAME = "NAME";
            public static final String PROFILE_URL = "PROFILE_URL";
            public static final String PROFILE_GPLUS = "PROFILE_GPLUS";
        }

        public static String createTable() {

            StringBuffer stringBuffer = new StringBuffer("CREATE TABLE ");
            stringBuffer.append(TABLE);
            stringBuffer.append(" (" + PersonColumns._ID + " integer primarykey autoincrement");
            stringBuffer.append(" " + PersonColumns.NAME + " TEXT");
            stringBuffer.append(" " + PersonColumns.PROFILE_URL + " TEXT");
            stringBuffer.append(" " + PersonColumns.PROFILE_GPLUS + " TEXT");
            stringBuffer.append(");");


            return stringBuffer.toString();
        }
    }

    public static class ComplexGiftTable {

        public static final String TABLE = "complexGift";

        public static final String PERSONS_BY_GIFT = TABLE + "/#";
        public static final String BASE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "vnd." + PersonTable.BASE_PATH;
        public static final Uri CONTENT_URI = Uri.withAppendedPath(uri, PERSONS_BY_GIFT);

        public static class ComplexGiftColumns implements BaseColumns {
            public static final String GIFT = "GIFT";
            public static final String PAYER = "PAYER";
            public static final String AMOUNT = "AMOUNT";
            public static final String DATE = "DATE";
        }

        public static String createTable() {

            StringBuffer stringBuffer = new StringBuffer("CREATE TABLE ");
            stringBuffer.append(TABLE);
            stringBuffer.append(" (" + ComplexGiftColumns._ID + " integer primarykey autoincrement");
            stringBuffer.append(" " + ComplexGiftColumns.GIFT + " TEXT");
            stringBuffer.append(" " + ComplexGiftColumns.PAYER + " TEXT");
            stringBuffer.append(" " + ComplexGiftColumns.AMOUNT + " TEXT");
            stringBuffer.append(" " + ComplexGiftColumns.DATE + " TEXT");
            stringBuffer.append(");");

            return stringBuffer.toString();
        }
    }
}
