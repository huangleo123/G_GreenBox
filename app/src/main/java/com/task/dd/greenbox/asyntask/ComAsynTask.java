package com.task.dd.greenbox.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.task.dd.greenbox.adapter.ComLVAdapter;
import com.task.dd.greenbox.bean.ComBean;
import com.task.dd.greenbox.jsonpull.ComJson;
import com.task.dd.greenbox.tool.HttpUtils;

import org.json.JSONException;

/**社区的异步类
 * Created by dd on 2016/12/20.
 */

public class ComAsynTask extends AsyncTask<String ,Integer,ComBean> {
    private ListView listView;
    private Context context;
    public ComAsynTask(ListView listView,Context context){
        this.listView=listView;
        this.context=context;
    }
    @Override
    protected ComBean doInBackground(String... params) {
        String jsonString=HttpUtils.getJsonContent(params[0]);
        ComJson comJson=new ComJson();
        ComBean comBean=new ComBean();
       /* try {
            comBean= comJson.ComPull(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return comBean;
    }

    @Override
    protected void onPostExecute(ComBean comBean) {
        //启用listview的适配器
        ComLVAdapter adapter=new ComLVAdapter(context,comBean.getComBeanList());
        listView.setAdapter(adapter);
        //启用mMomentAdapter=new MMomentAdapter() ;mMomentRv.setAdapter（mMomentAdapter）;启用mMomentAdapter.set（）

    }
}
