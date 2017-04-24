package com.task.dd.greenbox.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.bean.KnowBean;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**适配器，使用接口回调
 * Created by dd on 2016/12/13.
 */

public class KnowAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<KnowBean> knowListBean;
    private static final String TAG = "KnowAdapter";
    private Callback mCallback;


    public interface  Callback{
        public void click(View v) throws JSONException, UnsupportedEncodingException;
    }


    public KnowAdapter(Context context, List<KnowBean> knowListBean ,Callback callback){
        this.context=context;
        this.knowListBean=knowListBean;
        mCallback=callback;
    }

    @Override
    public int getCount() {
        return knowListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return knowListBean==null?null:knowListBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView");
        View view=null;
        if(convertView!=null){
            view =convertView;
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.item_know,parent,false);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder==null){
            holder=new ViewHolder();
            holder.itemImage= (ImageView) view.findViewById(R.id.iv_know_list);
            holder.itemTitle= (TextView) view.findViewById(R.id.id_know_title);
            holder.itemTime= (TextView) view.findViewById(R.id.id_know_time);
            holder.itemViewNum= (TextView) view.findViewById(R.id.tv_know_view_nm);

        }
        //取出数据
        KnowBean knowBean= knowListBean.get(position);
        //装载数据
        Glide
                .with(context)
                .load(knowBean.getThumb())
                .centerCrop()
                .into(holder.itemImage);

        holder.itemTime.setText(knowBean.getCreatetime());
        holder.itemTitle.setText(knowBean.getTitle());
        holder.itemViewNum.setText(knowBean.getId());
        holder.itemTitle.setOnClickListener(this);
        holder.itemTitle.setTag(position);
        return view;
    }

    @Override
    public void onClick(View v) {
        try {
            mCallback.click(v);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    private class ViewHolder {
        public ImageView itemImage;
        public TextView itemTitle,itemTime,itemViewNum;
    }




}
