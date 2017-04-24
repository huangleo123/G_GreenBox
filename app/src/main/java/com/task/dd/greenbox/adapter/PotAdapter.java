package com.task.dd.greenbox.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.task.dd.greenbox.R;
import com.task.dd.greenbox.bean.PotBean;
import com.task.dd.greenbox.bean.Pot_User_Bean;

/**展示花盆列表的listView
 * Created by dd on 2016/12/10.
 */

public class PotAdapter extends BaseAdapter {


    private Context context;
    private PotBean potBean;
    private static final String TAG = "PotAdapter";
    public PotAdapter (Context context,PotBean potBean) {
        this.context=context;
        this.potBean=potBean;
    }





    @Override
    public int getCount() {
        return potBean.getName_List().size();
    }

    @Override
    public Object getItem(int position) {
        return potBean.getName_List()==null?null:potBean.getName_List().get(position);
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
            view=convertView;
        }else {
            view= LayoutInflater.from(context).inflate(R.layout.item_pot,parent,false);

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder==null){
            holder =new ViewHolder();
            holder.potname= (TextView) view.findViewById(R.id.tv_my);
        }
      holder.potname.setText(potBean.getName_List().get(position));
        return view;
    }
    private class ViewHolder {

        public TextView potname;
    }
}
