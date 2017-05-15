package com.task.dd.greenbox.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.task.dd.greenbox.MainActivity;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.asyntask.AddPotAsynTask;
import com.task.dd.greenbox.bean.BeanLab;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



/**点击添加花盆的时候加上的activity
 * 需要添加的功能是点击添加图片
 * Created by dd on 2017/4/15.
 */

public class AddPotActivity extends Activity {
    private TextView textView;
    private EditText editText;//植物昵称
    private Button button;//确定按钮
    private ImageView imageView;//头像
    private ImageView imageView_back;//返回
    private String path;
    private String user_id;
    private String pot_id;
    private String  pot_name;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private static final String POT_ID="POT_ID";
    private static final String USER_ID="USER_ID";
    private static final  String ID_STRING="id_string";
    private String loginUrl="http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/login?number=111111&password=1111";//登录成功就返回用户ID
    private String selPot="http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/search?id=27";//通过用户的ID返回花盆名字
    private String addPot ="http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/sell?name=昵称&id=用户&花盆id";
    private String nameUrl="http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/sell?&id=花盆id";

    private  String url ="http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/sell?id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pot);
        pot_id=getIntent().getExtras().getString(POT_ID);
        user_id=getIntent().getExtras().getString(USER_ID);

       // linearLayout= (LinearLayout) findViewById(layout_bar);
        //Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
       // progressBar= (ProgressBar) findViewById(R.id.progress);


        editText= (EditText) findViewById(R.id.ed_pot_name);
        button= (Button) findViewById(R.id.button_add_pot);
        imageView= (ImageView) findViewById(R.id.iv_pot_image);
       /* pot_name=editText.getText().toString().trim();
        path="http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/sell?name="+pot_name+"&model="+pot_id+"&id="+user_id;
        AddPotAsynTask addPotAsynTask=new AddPotAsynTask(getApplicationContext(),progressBar,linearLayout);
        addPotAsynTask.execute(path);*/
        imageView_back= (ImageView) findViewById(R.id.iv_add_pot_back);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击确定后上传
                //获得url后调用http类上传，
                //写一个判断 ，返回有数据的话就 结束这个activity
                pot_name=editText.getText().toString().trim();
                path="http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/sell?name="+pot_name+"&model="+pot_id+"&id="+user_id;
                OkHttpClient client=new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(path)
                        .get()
                        .build();
                Call call =client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"注册失败,检查网络",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        //到底返回了什么？
                        String jsonString =response.body().string();
                        if (jsonString.equals("")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"花盆已经被添加，请检查二维码",Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{

                            try {
                                JSONObject jsonObject =new JSONObject(jsonString);
                                String result=jsonObject.getString("status");
                                if (result.equals("1")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),"添加花盆成功",Toast.LENGTH_LONG).show();
                                            //Intent intent=newIntent(getApplicationContext(), MainActivity.class,user_phone);
                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                            intent.putExtra(ID_STRING,user_id);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }




                    }
                });




            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"功能正在完善",Toast.LENGTH_LONG).show();

            }
        });






        //点击二维码后联网实现了绑定，然后跳转到这里，在这里向服务器提供用户的花盆的昵称。
        //1.实现bean（昵称）上传
        //2.实现点击更换图片，图片的上传。


    }
}
