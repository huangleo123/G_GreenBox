package com.task.dd.greenbox.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.task.dd.greenbox.Activity.AddPotActivity;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.adapter.PotAdapter;
import com.task.dd.greenbox.bean.PotBean;
import com.task.dd.greenbox.tool.FastBlur;
import com.task.dd.greenbox.tool.GradientImageView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;

import static com.task.dd.greenbox.MainActivity.REQUEST_CODE;

/**
 * Created by dd on 2016/12/9.
 */

public class PotFragment extends Fragment implements OnItemClickListener,PotAdapter.Callback {
    private ListView potListView;
    private List<String> name_list;
    private ImageView addImageview;
    private PotBean potBean;
    private ImageView pot_head_back;//头部虚化的位置，当然以imageView来显示
    private Bitmap bitmap;//头部虚化资源文件
    private FastBlur fastBlur=new FastBlur();//头部虚化的方法文件
    private GradientImageView auto_image;
    private static final String POT_ID_URL="POT_ID_URL";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmemt_pot,container,false);
        potListView= (ListView) view.findViewById(R.id.lv_fragment_pot);
        ZXingLibrary.initDisplayOpinion(getContext());

        name_list=new ArrayList<>();
        name_list.add("我的小花盆");
        name_list.add("我的小花菜");

        potBean=new PotBean();
        potBean.setName_List(name_list);
        addHeadView();

        PotAdapter adapter=new PotAdapter(getContext(),potBean,this);
        potListView.setAdapter(adapter);
        potListView.setOnItemClickListener(this);
        addImageview= (ImageView) view.findViewById(R.id.iv_add);
        addImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"请点击右上角扫描添加设备",Toast.LENGTH_SHORT).show();
                //这里执行的是调用摄像头然后

                Intent intent = new Intent(getActivity(), CaptureActivity.class);

                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        return view;
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
                    Intent i=new Intent(getContext(), AddPotActivity.class);
                    i.putExtra(POT_ID_URL,result);
                    startActivity(i);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败，检查网络", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(),"item被点击第"+position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void click(View v) {
        Toast.makeText(getContext(),"item被点击",Toast.LENGTH_LONG).show();

    }
}
