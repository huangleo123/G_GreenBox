package com.task.dd.greenbox.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.task.dd.greenbox.bean.BeanLab;
import com.task.dd.greenbox.tool.HttpConnection;
import com.task.dd.greenbox.tool.HttpUtils;

import java.io.IOException;

/**添加花盆的异步类
 * Created by dd on 2017/4/16.
 */

public class AddPotAsynTask extends AsyncTask<String ,Integer,String> {
    private Context context;
    private ProgressBar progressBar;
    private String code;
    private String result;
    private LinearLayout linearLayout;
    public AddPotAsynTask(Context context,ProgressBar progressBar,LinearLayout linearLayout){
        this.context=context;
        this.progressBar=progressBar;
        this.linearLayout=linearLayout;

    }


    //准备运行
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setProgress(0);
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress(0);//发布进度为零
        String path =params[0];
        result=HttpUtils.getJsonContent(path);
        publishProgress(30);// 进度值30
        publishProgress(100);// 进度值100
        return result;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(context,"该地址无效",Toast.LENGTH_LONG).show();
            //注销这个进程，

        }else {
            Toast.makeText(context,"成功获取ID",Toast.LENGTH_LONG).show();
            //跳转到其他进程
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);



    }
}
