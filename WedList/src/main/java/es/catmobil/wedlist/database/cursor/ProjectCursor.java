package es.catmobil.wedlist.database.cursor;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.base.BaseCursor;
import es.catmobil.wedlist.database.cursor.base.CursorUtils;
import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Project;
import io.card.payment.o;

/**
 * Created by Bernat on 26/10/13.
 */
public class ProjectCursor extends BaseCursor<Project> {

    private Context context;

    public ProjectCursor(Context context) {
        this.context = context;
    }

    @Override
    public ContentValues setValues(Project project) {
        ContentValues values = new ContentValues();

        values.put(DataContract.ProjectTable.ProjectColumns.NAME, project.getName());
        values.put(DataContract.ProjectTable.ProjectColumns.DATE, project.getDate().getTime());
        values.put(DataContract.ProjectTable.ProjectColumns.EMAIL, project.getEmail());
        values.put(DataContract.ProjectTable.ProjectColumns.DESCRIPTION, project.getDescription());
        values.put(DataContract.ProjectTable.ProjectColumns.EXTRAS, project.getPlace());



        ContentValues[] giftValues = new GiftCursor(context).setValuesArray(project.getGifts());

        context.getContentResolver().bulkInsert(DataContract.GiftTable.CONTENT_URI, giftValues);

        return values;
    }

    @Override
    public Project readValues(Cursor cursor) {
        Project project = new Project();

        if (cursor != null) {
            CursorUtils cursorUtils = new CursorUtils(cursor);

            project.setName(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.NAME));
            project.setDescription(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.DESCRIPTION));
            project.setEmail(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.EMAIL));
            project.setPlace(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.EXTRAS));

            Date date = new Date(cursorUtils.getLong(DataContract.ProjectTable.ProjectColumns.DATE));

            Uri giftUri = ContentUris.withAppendedId(DataContract.GiftTable.CONTENT_URI_BY_PROJECT, cursorUtils.getLong(BaseColumns._ID));
            Cursor giftCursor = context.getContentResolver().query(giftUri, null, null, null, null);

            if (giftCursor != null) {
                List<Gift> gifts = new ArrayList<Gift>();
                GiftCursor giftParser = new GiftCursor(context);
                while (cursor.moveToNext()) {
                    Gift gift = giftParser.readValues(cursor);
                    gifts.add(gift);
                }

                project.setGifts(gifts);
            }

            project.setDate(date);
        }
        return project;
    }
}
