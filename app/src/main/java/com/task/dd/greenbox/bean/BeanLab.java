package com.task.dd.greenbox.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.task.dd.greenbox.database.BeanBaseHelper;
import com.task.dd.greenbox.database.DBSchema;

import java.util.UUID;

/**
 * Created by dd on 2017/3/20.
 */

public class BeanLab {
    private static  BeanLab sBeanLab;
    private Context mcontext;
    private SQLiteDatabase mDatabase;

    public  static  BeanLab get(Context context){
        if (sBeanLab==null){
        sBeanLab=new BeanLab(context);

        }
        return sBeanLab;
    }

    public BeanLab (Context context){
        mcontext=context.getApplicationContext();
        mDatabase=new BeanBaseHelper(mcontext).getWritableDatabase();

    }
    //创建插入数据库的方法
    public static ContentValues getContentValues(String phone,String password){
        ContentValues values=new ContentValues();
        values.put(DBSchema.Table.Cols.UUID, UUID.randomUUID().toString());
        values.put(DBSchema.Table.Cols.PHONE,phone);
        values.put(DBSchema.Table.Cols.PASSWORD,password);
        return values;
    }

    //插入数据的时候选择下面的方法就可以 l
    public void addValues(String phone,String password){
        ContentValues values=getContentValues(phone,password);
        mDatabase.insert(DBSchema.Table.NAME,null,values);
    }
    //创建返回指定数据的查询方法，cursor 还需要拆分
    public Cursor queryPhone(String tablename,String[] columns, String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(
                tablename,//表名
                columns,//列名称 在查询特定名称时。默认选择整个表， null 代表整个表
                whereClause,//条件限制，比如我要选择的是phone这一列则启用，"phone=?"
                whereArgs,//{"13800138000"}当上面的参数有？的时候，我们就要启用了
                null,
                null,
                null
        );
        return cursor;
    }
}
