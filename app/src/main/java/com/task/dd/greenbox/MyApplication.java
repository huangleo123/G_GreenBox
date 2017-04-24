package com.task.dd.greenbox;

import android.app.Activity;
import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aaron on 16/9/7.
 */

public class MyApplication  extends Application {
    private List activitys = null;
    private static MyApplication instance;



    private MyApplication() { activitys = new LinkedList(); }
    public static MyApplication getInstance() { if (null == instance) { instance = new MyApplication(); } return instance;  }
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if(!activitys.contains(activity)){
                activitys.add(activity);
            }
        }else{
            activitys.add(activity);
        }
    }


}
