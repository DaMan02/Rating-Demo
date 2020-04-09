package com.innovvscript.stamurairating.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String TAG = "DB";
    String TABLE_NAME="m_table";
    String KEY_RATING = "m_rating";
    String KEY_TIME = "m_time";
    String KEY_ID = "id";

    public DbHandler(Context context) {
        super(context, "m_database", null , 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_RATING + " TEXT," +  KEY_TIME + " TEXT);";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addHistoryItem(HistoryItem item){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RATING,item.getRating());
        values.put(KEY_TIME,item.getTime());
        db.insert(TABLE_NAME,null,values);
    }

    public List<HistoryItem> getAllHistoryItems(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<HistoryItem> itemList = new ArrayList<>();
        Cursor cursor=db.query(TABLE_NAME,new String[]{ KEY_ID,KEY_RATING, KEY_TIME }
                ,null,null,null,null,KEY_ID );

        if(cursor.moveToFirst()){
            do{
                HistoryItem item = new HistoryItem();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                item.setRating(cursor.getString(cursor.getColumnIndex(KEY_RATING)));
                item.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                itemList.add(item);
            }while ((cursor.moveToNext()));
        }
        cursor.close();
        return  itemList;
    }
}
