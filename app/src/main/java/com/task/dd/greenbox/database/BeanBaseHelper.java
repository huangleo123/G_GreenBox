package com.task.dd.greenbox.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.task.dd.greenbox.database.DBSchema.Table;

/**创建数据库，按照流程是要很麻烦的，好在现在有SQLiteOpenHelper类
 * Created by dd on 2017/3/20.
 */

public class BeanBaseHelper extends SQLiteOpenHelper {
    private static final  int VERSION=1;
    private static final  String DATABASE_NAME="beanBase.db";
    public BeanBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        Table.Cols.UUID + ", " +
                        Table.Cols.PHONE + ", " +
                        Table.Cols.PASSWORD + ", " +
                        Table.Cols.USERNAME + ", " +
                        Table.Cols.SIGNATURE +
                        ")"
       /* db.execSQL("create table" + Table.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Table.Cols.UUID +", "+
                Table.Cols.PHONE+", "+
                Table.Cols.PASSWORD+", "+
                Table.Cols.USERNAME+", "+
                Table.Cols.SIGNATURE+", "+
                ")"*/
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
