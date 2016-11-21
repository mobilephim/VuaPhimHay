package Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tuandeptrai on 20/11/2016.
 */

public class DatabasePhim extends SQLiteOpenHelper {

    public DatabasePhim(Context context){
super(context,"Phan loai phim ", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Querydata("CREATE TABLE PhimBo(_ID INTEGER PRIMARY KEY ,name NVARCHAR NOT NULL,type NVARCHAR NOT NULL ,thumb NVARCHAR NOT NULL,year NVARCHAR NOT NULL,decs NVARCHAR NOT NULL,tap INTEGER NOT NULL,link NVARCHAR NOT NULL,)");

    }
    public Cursor GetAll(String sql){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(sql,null);
        return c;

    }
    public void Querydata(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
