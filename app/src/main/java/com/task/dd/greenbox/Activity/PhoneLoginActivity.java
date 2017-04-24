package com.task.dd.greenbox.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.mob.commons.SMSSDK;
import com.task.dd.greenbox.MainActivity;
import com.task.dd.greenbox.adapter.KnowAdapter;
import com.task.dd.greenbox.bean.BeanLab;
import com.task.dd.greenbox.database.DBSchema;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by dd on 2017/3/15.
 */

public class PhoneLoginActivity extends AppCompatActivity {
    private static final String USER_PHONE="PhoneLoginActivity.user_phone";
    private static  boolean back=false;
    private BeanLab beanLab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cn.smssdk.SMSSDK.initSDK(this,"1c1a35d0b7304","9a3d033680dc6d0e4c0788c7831797fa");
        beanLab=BeanLab.get(getApplicationContext());
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if(result == cn.smssdk.SMSSDK.RESULT_COMPLETE){
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    //应为用了第三方验证码登录 ，没有办法在获得手机号码之前进行是数据库的查询。
                    Cursor cursor= beanLab.queryPhone(DBSchema.Table.NAME,new String[]{"phone"},"phone=?",new String[]{phone});
                    if (cursor.getCount()==1){
                        Toast.makeText(getApplicationContext(),"已有账号，直接登录",Toast.LENGTH_SHORT).show();

                        Intent i=newIntent(PhoneLoginActivity.this,MainActivity.class,phone);
                        startActivity(i);
                        finish();
                    }else{
                        Intent i=newIntent(PhoneLoginActivity.this,PassWordActivity.class,phone);
                        startActivity(i);
                        finish();
                    }

                }
            }
        });
        registerPage.show(this);
        finish();



    }
    public static Intent newIntent(Context context,Class c ,String phone){
        Intent i= new Intent(context,c);
        i.putExtra(USER_PHONE,phone);
        return i;
    }



}

