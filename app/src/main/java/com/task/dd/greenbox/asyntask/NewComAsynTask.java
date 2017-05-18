package com.task.dd.greenbox.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.task.dd.greenbox.Activity.Moment;
import com.task.dd.greenbox.Fragment.TestFragment;
import com.task.dd.greenbox.adapter.ComLVAdapter;

import com.task.dd.greenbox.adapter.MomentAdapter;
import com.task.dd.greenbox.bean.ComBean;
import com.task.dd.greenbox.jsonpull.ComJson;
import com.task.dd.greenbox.tool.HttpUtils;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

import org.json.JSONException;

/**仙剑
 * Created by dd on 2017/3/4.
 */

public class NewComAsynTask extends AsyncTask<String ,Integer,Moment> {
    private RecyclerView recyclerView;
    private BGANinePhotoLayout.Delegate delegate;
    private Context context;
    private MomentAdapter mMomentAdapter;
    public NewComAsynTask(RecyclerView recyclerView, Context context, BGANinePhotoLayout.Delegate delegate){
        this.recyclerView=recyclerView;
        this.context=context;
        this.delegate=delegate;
    }
    @Override
    protected Moment doInBackground(String... params) {
        String jsonString= HttpUtils.getJsonContent(params[0]);
        ComJson comJson=new ComJson();
        ComBean comBean=new ComBean();
        Moment moment=new Moment();
        try {
            moment= comJson.ComPull(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moment;
    }
    @Override
    protected void onPostExecute(Moment moment) {
        MomentAdapter mMomentAdapter=new MomentAdapter(recyclerView,delegate);
        recyclerView.setAdapter(mMomentAdapter);
        mMomentAdapter.setData(moment.getMoments());//Moment集合
        //启用mMomentAdapter=new MMomentAdapter() ;mMomentRv.setAdapter（mMomentAdapter）;启用mMomentAdapter.set（）

    }
}
