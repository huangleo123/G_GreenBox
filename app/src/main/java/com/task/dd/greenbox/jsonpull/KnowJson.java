package com.task.dd.greenbox.jsonpull;

import com.task.dd.greenbox.bean.KnowBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dd on 2016/12/11.
 */

public class KnowJson {
    private KnowBean knowBean;
    private KnowBean knowBean1;
    private List<KnowBean> knowList;
    public KnowBean knowPull(String jsonString) throws JSONException {

        knowBean =new KnowBean();
        knowList=new ArrayList<>();
        JSONObject jsonObject=new JSONObject(jsonString);
        JSONObject datajsonObject=jsonObject.getJSONObject("data");
        JSONArray listjsonArray=datajsonObject.getJSONArray("list");
        for (int i = 0; i <listjsonArray.length();i++) {
            knowBean1=new KnowBean();
            knowBean1.setId(listjsonArray.getJSONObject(i).getString("id"));
            knowBean1.setThumb(listjsonArray.getJSONObject(i).getString("thumb"));
            knowBean1.setCreatetime(listjsonArray.getJSONObject(i).getString("createtime"));
            knowBean1.setTitle(listjsonArray.getJSONObject(i).getString("title"));
            knowBean1.setViewnum(listjsonArray.getJSONObject(i).getString("viewnum"));
            knowBean1.setContent(listjsonArray.getJSONObject(i).getString("content"));
            knowBean1.setUrl(listjsonArray.getJSONObject(i).getString("url_path"));
            knowList.add(knowBean1);
        }
        knowBean.setListKnowBean(knowList);
        return knowBean;
    }

}
