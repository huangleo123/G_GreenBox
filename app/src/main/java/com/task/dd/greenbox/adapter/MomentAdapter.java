package com.task.dd.greenbox.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.task.dd.greenbox.Activity.Moment;
import com.task.dd.greenbox.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * Created by dd on 2017/3/5.
 */

public  class MomentAdapter extends BGARecyclerViewAdapter<Moment> {
    private BGANinePhotoLayout.Delegate mDelegate;
    public MomentAdapter(RecyclerView recyclerView, BGANinePhotoLayout.Delegate mDelegate) {
        super(recyclerView, R.layout.item_moment);
        this.mDelegate=mDelegate;
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Moment moment) {
        //适配器中的文字是String类型的，而PhotoS是ArrayList类型的URl
        //如果有文字就显示视图，如果没有文字就隐藏视图
        if (TextUtils.isEmpty(moment.content)) {
            helper.setVisibility(R.id.tv_item_moment_content, View.GONE);
        } else {
            helper.setVisibility(R.id.tv_item_moment_content, View.VISIBLE);
            helper.setText(R.id.tv_item_moment_content, moment.content);
        }
        //设置九宫格
        //String[] userString = {"小花小草", "紫罗兰", "小蛋蛋是我", "砖石玫瑰", "阳光明媚", "小橙子", "云中漫步", "hhl199570827", "呆妞小宝", "吃货宝宝", "幽谷佳人", "非诚勿扰", "陈立彬傻逼", "我是喜洋洋", "三笠紫外", "媛媛归来", "幽谷佳人", "乱世传说", "风中的花香", "中国好孤雁", "芝兰", "背背"};

        //因为这个轮子修改了BaseAdapterHelper 的方法，本来有一部加载的方法现在没有了。看了源码后现在尝试直接使用源码

//头像的问题怎么解决呢？helper没直接设置图片的方法
        helper.setText(R.id.tv_item_moment_username,moment.username);
        helper.setText(R.id.tv_item_moment_time,moment.time);
        helper.setImageResource(R.id.iv_item_moment_avatar,moment.Resid);
        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.npl_item_moment_photos);
        ninePhotoLayout.setDelegate(mDelegate);
        //photos 那里是把所有的图片都下载了 ，要改一下模型
        ninePhotoLayout.setData(moment.getPhotos());
        //

    }
}
