package com.task.dd.greenbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.mob.commons.SMSSDK;
import com.task.dd.greenbox.Activity.ComActivity;
import com.task.dd.greenbox.Activity.LoginActivity;
import com.task.dd.greenbox.Fragment.ComFragment;
import com.task.dd.greenbox.Fragment.KnowFragment;
import com.task.dd.greenbox.Fragment.PotFragment;
import com.task.dd.greenbox.Fragment.SetFragment;
import com.task.dd.greenbox.Fragment.TestFragment;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //这个借口不能点击吧 显示图片和文字 两个就行 用本地吧
    public static final int REQUEST_CODE = 111;
    private String todayUrl="http://rest.aihuhua.com/?sign=ACF437D6CA2E8CC9D2B1818C22570F12&platform=android&_timestamp=1481204480&v=1.0&appid=1001&method=app.home.xingyun&uuid=7621A2C3769708DD1412CCF2C02363A6";
    //可以滑动的8张图片组合在一起  用本地吧
    private String photoUrl="http://rest.aihuhua.com?sign=CF34E5033C4FC4A6144FB49B7651A99E&platform=android&_timestamp=1481721271&v=1.0&appid=1001&method=app.flower.category&uuid=7621A2C3769708DD1412CCF2C02363A6";
    //                       http://rest.aihuhua.com/?sign=C813F4DD54A01317A963673EB6068D74&platform=android&_timestamp=1481721271&v=1.0&appid=1001&method=app.flower.category&uuid=7621A2C3769708DD1412CCF2C02363A6
    private String dateUrl="http://rest.aihuhua.com?sign=492A84BCD677ED7178B611085AF0300E&platform=android&v=1.0&method=app.weiba.list&p=1&uuid=7621A2C3769708DD1412CCF2C02363A6&_timestamp=1481205074&appid=1001&pagesize=30";
    //好多文章并且可以点击的
    private String listUrl="https://api.fengqiaoju.com/v1/baikes/update/?page=1";
    private  String listURL1="https://api.fengqiaoju.com/v1/articles/update/?page=1";
    //文章列表
    private String textUrl="https://api.fengqiaoju.com/v1/articles/update/?page=1";
    private String sousuo="https://api.fengqiaoju.com/v1/baikes/?keyword=水仙&page=1";
    private String sousuoItem="https://api.fengqiaoju.com/v1/baikes/626/";
    //搜索的时候如果没有结果 可以通过返回的数据中的total_num 值来判断。
    //而监听的话是可以写成 加id
    private RadioButton potButton;
    private RadioButton comButton;
    private RadioButton setButton;
    private RadioButton knowButton;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView searchImageView;
    private ImageView scanImageView;
    private ImageView addImageview;
    private String id;
    private String userPhone;
    private static final String EXTRA_STRING="LoginActivity.extra_string";
    private static final  String ID_STRING="id_string";

    private String qqid="1105957331";
    private String qqsre ="fThBurHZAHxCw9rE";

    private String weiboid="1877273976";
    private String weibosre="6529f381004a8b50923a60cff79b8067";

    private String weixingID="wxfd1fec16748ab60d";
    private String weixingsre="55262d0f41821898176a89aabf08934d";

    private Fragment potFragment,comFragment,setFragment,knowFragment,testFragment;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cn.smssdk.SMSSDK.initSDK(this,"","");
        ZXingLibrary.initDisplayOpinion(this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//在主题中已经申明不要标题了 可是还是会显示 ，只能再次申明了
        setContentView(R.layout.activity_main);
        id=getIntent().getExtras().getString(ID_STRING);
        userPhone=getIntent().getExtras().getString(EXTRA_STRING);

        potButton= (RadioButton) findViewById(R.id.pot_button);
        comButton= (RadioButton) findViewById(R.id.com_button);
        setButton= (RadioButton) findViewById(R.id.set_button);
        knowButton= (RadioButton) findViewById(R.id.know_button);
        searchImageView= (ImageView) findViewById(R.id.iv_search);
        scanImageView= (ImageView) findViewById(R.id.iv_scan);
        scanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        potButton.setOnClickListener(this);
        comButton.setOnClickListener(this);
        setButton.setOnClickListener(this);
        knowButton.setOnClickListener(this);
        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
       // toolbar.inflateMenu(R.menu.toolbar_menu);  不需要目录所以不启用了  但是目录已经写好了 启用就可以了
        toolbar.setNavigationIcon(R.mipmap.ic_greedbox80xr);
        toolbar.setTitle("GreenBox");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        //尝试销毁登录activity
        LoginActivity.instance.finish();





    }
    //设置监听器，case1-4处理监听事件
    @Override
    public void onClick(View v){
        restartButton();
        switch (v.getId()){
            case R.id.pot_button:
                showCurrentFragment(1);
                break;
            case R.id.com_button:
                showCurrentFragment(2);
                break;
            case R.id.know_button:
                showCurrentFragment(3);
                break;
            case R.id.set_button:
                showCurrentFragment(4);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //result 代表的是扫描的结果 ，再次应该直接异步然后起动线程
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败，检查网络", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void showCurrentFragment(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i){
            case 1:
                Drawable pot_click= ContextCompat.getDrawable(getApplication(),R.mipmap.icon_pot_ed);

                //findviewbyid
                potButton.setCompoundDrawablesWithIntrinsicBounds(null,pot_click,null,null);
                potButton.setTextColor(0xff17b729);
                if(potFragment==null){
                    potFragment=new PotFragment();
                    fragmentTransaction.add(R.id.frame_layout,potFragment);
                }
                else {
                    fragmentTransaction.show(potFragment);
                }
                break;
            case 2:
                Drawable com_click =ContextCompat.getDrawable(getApplication(),R.mipmap.icon_com_ed);
                comButton.setCompoundDrawablesWithIntrinsicBounds(null,com_click,null,null);
                comButton.setTextColor(0xff17b729);
                //此处将不启用fragment，而是启用Activity。

                if(testFragment==null){
                    testFragment=new TestFragment();
                    fragmentTransaction.add(R.id.frame_layout,testFragment);
                }
                else {
                    fragmentTransaction.show(testFragment);
                }


                break;
            case 3:
                Drawable know_click =ContextCompat.getDrawable(getApplication(),R.mipmap.icon_know_ed);
                knowButton.setCompoundDrawablesWithIntrinsicBounds(null,know_click,null,null);
                knowButton.setTextColor(0xff17b729);
                if(knowFragment==null){
                    knowFragment=new KnowFragment();
                    fragmentTransaction.add(R.id.frame_layout,knowFragment);
                }
                else {
                    fragmentTransaction.show(knowFragment);
                }
                break;
            case 4:
                Drawable set_click =ContextCompat.getDrawable(getApplication(),R.mipmap.icon_set_ed);
                setButton.setCompoundDrawablesWithIntrinsicBounds(null,set_click,null,null);
                setButton.setTextColor(0xff17b729);
                if(setFragment==null){
                    setFragment=new SetFragment();
                    fragmentTransaction.add(R.id.frame_layout,setFragment);
                }
                else {
                    fragmentTransaction.show(setFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }
    private void restartButton() {
        Drawable pot = ContextCompat.getDrawable(getApplication(),R.mipmap.icon_pot);
        Drawable com = ContextCompat.getDrawable(getApplication(),R.mipmap.icon_com);
        Drawable know = ContextCompat.getDrawable(getApplication(),R.mipmap.icon_know);
        Drawable set = ContextCompat.getDrawable(getApplication(), R.mipmap.icon_set);
        potButton.setCompoundDrawablesWithIntrinsicBounds(null, pot, null, null);
        comButton.setCompoundDrawablesWithIntrinsicBounds(null, com, null, null);
        knowButton.setCompoundDrawablesWithIntrinsicBounds(null, know, null, null);
        setButton.setCompoundDrawablesWithIntrinsicBounds(null, set, null, null);
        potButton.setTextColor(0xff1d1d1d);
        comButton.setTextColor(0xff1d1d1d);
        knowButton.setTextColor(0xff1d1d1d);
        setButton.setTextColor(0xff1d1d1d);

    }
    private void hideFragments(FragmentTransaction transaction) {
        if (potFragment != null) {
            transaction.hide(potFragment);
        }
        if (testFragment != null) {
            transaction.hide(testFragment);
        }
        if (knowFragment !=null){
            transaction.hide(knowFragment);
        }
        if (setFragment != null) {
            transaction.hide(setFragment);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }



}
