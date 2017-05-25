package com.task.dd.greenbox.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.task.dd.greenbox.Activity.AddPotActivity;
import com.task.dd.greenbox.Activity.ControlActivity;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.adapter.PotAdapter;
import com.task.dd.greenbox.asyntask.GetPotMessageAsyncTask;
import com.task.dd.greenbox.bean.PotBean;
import com.task.dd.greenbox.tool.FastBlur;
import com.task.dd.greenbox.tool.GradientImageView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.task.dd.greenbox.MainActivity.REQUEST_CODE;

/**
 * Created by dd on 2016/12/9.
 */

public class PotFragment extends Fragment implements OnItemClickListener,PotAdapter.Callback {

    private ListView potListView;
    private List<String> name_list;
    private List<String> id_list;
    private ImageView addImageview;
    private TextView tv_tips;
    private PotBean mpotBean=new PotBean();
    private ImageView pot_head_back;//头部虚化的位置，当然以imageView来显示
    private Bitmap bitmap;//头部虚化资源文件
    private FastBlur fastBlur=new FastBlur();//头部虚化的方法文件
    private GradientImageView auto_image;
    private PotAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private static final String USER_ID="USER_ID";
    private static final String POT_ID ="POT_ID";
    private  String user_id;//用户id
    private static final  String ID_STRING="id_string";
    //尝试使用返回name_list而不是bean；思考如何实现点击修改昵称


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmemt_pot,container,false);
        potListView= (ListView) view.findViewById(R.id.lv_fragment_pot);
        //tv_tips= (TextView) view.findViewById(R.id.tv_tips);
        //progressBar= (ProgressBar) view.findViewById(R.id.progressbar_pot);
       // linearLayout= (LinearLayout) view.findViewById(R.id.ll_pot_pb_pot);

        ZXingLibrary.initDisplayOpinion(getContext());
        addHeadView();
        user_id=getActivity().getIntent().getExtras().getString(ID_STRING);

        name_list=new ArrayList<>();
        id_list=new ArrayList<>();
        id_list.add("01");
        name_list.add("暂无花盆");
        mpotBean.setName_list(name_list);

        adapter=new PotAdapter(getContext(),mpotBean,this);

        potListView.setAdapter(adapter);
        potListView.setOnItemClickListener(this);
        getPotMessage();

        addImageview= (ImageView) view.findViewById(R.id.iv_add);
        addImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击了添加按钮
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        return view;
    }

   private void getPotMessage() {

       OkHttpClient client=new OkHttpClient();
       final Request request=new Request.Builder()
               //.url("http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/login?number=111111&password=1111")
               .url("http://srms.telecomlab.cn/ZZX/lihuas/index.php/home/wx/search?id="+user_id)
               .get()
               .build();
       Call call =client.newCall(request);

       call.enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {

               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(getContext(),"网络问题",Toast.LENGTH_SHORT).show();
                   }
               });
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String string =response.body().string();
               //{"data":[{"id":"2","name":"花盆111","par_id":"27","model":"100001"},{"id":"3","name":"花盆","par_id":"27","model":"100002"}],"status":"1"}
               try {
                   JSONObject jsonObject=new JSONObject(string);
                   String result=jsonObject.getString("status");
                   name_list=new ArrayList<>();
                   id_list=new ArrayList<>();
                   if (result.equals("1")){
                       JSONArray dataJsonArray =jsonObject.getJSONArray("data");
                       for (int i = 0; i <dataJsonArray.length() ; i++) {
                           name_list.add(dataJsonArray.getJSONObject(i).getString("name"));
                           id_list.add(dataJsonArray.getJSONObject(i).getString("model"));
                       }

                       PotBean potbean=new PotBean();
                       potbean.setName_list(name_list);
                       potbean.setId_list(id_list);
                       mpotBean=potbean;
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {

                               adapter.refreshData(mpotBean);//想不到这个是在UI线程的

                           }
                       });
                   }else {

                       //没有数据，空处理
                   }

               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
       });
        /*GetPotMessageAsyncTask getPotMessageAsyncTask = new GetPotMessageAsyncTask(getContext(),potListView);
        String getIdUrl = "https://api.fengqiaoju.com/v1/articles/update/?page=1";
        getPotMessageAsyncTask.execute(getIdUrl);
        getPotMessageAsyncTask.setFinishListener(new GetPotMessageAsyncTask.DataFinishListener() {
            @Override
            public void dataFinishSuccessfully(PotBean potBean) {
                mpotBean=potBean;
                adapter.refreshData(mpotBean);

            }

            @Override
            public void dataFinishFailed() {
                Toast.makeText(getContext(),"获取花盆信息失败，检查网络",Toast.LENGTH_LONG).show();
            }
        });
*/
    }

    private void addHeadView() {
        View view =View.inflate(getContext(),R.layout.item_pot_icon,null);
        potListView.addHeaderView(view);
        /////////////////////头部虚化/////////////////////////
        pot_head_back= (ImageView) view.findViewById(R.id.pot_back);
        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.fojiateng);
        view.post(new Runnable() {
            @Override
            public void run() {
                fastBlur.blur(bitmap,pot_head_back);
            }
        });
        auto_image= (GradientImageView) view.findViewById(R.id.iv_auto);
        auto_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击自动",Toast.LENGTH_LONG).show();
            }
        });


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);

                    //result 代表的是扫描的结果 ，再次应该直接异步然后起动线程
                    //向自己搭建的服务器上传花盆的id,(用result组成一个url)。
                    //启动一个Activity,该activity启动异步，加载的时候等待，完成后跳转到
                    //先用一个天气的试一下
                    boolean isNumber = true;
                    int num=0;
                    char[] ch=result.toCharArray();
                    for (int i = 0; i <ch.length; i++) {
                        isNumber=Character.isDigit(ch[i]);
                        if (!isNumber){
                            num++;
                        }
                    }
                    if (num==0&ch.length==6){
                        Intent i=new Intent(getContext(), AddPotActivity.class);
                        i.putExtra(USER_ID,user_id);
                        i.putExtra(POT_ID,result);
                        startActivity(i);


                    }else {
                        Toast.makeText(getContext(), "不是合法的注册二维码", Toast.LENGTH_LONG).show();
                    }



                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败，检查网络", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //向服务器提交该用户的花盆，服务器返回花盆的id
        Log.i("ITEM_P", String.valueOf(position));
        Log.i("ITEM_ID", String.valueOf(id));
       if (position!=0){
           //因为增加了头部所以写了这while
           String potName=mpotBean.getName_list().get(0);

           if (potName.equals("暂无花盆")){
               Toast.makeText(getContext(),"请添加花盆",Toast.LENGTH_LONG).show();
           }else {
               String PotID=mpotBean.getId_list().get(position-1);
               Intent i= new Intent(getActivity(),ControlActivity.class);
               i.putExtra(POT_ID,PotID);
               startActivity(i);
           }

       }else {
           //点击的是listview 添加的头部
       }
        //Toast.makeText(getContext(),String.valueOf(id),Toast.LENGTH_LONG).show();
       // String PotID=mpotBean.getId_list().get(position-1);//减去1是为了去掉头部
        //Toast.makeText(getContext(),"item被点击第"+position,Toast.LENGTH_LONG).show();

      //  i.putExtra(POT_ID,PotID);
       // startActivity(i);
    }

    @Override
    public void click(View v) {
       // Toast.makeText(getContext(),"item被点击",Toast.LENGTH_LONG).show();

    }
}
