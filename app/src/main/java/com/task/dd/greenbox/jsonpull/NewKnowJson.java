package com.task.dd.greenbox.jsonpull;

import android.util.Log;

import com.task.dd.greenbox.bean.KnowBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dd on 2017/3/9.
 */

public class NewKnowJson {
    private KnowBean knowBean;
    private KnowBean knowBean1;
    private List<KnowBean> knowList;

    public KnowBean knowPull(String jsonString) throws JSONException {

        knowBean = new KnowBean();
        knowList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);//应该通过调用文件传入
        JSONObject dataObject= jsonObject.getJSONObject("data");
        JSONArray listjsonArray = dataObject.getJSONArray("list");
        for (int i = 0; i < listjsonArray.length(); i++) {
            knowBean1 = new KnowBean();
            knowBean1.setCreatetime(listjsonArray.getJSONObject(i).getString("addtime"));
            knowBean1.setViewnum(listjsonArray.getJSONObject(i).getString("daodu"));
            knowBean1.setId(listjsonArray.getJSONObject(i).getString("id"));
            knowBean1.setTitle(listjsonArray.getJSONObject(i).getString("title"));
            knowBean1.setThumb(listjsonArray.getJSONObject(i).getString("picname"));
            knowBean1.setUrl(listjsonArray.getJSONObject(i).getString("url_path"));
            knowList.add(knowBean1);
        }
        knowBean.setListKnowBean(knowList);

        return knowBean;
    }
}
