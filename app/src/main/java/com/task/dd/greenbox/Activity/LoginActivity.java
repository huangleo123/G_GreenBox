package com.task.dd.greenbox.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.task.dd.greenbox.MainActivity;
import com.task.dd.greenbox.MyApplication;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.bean.BeanLab;
import com.task.dd.greenbox.database.DBSchema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dd on 2017/3/16.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editText_phone;
    private String user_phone;
    private String user_password;
    private Button button;
    private ImageView reg_qq;
    private ImageView reg_phone;
    private ImageView reg_weibo;
    private TextView user_pact;
    public static LoginActivity instance;
    private static  final String TAG="LoginActivity";



    private static final String EXTRA_STRING="LoginActivity.extra_string";
    private static final  String ID_STRING="id_string";
    private String id;
    private BeanLab beanLab;

    private int numble=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
       beanLab=BeanLab.get(getApplicationContext());
        setContentView(R.layout.login_layout);
        reg_phone= (ImageView) findViewById(R.id.register_number);
        reg_qq= (ImageView) findViewById(R.id.register_qq);
        reg_weibo= (ImageView) findViewById(R.id.register_blog);
        user_pact= (TextView) findViewById(R.id.tv_user_pact);
        editText_phone= (EditText) findViewById(R.id.edit_phone);
        button= (Button) findViewById(R.id.button_login);
        instance=this;



        editText= (EditText) findViewById(R.id.edit_password);


        editText.setOnTouchListener(new View.OnTouchListener() {
            private static final int DRAWABLE_RIGHT = 2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText.getCompoundDrawables()[DRAWABLE_RIGHT ]== null){
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP){
                    return false;
                }
                if (event.getX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()-150)){
                    numble=numble+1;

                    if ((numble&1)!=0){
                        //不可见，闭眼，默认是1,奇数
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        Drawable no_look = ContextCompat.getDrawable(getApplication(),R.mipmap.no_look);

                        //没有下面的方法的话就点一下后全部消失
                        no_look.setBounds(0,0,no_look.getMinimumWidth(),no_look.getMinimumHeight());


                        editText.setCompoundDrawables(null,null,no_look,null);
                    }else {
                        //
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        Drawable can_look = ContextCompat.getDrawable(getApplication(),R.mipmap.can_look);

                        can_look.setBounds(0,0,can_look.getMinimumWidth(),can_look.getMinimumHeight());

                        editText.setCompoundDrawables(null,null,can_look,null);
                    }
                    return true;

                }

                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_password=editText.getText().toString().trim();
                user_phone=editText_phone.getText().toString().trim();
                //调用数据库来查询是否有对应的数据 判断是否进入主页
                OkHttpClient client=new OkHttpClient();
                final Request request= new Request.Builder()
                        .url("http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/Wx/login/?number="+user_phone+"&password="+user_password)
                        //http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/Wx/login/?number=15627594935&password=a159753
                        .get()
                        .build();
                Call call=client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"网络问题",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonString =response.body().string();

                       // Log.i(TAG,"response"+response.body().string());
                        try {
                            JSONObject jsonObject =new JSONObject(jsonString);

                            String result=jsonObject.getString("status");
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            JSONObject id_jsonObject=jsonArray.getJSONObject(0);
                            id =id_jsonObject.getString("id");

                            if (result.equals("0")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"账号密码错误",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                //将ID传入

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=newIntent(getApplicationContext(), MainActivity.class,user_phone);
                                        intent.putExtra(ID_STRING,id);
                                        startActivity(intent);
                                        LoginActivity.this.finish();

                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                           // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }


                    }
                });


                /*Cursor cursor=beanLab.queryPhone(DBSchema.Table.NAME,new String[]{"phone,password"},"phone=? and password=?",new String[]{user_phone,user_password});
                if (user_password.equals("")||user_phone.equals("")){
                    Toast.makeText(getApplication(),"用户名和密码不能为空",Toast.LENGTH_LONG).show();
                }else if(cursor.getCount()==1){//有且只有符合要求的cursor才能进入。
                    Intent intent=newIntent(getApplicationContext(), MainActivity.class,user_phone);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(getApplication(),"用户名或者密码不正确",Toast.LENGTH_LONG).show();
                }*/

            }
        });
        reg_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=newIntent(getApplicationContext(),PhoneLoginActivity.class);
                startActivity(intent);

            }
        });
        reg_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"qq登录",Toast.LENGTH_SHORT).show();

            }
        });
        reg_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"微博登录",Toast.LENGTH_SHORT).show();
            }
        });
        //用户协议点击






    }
    public static Intent newIntent(Context context,Class c){
        Intent intent=new Intent(context,c);
        return intent;
    }
    public static Intent newIntent(Context context,Class c,String extra){
        Intent intent=new Intent(context,c);
        intent.putExtra(EXTRA_STRING,extra);
        return intent;
    }
}