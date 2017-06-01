package com.task.dd.greenbox.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.task.dd.greenbox.R;
import com.task.dd.greenbox.bean.Pot_Message;
import com.task.dd.greenbox.bean.SingleBean;
import com.task.dd.greenbox.jsonpull.PotMessageJson;
import com.task.dd.greenbox.tool.GradientImageView;
import com.task.dd.greenbox.tool.IOSSwitchView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**花盆的控制
 * Created by dd on 2017/4/22.
 */

public class ControlActivity extends Activity{
    private ImageView imageView;//显示图片
    private ImageView backImageView;//返回的IV
    private ProgressBar pb_temperature;//温度高度条
    private ProgressBar pb_sunshine;//光照高度条
    private ProgressBar pb_humidity;//湿度高度条
    private ProgressBar pb_water;//水高度条
    private ProgressBar pb_soil_humidity;
    private GradientImageView iv_water;//水图标
    private GradientImageView iv_water_back;//水图标背景
    private GradientImageView iv_sunshine;//阳光图片
    private GradientImageView iv_sunshine_back;//
    private ImageView iv_recode;//记录图标
    private TextView tv_sun;
    private TextView tv_water;
    private TextView tv_humidity;
    private TextView tv_temperature;
    private TextView tv_soil_humidity;
    private List<String> switch_list;
    private IOSSwitchView light_switch;
    private  int i=0;
    private TextView potname;


    private Pot_Message pot_message;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "MainActivity";
    private SingleBean singlebean;
    private String lightglobal="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_control);
        imageView= (ImageView) findViewById(R.id.iv_show);
        backImageView= (ImageView) findViewById(R.id.iv_back);//返回
        pb_temperature= (ProgressBar) findViewById(R.id.progressbar_temperature);
        pb_sunshine= (ProgressBar) findViewById(R.id.progressbar_sun);
        pb_humidity= (ProgressBar) findViewById(R.id.progressbar_humidity);
        pb_water= (ProgressBar) findViewById(R.id.progressbar_water);
        pb_soil_humidity= (ProgressBar) findViewById(R.id.progressbar_soil);
        iv_water= (GradientImageView) findViewById(R.id.iv_water_button);//水按钮
        iv_water_back= (GradientImageView) findViewById(R.id.iv_water_back);//水背景
        iv_sunshine_back= (GradientImageView) findViewById(R.id.iv_sunshine_back);//光照背景
        iv_sunshine= (GradientImageView) findViewById(R.id.iv_sun_button);//光照按钮
        iv_recode= (ImageView) findViewById(R.id.iv_recode_button);
        tv_sun= (TextView) findViewById(R.id.tv_sun);
        tv_temperature= (TextView) findViewById(R.id.tv_temperature);
        tv_humidity= (TextView) findViewById(R.id.tv_humidity);
        tv_water= (TextView) findViewById(R.id.tv_water);
        tv_soil_humidity= (TextView) findViewById(R.id.tv_soil_humidity);
        potname= (TextView) findViewById(R.id.pot_head_name);
        String pot_head_name=getIntent().getExtras().getString("POT_HEAD_NAME");
        potname.setText(pot_head_name);

        //water_switch= (IOSSwitchView) findViewById(R.id.switch_water);


        //进入马上刷新数据
        getMessage();


