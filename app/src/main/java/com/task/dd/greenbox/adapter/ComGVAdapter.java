package com.task.dd.greenbox.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.task.dd.greenbox.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**gridView的适配器
 * Created by dd on 2016/12/19.
 */

public class ComGVAdapter extends BaseAdapter {
    private JSONArray jsonArray;
    private Context context;
    private GridView gridView;//为了实现自适应的gridVew而加入的，后来加入了CustomGridView就解决了。


    public ComGVAdapter(GridView gridView,JSONArray jsonArray,Context context){
        this.jsonArray=jsonArray;
        this.context=context;
        this.gridView=gridView;
    }


    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {

            try {
                return jsonArray.get(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =null;
        if(convertView!=null){
            view=convertView;
        }else{
            view= LayoutInflater.from(context).inflate(R.layout.comgriditem,parent,false);
        }
        ViewHolder hodler = (ViewHolder) view.getTag();
        if (hodler==null){
            hodler=new ViewHolder();
            hodler.itemImage= (ImageView) view.findViewById(R.id.gv_item);

            view.setTag(hodler);
            //加入调整gridView 大小的代码


        }
        try {
            String url= (String) jsonArray.get(position);
            Glide
                    .with(context)
                    .load(url)
                    .centerCrop().centerCrop()
                    .into(hodler.itemImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
    private class ViewHolder{
        public ImageView itemImage;






    }
}
