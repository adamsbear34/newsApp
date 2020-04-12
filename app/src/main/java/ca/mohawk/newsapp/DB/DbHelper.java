package ca.mohawk.newsapp.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "news.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE = "CREATE TABLE newsTable ( _id INTEGER PRIMARY KEY, title TEXT, source TEXT, description TEXT)";
    /**
     *
     * @param context
     */
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    /**
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    /**
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /**
     *
     * @param title
     * @return
     */
    public boolean deleteRecord(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("newsTable", "title =?", new String[]{title}) > 0;
    }


}
