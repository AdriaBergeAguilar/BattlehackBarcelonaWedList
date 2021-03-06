package es.catmobil.wedlist.database.cursor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.base.BaseCursor;
import es.catmobil.wedlist.database.cursor.base.CursorUtils;
import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Project;

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

        values.put(DataContract.ProjectTable.ProjectColumns.SERVER_ID, project.getServerId());
        values.put(DataContract.ProjectTable.ProjectColumns.NAME, project.getName());
        if (project.getDate() != null) {
            values.put(DataContract.ProjectTable.ProjectColumns.DATE, project.getDate());
        }
        values.put(DataContract.ProjectTable.ProjectColumns.EMAIL, project.getEmail());
        values.put(DataContract.ProjectTable.ProjectColumns.DESCRIPTION, project.getDescription());
        values.put(DataContract.ProjectTable.ProjectColumns.EXTRAS, project.getExtras());
        values.put(DataContract.ProjectTable.ProjectColumns.IMAGE, project.getImage());

        if (project.getGifts() != null) {
            ContentValues[] giftValues = new GiftCursor(context).setValuesArray(project.getGifts());

            context.getContentResolver().bulkInsert(DataContract.GiftTable.CONTENT_URI, giftValues);
        }

        return values;
    }

    @Override
    public Project readValues(Cursor cursor) {
        Project project = new Project();

        if (cursor != null) {
            CursorUtils cursorUtils = new CursorUtils(cursor);

            project.setServerId(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.SERVER_ID));
            project.setName(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.NAME));
            project.setDescription(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.DESCRIPTION));
            project.setEmail(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.EMAIL));
            project.setExtras(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.EXTRAS));
            project.setImage(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.IMAGE));
            project.setDate(cursorUtils.getString(DataContract.ProjectTable.ProjectColumns.DATE));

            project.setGifts(new ArrayList<Gift>());

            String where = DataContract.GiftTable.GiftColumns.PROJECT + "=" + cursorUtils.getLong(BaseColumns._ID);

            Cursor giftCursor = context.getContentResolver().query(DataContract.GiftTable.CONTENT_URI, null, where, null, null);

            if (giftCursor != null) {
                List<Gift> gifts = new ArrayList<Gift>();
                GiftCursor giftParser = new GiftCursor(context);
                while (giftCursor.moveToNext()) {
                    Gift gift = giftParser.readValues(giftCursor);
                    gifts.add(gift);
                }

                project.setGifts(gifts);
            }

        }
        return project;
    }
}
