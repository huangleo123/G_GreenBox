package com.task.dd.greenbox.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.task.dd.greenbox.bean.KnowBean;
import com.task.dd.greenbox.jsonpull.KnowJson;
import com.task.dd.greenbox.tool.HttpUtils;

import org.json.JSONException;

/**
 * Created by dd on 2016/12/11.
 */

public class KnowAsynTask extends AsyncTask<String,Integer,KnowBean> {
    private ListView listView;
    private BaseAdapter adapter;
    private Context context;
    private String r6pua1via3h5;

    public  KnowAsynTask(ListView listView, Context context){
        this.listView=listView;
        this.context=context;


    }
    @Override
    protected KnowBean doInBackground(String... params) {
        String url=params[0];

        String jsonString= HttpUtils.getJsonContent(url);
        KnowBean knowBean=new KnowBean();
        KnowJson knowJson=new KnowJson();
        try {
            knowBean=knowJson.knowPull(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return knowBean;
    }

    @Override
    protected void onPostExecute(KnowBean knowBean) {
       //list 装入数据
       // adapter=new KnowAdapter(context,knowListBean.getListKnowBean(),);
        listView.setAdapter(adapter);

    }

}
