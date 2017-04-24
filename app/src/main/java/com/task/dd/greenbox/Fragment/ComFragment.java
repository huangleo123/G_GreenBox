package com.task.dd.greenbox.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.task.dd.greenbox.R;
import com.task.dd.greenbox.adapter.ComLVAdapter;
import com.task.dd.greenbox.asyntask.ComAsynTask;

import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * Created by dd on 2016/12/9.
 */

public class ComFragment extends Fragment {
    private String url ="http://hua.nongbangzhu.cn/rest/modules/today/android/1799?rnAndroid=25";
    private String url2="http://hua.nongbangzhu.cn/rest/modules/today/android/1985?rnAndroid=25";
    private String url_1="https://api.fengqiaoju.com/v1/groups/5/topics/?page=1";//阳台养花
    private GridView gridView;
    private ListView listView;
    private ComLVAdapter comLVAdapter;

 @Nullable
 @Override
 public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_com,container,false);
     //添加视图与原本视图的操作方法是不同的
     View headview=View.inflate(getContext(),R.layout.comhead,null);
     listView= (ListView) view.findViewById(R.id.lv_com);
     gridView= (GridView) view.findViewById(R.id.gv_com_item);
     //异步获取数据然后加载数据
     ComAsynTask comAsynTask=new ComAsynTask(listView,getContext());
     //启用异步类时候加载数据是为了在onPostExecute方法为listView装入数据。
     comAsynTask.execute(url_1);//阳台养花的网址
     //至此，通过异步类实现了获取网络数据，解析，装入数据，启用listView适配器以显示数据。
     listView.addHeaderView(headview);
     return view;

 }

}
