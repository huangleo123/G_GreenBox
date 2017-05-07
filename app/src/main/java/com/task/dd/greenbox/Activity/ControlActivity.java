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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.bean.Pot_Message;
import com.task.dd.greenbox.bean.SingleBean;
import com.task.dd.greenbox.jsonpull.PotMessageJson;
import com.task.dd.greenbox.tool.GradientImageView;
import com.task.dd.greenbox.tool.IOSSwitchView;

import org.json.JSONException;

import java.io.IOException;
import java.util.Iterator;
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
    private GradientImageView iv_sunshine;//阳光图片
    private ImageView iv_recode;//记录图标
    private TextView tv_sun;
    private TextView tv_water;
    private TextView tv_humidity;
    private TextView tv_temperature;
    private TextView tv_soil_humidity;
    private List<String> switch_list;
    private IOSSwitchView light_switch;
    private IOSSwitchView water_switch;

    private Pot_Message pot_message;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "MainActivity";
    private SingleBean singlebean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_control);
        imageView= (ImageView) findViewById(R.id.iv_show);
        backImageView= (ImageView) findViewById(R.id.iv_back);
        pb_temperature= (ProgressBar) findViewById(R.id.progressbar_temperature);
        pb_sunshine= (ProgressBar) findViewById(R.id.progressbar_sun);
        pb_humidity= (ProgressBar) findViewById(R.id.progressbar_humidity);
        pb_water= (ProgressBar) findViewById(R.id.progressbar_water);
        pb_soil_humidity= (ProgressBar) findViewById(R.id.progressbar_soil);
        iv_water= (GradientImageView) findViewById(R.id.iv_water_button);
        iv_sunshine= (GradientImageView) findViewById(R.id.iv_sun_button);
        iv_recode= (ImageView) findViewById(R.id.iv_recode_button);
        tv_sun= (TextView) findViewById(R.id.tv_sun);
        tv_temperature= (TextView) findViewById(R.id.tv_temperature);
        tv_humidity= (TextView) findViewById(R.id.tv_humidity);
        tv_water= (TextView) findViewById(R.id.tv_water);
        tv_soil_humidity= (TextView) findViewById(R.id.tv_soil_humidity);
        light_switch= (IOSSwitchView) findViewById(R.id.switch_light);
        water_switch= (IOSSwitchView) findViewById(R.id.switch_water);


        //进入马上刷新数据
        getMessage();

        setListener();



    }

    private void SetSwitch(final String light, final String pump) throws IOException {

        String jsonSting = "{\"key\":\"inbox\",\"value\":{\"lightset\":\"" + light + "\",\"soil_set\":\"" + pump + "\"}}";
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
                Log.i(TAG, "onResponse: " + response.body().string());
                ControlActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"开关成功设置为light"+light+"水"+pump,Toast.LENGTH_LONG).show();
                    }
                });

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
                //点击改变颜色
            }
        });
        water_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
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
        });
        light_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onStateSwitched(boolean isOn) throws IOException {
                if (isOn){
                    //开
                    water_switch.setOnSwitchStateChangeListener(new IOSSwitchView.OnSwitchStateChangeListener() {
                        @Override
                        public void onStateSwitched(boolean isOn) throws IOException {
                            if (isOn){
                                //开的状态开
                                String water =singlebean.getSwitch_list().get(1);
                                SetSwitch("1",water);
                                singlebean.getSwitch_list().set(0,"1");
                                Toast.makeText(getApplicationContext(),"灯控制开的状态开",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"水是"+water,Toast.LENGTH_LONG).show();

                            }else {
                                //开的状态关
                                String water =singlebean.getSwitch_list().get(1);
                                SetSwitch("0",water);
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
                                SetSwitch("1",water);
                                singlebean.getSwitch_list().set(0,"1");
                                Toast.makeText(getApplicationContext(),"灯控制关闭下打开",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"水是"+water,Toast.LENGTH_LONG).show();
                            }else {
                                //关闭下关闭
                                String water=singlebean.getSwitch_list().get(1);
                                SetSwitch("0",water);
                                singlebean.getSwitch_list().set(0,"0");
                                Toast.makeText(getApplicationContext(),"灯控制关闭下关闭",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"水是"+water,Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

    }

    private void getMessage() {
        //用okHttp框架,以下都采用异步的方式
        //获取花盆传感器的信息
        getPotStatus();
        //获取开关状态信息
        getSwitchStatus();
        getPhoto();


    }

    private void getPotStatus() {
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                //<device_id>是354593
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
                            Toast.makeText(getApplicationContext(),"获取到light是"+light+"获取到water是"+water,Toast.LENGTH_LONG).show();
                            if (light.equals("1")){
                                try {
                                    light_switch.setOn(true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                try {
                                    light_switch.setOn(false);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (water.equals("1")){
                                try {
                                    water_switch.setOn(true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                try {
                                    water_switch.setOn(false);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }


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
        Glide.with(getApplicationContext())
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
                .placeholder(R.mipmap.m)
                .centerCrop()
                .into(imageView);

       /* OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.huabaike.com/uploads/allimg/sltimg/201703/bp_58c6624832cd5.jpg")
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

                        Toast.makeText(getApplication(),"加载成功",Toast.LENGTH_LONG).show();
                    }
                });



            }
        });*/





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
