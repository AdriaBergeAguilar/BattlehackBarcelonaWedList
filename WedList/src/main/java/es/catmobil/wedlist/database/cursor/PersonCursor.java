package es.catmobil.wedlist.database.cursor;

import android.content.ContentValues;
import android.database.Cursor;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.base.BaseCursor;
import es.catmobil.wedlist.database.cursor.base.CursorUtils;
import es.catmobil.wedlist.model.Person;

/**
 * Created by Bernat on 26/10/13.
 */
public class PersonCursor extends BaseCursor<Person> {
    @Override
    public ContentValues setValues(Person person) {
        ContentValues values = new ContentValues();

        values.put(DataContract.PersonTable.PersonColumns.NAME, person.getName());
        values.put(DataContract.PersonTable.PersonColumns.PROFILE_GPLUS, person.getEmail());
        values.put(DataContract.PersonTable.PersonColumns.PROFILE_IMAGE_URL, person.getImage());


        return values;
    }

    @Override
    public Person readValues(Cursor cursor) {

        Person person = new Person();

        if (cursor != null) {
            CursorUtils cu = new CursorUtils(cursor);
            person.setName(cu.getString(DataContract.PersonTable.PersonColumns.NAME));
            person.setEmail(cu.getString(DataContract.PersonTable.PersonColumns.PROFILE_GPLUS));
            person.setImage(cu.getString(DataContract.PersonTable.PersonColumns.PROFILE_IMAGE_URL));
        }

        return null;
    }
}
