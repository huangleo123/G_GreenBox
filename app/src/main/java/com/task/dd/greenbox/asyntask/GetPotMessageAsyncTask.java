package com.task.dd.greenbox.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.task.dd.greenbox.adapter.PotAdapter;
import com.task.dd.greenbox.bean.PotBean;
import com.task.dd.greenbox.tool.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**获取用户所有花盆的ID和名字
 * Created by dd on 2017/4/26.
 */

public class GetPotMessageAsyncTask extends AsyncTask <String ,Integer,PotBean> {

    private List<String> pot_id_list;
    private List<String> pot_name_list;
    private Context context;
    private PotAdapter potAdapter;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private static final String POT_ID="POT_ID";

    private PotBean potBean;
    private ListView listView;

    private DataFinishListener dataFinishListener;

    public void setFinishListener(DataFinishListener dataFinishListener) {
        this.dataFinishListener = dataFinishListener;
    }


    public GetPotMessageAsyncTask(Context context, ListView listView,ProgressBar progressBar,LinearLayout linearLayout){
        this.context=context;
        this.listView=listView;
        this.progressBar=progressBar;
        this.linearLayout=linearLayout;

    }
    public GetPotMessageAsyncTask(Context context, ListView listView){
        this.context=context;
        this.listView=listView;


    }


    @Override
    protected PotBean doInBackground(String... params) {
        //String ID_JsonString= HttpUtils.getJsonContent(params[0]);
        //String Name_JsonString=HttpUtils.getJsonContent(params[1]);
        String dfdf=HttpUtils.getJsonContent(params[0]);
        String ID_JsonString="{\"id_list\":[{\"Id\":\"10000\"},{\"Id\":\"10001\"}]}";
        String Name_JsonString="{\"name_list\":[{\"name\":\"草\"},{\"name\":\"花\"}]}";
  //      publishProgress(30);

        potBean=new PotBean();

        pot_name_list=new ArrayList<>();
        pot_id_list=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(ID_JsonString);
            JSONArray jsonArray =jsonObject.getJSONArray("id_list");
            JSONObject name_jsonObject = new JSONObject(Name_JsonString);
            JSONArray name_jsonArray =name_jsonObject.getJSONArray("name_list");


            for (int i = 0; i <jsonArray.length() ; i++) {
                pot_id_list.add(jsonArray.getJSONObject(i).getString("Id"));
                pot_name_list.add(name_jsonArray.getJSONObject(i).getString("name"));
            }

            potBean.setId_list(pot_id_list);
            potBean.setName_list(pot_name_list);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return potBean;
        //JSON示例
        //{"id_list":[{"Id":"10000"},{"Id":"10001"}]}
        //{"name_list":[{"name":"草"},{"name":"花"}]}
    }

    @Override
    protected void onPostExecute(PotBean potBean) {
        super.onPostExecute(potBean);
        if(potBean!=null){
            dataFinishListener.dataFinishSuccessfully(potBean);
            //progressBar.setVisibility(View.GONE);
            //linearLayout.setVisibility(View.GONE);
        }
        else {
            dataFinishListener.dataFinishFailed();
        }


    }
    public  interface DataFinishListener {
        void dataFinishSuccessfully(PotBean potBean);
        void dataFinishFailed();
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
       // progressBar.setProgress(values[0]);



    }
}





