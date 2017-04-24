package com.task.dd.greenbox.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by dd on 2016/12/18.
 */

public class KnowVPAdapter extends PagerAdapter{
    private List<View> viewList;
    public  KnowVPAdapter(List<View> viewList){
        this.viewList=viewList;
    }
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        ((ViewPager) view).removeView(viewList.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        ((ViewPager) view).addView(viewList.get(position));
        return viewList.get(position);
    }
}
