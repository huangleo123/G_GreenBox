package com.task.dd.greenbox.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.bean.ComBean;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by dd on 2016/12/20.
 */

public class ComLVAdapter extends BaseAdapter {
    private List<ComBean> comBeanList;
    private Context context;
    private List<String> username;
    public ComLVAdapter(Context context, List<ComBean> comBeanList){
        this.comBeanList=comBeanList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return comBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return comBeanList==null?null:comBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =null;
        if (convertView!=null){
            view=convertView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_com,parent,false);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder==null){
            holder=new ViewHolder();
            holder.userImage= (com.task.dd.greenbox.tool.GradientImageView) view.findViewById(R.id.iv_com_item_user);
            holder.user= (TextView) view.findViewById(R.id.tv_com_item_user);
            holder.desc= (TextView) view.findViewById(R.id.tv_com_item_desc);
            holder.gridView= (GridView) view.findViewById(R.id.gv_com_item);
            holder.time=(TextView)view.findViewById(R.id.time);
        }
        Typeface type=Typeface.createFromAsset(context.getAssets(), "fonts/ziti.ttf");
        holder.desc.setTypeface(type);


        ComBean comBean=comBeanList.get(position);
        //取出数据但是里面只是包含了图片的集合地址 文字描述，用户名还有用户头像没有
        holder.desc.setText(comBean.getContent());
        holder.user.setText(comBean.getUsername().get(position));
        holder.time.setText(comBean.getTime());
        Glide
                .with(context)
                .load(comBean.getUserurl().get(position))
                .centerCrop()
                .into(holder.userImage);
        /////////////////////////////
        JSONArray urlArray=comBean.getUrl();
        if (urlArray.length()>9){
            //切断数组
            for (int i =4; i <urlArray.length() ; i++) {
                urlArray.remove(i);
                //为什么是4？我也不知道
            }
            ComGVAdapter comGVAdapter=new ComGVAdapter(holder.gridView,comBean.getUrl(),context);
            holder.gridView.setAdapter(comGVAdapter);
        }else{
            ComGVAdapter comGVAdapter=new ComGVAdapter(holder.gridView,comBean.getUrl(),context);
            holder.gridView.setAdapter(comGVAdapter);
        }
        return view;
    }
    private class ViewHolder {
        public com.task.dd.greenbox.tool.GradientImageView userImage;
        public TextView user,desc,time;
        public GridView gridView;
    }
}