        setListener();






    }

    private void setButton() {
        //判断light的状态
        String light=singlebean.getSwitch_list().get(0);
        if (light.equals("0")){
            iv_sunshine_back.setImageDrawable(getResources().getDrawable(R.drawable.button_light_bg_normal));

        } else {
            iv_sunshine_back.setImageDrawable(getResources().getDrawable(R.drawable.button_light_bg_focused));
        }
    }

    private void SetSwitch(final String light, final String pump) throws IOException {

        final String set_light=singlebean.getSwitch_list().get(0);
        Log.i(TAG, "content: " +"开头set_light"+set_light );
        String jsonSting = "{\"key\":\"inbox\",\"value\":{\"light\":\"" + light + "\",\"pumb\":\"" + pump + "\"}}";

        Log.i("content",jsonSting);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestbody = RequestBody.create(JSON, jsonSting);
        Request request = new Request.Builder()
                .url("http://api.yeelink.net/v1.0/device/354593/sensor/400901/datapoints")
                .post(requestbody)
                .addHeader("u-apikey", "0ad358217706ef3af6cbe7833a1835ba")
                .addHeader("cache-control", "no-cache")
                .build();
       // Response response = client.newCall(request).execute();
        Call call= client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: " + e);
                ControlActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"开关失效，请检查网络",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: " + response.code());
                int code= response.code();
                if(code==406){
                    ControlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //状态的改变
                            if (set_light.equals("0")){
                                iv_sunshine_back.setImageDrawable(getResources().getDrawable(R.drawable.button_light_bg_normal));
                                singlebean.getSwitch_list().set(0,"0");
                                Log.i(TAG, "content: " +"访问失败，重置0" );
                            }else {
                                iv_sunshine_back.setImageDrawable(getResources().getDrawable(R.drawable.button_light_bg_focused));
                                singlebean.getSwitch_list().set(0,"1");
                                Log.i(TAG, "content: " +"访问失败，重置1" );
                            }
                            Toast.makeText(getApplicationContext(),"点击太快，访问失败", Toast.LENGTH_LONG).show();
                        }
                    });
                }else if (code==200){
                    ControlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.i(TAG, "content: " +"访问成功" );

                        }
                    });

                }


            }
        });
    }



    private void setListener() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//点击注销
            }
        });
        iv_water.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String light=singlebean.getSwitch_list().get(0);
                iv_water_back.setImageDrawable(getResources().getDrawable(R.drawable.button_bg_focused));
                TimerTask task=new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                iv_water_back.setImageDrawable(getResources().getDrawable(R.drawable.button_bg_normal));

                            }
                        });

                    }

                };
                Timer timer = new Timer();
                timer.schedule(task,5000);
                try {
                    SetSwitch(light,"1");
                    Log.i(TAG, "content: " +"执行setSwitch后修改了"+light  + "1" );
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        iv_sunshine.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String light=singlebean.getSwitch_list().get(0);
                Log.i(TAG, "content: " +"点击时候的light"+"  "+light);
                if (light.equals("0")){
                    iv_sunshine_back.setImageDrawable(getResources().getDrawable(R.drawable.button_light_bg_focused));
                    try {
                        SetSwitch("1","0");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    singlebean.getSwitch_list().set(0,"1");
                    Log.i(TAG, "content: " +"执行setSwitch后修改了"+"1  0" );


                } else{
                    iv_sunshine_back.setImageDrawable(getResources().getDrawable(R.drawable.button_light_bg_normal));
                    try {
                        SetSwitch("0","0");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    singlebean.getSwitch_list().set(0,"0");
                    Log.i(TAG, "content: " +"执行setSwitch后修改了"+"0  0" );


                }
                }

        });
       /* water_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onStateSwitched(boolean isOn) throws IOException {
                if(isOn){
                    //水开启的状态下
                    water_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
                        @Override
                        public void onStateSwitched(boolean isOn) throws IOException {
                            if (isOn){
                                Toast.makeText(getApplicationContext(),"水控制开启的状态开",Toast.LENGTH_LONG).show();
                                String light=singlebean.getSwitch_list().get(0);
                                Toast.makeText(getApplicationContext(),"灯是"+light,Toast.LENGTH_LONG).show();
                                singlebean.getSwitch_list().set(1,"1");
                                SetSwitch(light,"1");

                            }else{
                                //开的状态关
                                String light=singlebean.getSwitch_list().get(0);
                                SetSwitch(light,"0");
                                singlebean.getSwitch_list().set(1,"0");
                                Toast.makeText(getApplicationContext(),"灯是"+light,Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"水控制开的状态关",Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }else{

                    //关闭状态下
                    water_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
                        @Override
                        public void onStateSwitched(boolean isOn) throws IOException {
                            if (isOn){
                                Toast.makeText(getApplicationContext(),"水控制关闭下执行打开",Toast.LENGTH_LONG).show();
                                //关闭下执行打开
                                String light=singlebean.getSwitch_list().get(0);
                                SetSwitch(light,"1");
                                singlebean.getSwitch_list().set(1,"1");
                                Toast.makeText(getApplicationContext(),"灯是"+light,Toast.LENGTH_LONG).show();
                            }else{
                                //关闭下执行关闭
                                Toast.makeText(getApplicationContext(),"水控制关闭下执行关闭",Toast.LENGTH_LONG).show();
                                String light=singlebean.getSwitch_list().get(0);
                                SetSwitch(light,"0");
                                singlebean.getSwitch_list().set(1,"0");
                                Toast.makeText(getApplicationContext(),"灯是"+light,Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

            }
        });*/
       /* light_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onStateSwitched(boolean isOn) throws IOException {
                if (isOn){
                    //开
                    light_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
                        @Override
                        public void onStateSwitched(boolean isOn) throws IOException {
                            if (isOn){
                                //开的状态开
                                String water =singlebean.getSwitch_list().get(1);
                                SetSwitch("1","0");
                                singlebean.getSwitch_list().set(0,"1");
                                Toast.makeText(getApplicationContext(),"灯控制开的状态开",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"水是"+water,Toast.LENGTH_LONG).show();

                            }else {
                                //开的状态关
                                String water =singlebean.getSwitch_list().get(1);
                                SetSwitch("0","0");
                                singlebean.getSwitch_list().set(0,"0");
                                Toast.makeText(getApplicationContext(),"灯控制开的状态关",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"水是"+water,Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }else {
                    //关
                    light_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
                        @Override
                        public void onStateSwitched(boolean isOn) throws IOException {
                            if (isOn){
                                //关闭下打开
                                String water=singlebean.getSwitch_list().get(1);
                                SetSwitch("1","0");
                                singlebean.getSwitch_list().set(0,"1");
                                Toast.makeText(getApplicationContext(),"灯控制关闭下打开",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"水是"+water,Toast.LENGTH_LONG).show();
                            }else {
                                //关闭下关闭
                                String water=singlebean.getSwitch_list().get(1);
                                SetSwitch("0","0");
                                singlebean.getSwitch_list().set(0,"0");
                                Toast.makeText(getApplicationContext(),"灯控制关闭下关闭",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"水是"+water,Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });*/

    }

    private void getMessage() {
        //用okHttp框架,以下都采用异步的方式
        //获取花盆传感器的信息
        getPotStatus();
        //获取开关状态信息
        getSwitchStatus();
        getPhoto();
       // setButton();


    }

    private void getPotStatus() {
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                //<device_id>是354593Fget
                //<sensor_id>是400693
                .url("http://api.yeelink.net/v1.0/device/354593/sensor/400693/datapoints")
                .get()
                .addHeader("u-apikey", "0ad358217706ef3af6cbe7833a1835ba")
                .addHeader("cache-control", "no-cache")
                .build();
        Call call= client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ControlActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"获取花盆传感器失败，检查网络",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Response response = client.newCall(request).execute();
                String jsonString =  response.body().string();
                PotMessageJson potJson=new PotMessageJson();
                try {
                    pot_message=potJson.messagePull(jsonString);


                    //下面通过这个方法让子线程在子线程更新UI
                    ControlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getSwitchStatus() {
        OkHttpClient button=new OkHttpClient();
        Request button_request = new Request.Builder()
                .url("http://api.yeelink.net/v1.0/device/354593/sensor/400901/datapoints")
                .get()
                .addHeader("u-apikey", "0ad358217706ef3af6cbe7833a1835ba")
                .addHeader("cache-control", "no-cache")
                .build();
        Call button_call=button.newCall(button_request);
        button_call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ControlActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"获取按钮失败，检查网络",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString =  response.body().string();
                PotMessageJson potJson=new PotMessageJson();
                try {
                    //根据返回的数据更改按钮的属性。
                    switch_list=potJson.SwitvhPull(jsonString);
                     singlebean= SingleBean.get(switch_list);
                    ControlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String light=switch_list.get(0);
                            String water=switch_list.get(1);
                            setButton();

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }



        });
    }
    public void getPhoto() {
        //不知道为什么使用Glide无法加载图片，该url下。
        /*Glide.with(getApplicationContext())
                .load("http://api.yeelink.net/v1.0/device/354593/sensor/400698/photo/content")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                       // Log.d(TAG, "onException: " + e.toString()+"  model:"+model+" isFirstResource: "+isFirstResource);
                        Toast.makeText(getApplication(),"加载失败",Toast.LENGTH_LONG).show();
                        imageView.setImageResource(R.mipmap.d);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                       // Log.e(TAG, "isFromMemoryCache:"+isFromMemoryCache+"  model:"+model+" isFirstResource: "+isFirstResource);

                        return false;

                    }
                })

                .centerCrop()
                .into(imageView);*/

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.yeelink.net/v1.0/device/354593/sensor/400698/photo/content")
                //http://api.yeelink.net/v1.0/device/354593/sensor/400698/photo/content
                .get()
                .addHeader("u-apikey", "0ad358217706ef3af6cbe7833a1835ba")
                .addHeader("cache-control", "no-cache")
                .build();
        Call call= client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //网络图片请求成功，更新到主线程的ImageView

                        Toast.makeText(getApplication(),"加载失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //网络图片请求成功，更新到主线程的ImageView
                        imageView.setImageBitmap(bmp);
                    }
                });



            }
        });





        }


    private void showMessage() {
        String text=pot_message.getWater_level();

        tv_water.setText(text);
        String df=pot_message.getHumidity();
        tv_humidity.setText(df);
        tv_sun.setText(pot_message.getSun());
        tv_temperature.setText(pot_message.getTemperature());
        tv_soil_humidity.setText(pot_message.getSoil_humidity());
        //注意：数据支持整数，如果是小数的话将会报错
        pb_soil_humidity.setProgress(Integer.valueOf(pot_message.getSoil_humidity()));
        pb_humidity.setProgress(Integer.valueOf(pot_message.getHumidity()));
        pb_water.setProgress(Integer.valueOf(pot_message.getWater_level()));
        pb_sunshine.setProgress(Integer.valueOf(pot_message.getSun()));
        pb_temperature.setProgress(Integer.valueOf(pot_message.getTemperature()));

    }






}
