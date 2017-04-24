package com.task.dd.greenbox.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.task.dd.greenbox.Activity.WebActivity;
import com.task.dd.greenbox.MainActivity;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.adapter.KnowAdapter;
import com.task.dd.greenbox.adapter.KnowVPAdapter;

import android.widget.AdapterView.OnItemClickListener;

import com.task.dd.greenbox.bean.KnowBean;
import com.task.dd.greenbox.jsonpull.NewKnowJson;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dd on 2016/12/9.
 */

public class KnowFragment extends Fragment implements OnItemClickListener,KnowAdapter.Callback {
    private ListView knowlistView;
    private List<View> listView;
    private ArrayList<View> dots;
    private ViewPager viewPager;
    private View view1, view2, view3,view4;
    private int oldPosition = 0;// 记录上一次点的位置
    private static int  currentItem; // 当前页面
    private static final  String EXTRA_WEB="com.task.dd.greenbox.Fragment.KnowFragment";

    private String url="https://api.fengqiaoju.com/v1/articles/update/?page=1";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View view = inflater.inflate(R.layout.fragment_know,container,false);
        knowlistView= (ListView) view.findViewById(R.id.lv_fragment_know);


        try {
            KnowBean knowBean=new KnowBean();
            NewKnowJson knowJson=new NewKnowJson();
            knowBean=knowJson.knowPull(getData());
            KnowAdapter adapter= new KnowAdapter(getContext(),knowBean.getListKnowBean(),this);
            addIconView();
            addTodayView();
            knowlistView.setAdapter(adapter);
            knowlistView.setOnItemClickListener(this);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //KnowAsynTask knowAsynTask=new KnowAsynTask(knowlistView,getContext());
        //knowAsynTask.execute(url);   //s数据不采用；联网获取，采用本地数据




        return view;
    }

    private void addIconView() {
        View iconView =View.inflate(getContext(),R.layout.item_know_icon,null);
        viewPager= (ViewPager) iconView.findViewById(R.id.know_viewpager);
        listView=new ArrayList<>();
        LayoutInflater lf =LayoutInflater.from(getContext());
        view1 = lf.inflate(R.layout.know_vp1, null);
        view2 = lf.inflate(R.layout.know_vp2, null);
        view3 = lf.inflate(R.layout.know_vp3, null);
        view4 = lf.inflate(R.layout.know_vp4,null);
        listView.add(view1);
        listView.add(view2);
        listView.add(view3);
        listView.add(view4);
        dots = new ArrayList<>();
        dots.add(iconView.findViewById(R.id.view_dot_1));
        dots.add(iconView.findViewById(R.id.view_dot_2));
        dots.add(iconView.findViewById(R.id.view_dot_3));
        dots.add(iconView.findViewById(R.id.view_dot_4));
        dots.get(0).setBackgroundResource(R.drawable.dot_focused);

        KnowVPAdapter knowVPAdapter=new KnowVPAdapter(listView);
        viewPager.setAdapter(knowVPAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dots.get(oldPosition).setBackgroundResource(
                        R.drawable.dot_normal);
                dots.get(position)
                        .setBackgroundResource(R.drawable.dot_focused);

                oldPosition = position;
                currentItem = position;
                switch (currentItem)
                {
                    case 0:
                        listView.get(0).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(),"第一页",Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case 1:
                        listView.get(1).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(),"第二页",Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case 2:
                        listView.get(2).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(),"第3页",Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case 3:
                        listView.get(3).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(),"第4页",Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        knowlistView.addHeaderView(iconView);

    }

    private void addTodayView() {
        View todayView=View.inflate(getContext(),R.layout.item_know_today,null);
        knowlistView.addHeaderView(todayView);

    }
    public String getData() throws UnsupportedEncodingException {
        InputStream in = getResources().openRawResource(R.raw.api);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] array = new byte[1024];
        int len = 0;
        try {
            while ((len = in.read(array))!= -1){
                bos.write(array,0,len);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        array= bos.toByteArray();
        String jsonString = new String(array,"utf-8");
       return  jsonString ;

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            KnowBean knowBean=new KnowBean();
            NewKnowJson knowJson=new NewKnowJson();
            knowBean=knowJson.knowPull(getData());
            Toast.makeText(getContext(), "的item被点击了！，点击的位置是-->" + position+knowBean.getListKnowBean().get(position-2).getId(), Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getActivity(), WebActivity.class);
            String url=knowBean.getListKnowBean().get(position-2).getUrl();
            i.putExtra(EXTRA_WEB,url);
            startActivity(i);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void click(View v) throws UnsupportedEncodingException, JSONException {
        KnowBean knowBean=new KnowBean();
        NewKnowJson knowJson=new NewKnowJson();
        knowBean=knowJson.knowPull(getData());
        Intent i=new Intent(getActivity(), WebActivity.class);
        String url=knowBean.getListKnowBean().get((Integer) v.getTag()).getUrl();
        i.putExtra(EXTRA_WEB,url);
        startActivity(i);
        Toast.makeText(getContext(), "listview的内部的按钮被点击了！，位置是-->" +  knowBean.getListKnowBean().get((Integer) v.getTag()).getId()  , Toast.LENGTH_SHORT).show();

    }
}
