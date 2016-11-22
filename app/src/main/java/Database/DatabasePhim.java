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
        Querydata("CREATE TABLE IF NOT EXISTS [Phimbo] ([ID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,[type] NVARCHAR(30)  NOT NULL,[name] NVARCHAR(100)  NOT NULL,[thumb] VARCHAR(200)  NOT NULL,[year] VARCHAR(12)  NOT NULL,[decs] VARCHAR(200)  NOT NULL,[tap] INTEGER  NOT NULL,[link] VARCHAR(200)  NOT NULL,[linkphu] VARCHAR(200)  NOT NULL)");

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
