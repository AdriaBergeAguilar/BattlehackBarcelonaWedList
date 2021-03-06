package es.catmobil.wedlist.provider;

import android.database.sqlite.SQLiteDatabase;

import es.catmobil.wedlist.application.AppConfig;
import es.catmobil.wedlist.database.DatabaseHelper;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.provider.base.DespicableContentProvider;
import es.catmobil.wedlist.provider.base.SimpleItemMinionProvider;
import es.catmobil.wedlist.provider.base.SimpleMinionProvider;

/**
 * Created by Bernat on 26/10/13.
 */
public class WedListProvider extends DespicableContentProvider {

    private SQLiteDatabase db;

    @Override
    public void recruitMinions() {

        // WEDDINGS
        addMinion(new SimpleMinionProvider(DataContract.ProjectTable.TABLE, DataContract.ProjectTable.BASE_PATH, DataContract.ProjectTable.BASE_TYPE));
        addMinion(new SimpleItemMinionProvider(DataContract.ProjectTable.TABLE, DataContract.ProjectTable.BASE_ITEM_PATH, DataContract.ProjectTable.BASE_ITEM_TYPE));

        // GIFTS
        addMinion(new SimpleMinionProvider(DataContract.GiftTable.TABLE, DataContract.GiftTable.BASE_PATH, DataContract.GiftTable.BASE_TYPE));
        addMinion(new SimpleItemMinionProvider(DataContract.GiftTable.TABLE, DataContract.GiftTable.BASE_ITEM_PATH, DataContract.GiftTable.BASE_ITEM_TYPE));

        addMinion(new SimpleMinionProvider(DataContract.GiftTable.TABLE, DataContract.ProjectTable.BASE_PATH + "/" + DataContract.GiftTable.BASE_PATH, DataContract.GiftTable.BASE_TYPE));
        addMinion(new GiftsByProjectIdProvider());

        // PERSONS
        addMinion(new SimpleMinionProvider(DataContract.PersonTable.TABLE, DataContract.PersonTable.BASE_PATH, DataContract.PersonTable.BASE_TYPE));
        addMinion(new SimpleItemMinionProvider(DataContract.PersonTable.TABLE, DataContract.PersonTable.BASE_ITEM_PATH, DataContract.PersonTable.BASE_ITEM_TYPE));

        // PERSONS IN GIFT
        addMinion(new SimpleMinionProvider(DataContract.PersonsInGiftTable.TABLE, DataContract.PersonsInGiftTable.PERSONS_BY_GIFT, DataContract.PersonTable.BASE_TYPE));
        addMinion(new PayersGiftProvider());
    }

    @Override
    public String getAuthority() {
        return AppConfig.AUTHORITY;
    }

    @Override
    public SQLiteDatabase getDb() {
        if (db == null) {
            db = new DatabaseHelper(getContext()).getWritableDatabase();
        }
        return db;
    }
}
