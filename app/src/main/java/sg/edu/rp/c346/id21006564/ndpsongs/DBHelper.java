package sg.edu.rp.c346.id21006564.ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simplesongs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGER ="singers";
    private static final String COLUMN_YEAR ="year";
    private static final String COLUMN_STAR ="stars";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + COLUMN_TITLE +COLUMN_SINGER +"TEXT," + COLUMN_YEAR + COLUMN_STAR+  " INTEGER ) ";
        db.execSQL(createSongTableSql);
        Log.i("info","created tables");

        //Dummy records
        for (int i =0;i<4;i++){
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID,"id " + i);
            values.put(COLUMN_TITLE,"title" +i);
            values.put(COLUMN_SINGER,"singers" +i);
            values.put(COLUMN_YEAR,"year" +i);
            values.put(COLUMN_STAR,"star" +i);
            db.insert(TABLE_SONG,null,values);
        }
        Log.i("info","dummy records inserted");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }
    public long insertSong(String title,String singers,int year, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(COLUMN_ID,id);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGER,singers);
        values.put(COLUMN_YEAR,year);
        values.put(COLUMN_STAR,stars);
        long result = db.insert(TABLE_SONG, null, values);
       // db.close();
        if (result ==-1){
            Log.d("DBHelper", "Insert failed");
        }
       // Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns= {COLUMN_ID, COLUMN_TITLE,COLUMN_SINGER,COLUMN_YEAR,COLUMN_STAR};
        Cursor cursor = db.query(TABLE_SONG, columns, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers= cursor.getString(2);
                int year=cursor.getInt(3);
                int stars=cursor.getInt(4);
                Song song = new Song(title,singers,year,stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }




}
