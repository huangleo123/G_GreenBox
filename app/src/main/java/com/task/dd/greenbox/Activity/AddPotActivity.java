package com.task.dd.greenbox.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.task.dd.greenbox.R;
import com.task.dd.greenbox.asyntask.AddPotAsynTask;
import com.task.dd.greenbox.bean.BeanLab;

import static com.task.dd.greenbox.R.id.content_tv;
import static com.task.dd.greenbox.R.id.layout_bar;

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
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private static final String POT_ID_URL="POT_ID_URL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pot);
        path=getIntent().getExtras().getString(POT_ID_URL);
        linearLayout= (LinearLayout) findViewById(layout_bar);
        //Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
        progressBar= (ProgressBar) findViewById(R.id.progress);
        //启动异步
        AddPotAsynTask addPotAsynTask=new AddPotAsynTask(getApplicationContext(),progressBar,linearLayout);
        addPotAsynTask.execute(path);
        editText= (EditText) findViewById(R.id.ed_pot_name);
        button= (Button) findViewById(R.id.button_add_pot);
        imageView= (ImageView) findViewById(R.id.iv_pot_image);
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
                Toast.makeText(getApplicationContext(),"点击确定",Toast.LENGTH_LONG).show();

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
