package com.task.dd.greenbox.jsonpull;

import com.task.dd.greenbox.Activity.Moment;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.bean.ComBean;
import com.task.dd.greenbox.bean.ComUser;
import com.task.dd.greenbox.bean.KnowBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dd on 2016/12/20.
 */

public class ComJson{
    private ComBean comBean;
    private Moment moment;
    private List<ComBean> comBeanList;
    private List<Moment> moments;
    private ArrayList<String> photos;
    private List<String> username;
    public Moment ComPull(String jsonString) throws JSONException{
        comBean=new ComBean();
        moment=new Moment();
        comBeanList=new ArrayList<>();
        photos=new ArrayList<>();
        moments=new ArrayList<>();
        String[] userString = {"小花小草", "紫罗兰", "小蛋蛋是我", "砖石玫瑰", "阳光明媚", "小橙子", "云中漫步", "hhl199570827", "呆妞小宝", "吃货宝宝", "幽谷佳人", "非诚勿扰", "陈立彬傻逼", "我是喜洋洋", "三笠紫外", "媛媛归来", "幽谷佳人", "乱世传说", "风中的花香", "中国好孤雁", "芝兰", "背背"};
        int[] userId={R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.v,R.mipmap.e,R.mipmap.f,R.mipmap.g,R.mipmap.h,R.mipmap.i,R.mipmap.j,R.mipmap.k,R.mipmap.l,R.mipmap.m,R.mipmap.n,R.mipmap.o,R.mipmap.p,R.mipmap.q,R.mipmap.r,R.mipmap.s,R.mipmap.t,R.mipmap.u,};
        JSONObject jsonObject=new JSONObject(jsonString);
        JSONObject datajsonObject=jsonObject.getJSONObject("data");
        JSONArray listjsonArray=datajsonObject.getJSONArray("list");
        for (int i = 0; i <listjsonArray.length() ; i++) {
            comBean=new ComBean();
            moment=new Moment();
            comBean.setContent(listjsonArray.getJSONObject(i).getString("content"));
            moment.setContent(listjsonArray.getJSONObject(i).getString("content"));

            moment.setTime(listjsonArray.getJSONObject(i).getString("datetime"));
            comBean.setDatetime(listjsonArray.getJSONObject(i).getString("datetime"));

            comBean.setId(listjsonArray.getJSONObject(i).getString("id"));

            comBean.setUrl(listjsonArray.getJSONObject(i).getJSONArray("imgs"));

            comBean.setUid(listjsonArray.getJSONObject(i).getString("uid"));
            comBean.setTime(listjsonArray.getJSONObject(i).getString("datetime"));
            JSONArray jsonArray= listjsonArray.getJSONObject(i).getJSONArray("imgs");
            //给个循环

             moment.setResid(userId[i]);


            moment.setUsername(userString[i]);
            //将jsonArray转成ArrList
            for (int j = 0; j <jsonArray.length() ; j++) {
                photos=new ArrayList<>();
                for (int k=0;k<jsonArray.length()&k<9;k++){
                    String string=jsonArray.getString(k);
                    photos.add(string);
                }
                moment.setPhotos(photos);
                /*if (j==jsonArray.length()){
                    photos=new ArrayList<>();
                }*/

            }


            comBeanList.add(comBean);
            moments.add(moment);
        }
        comBean.setComBeanList(comBeanList);
        moment.setMoments(moments);//借助这个方法将moments传递出去。

        return moment;
    }

}
