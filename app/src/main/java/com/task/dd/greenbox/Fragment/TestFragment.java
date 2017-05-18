package com.task.dd.greenbox.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.task.dd.greenbox.Activity.ComActivity;
import com.task.dd.greenbox.Activity.MomentAddActivity;
import com.task.dd.greenbox.R;
import com.task.dd.greenbox.Activity.Moment;
import com.task.dd.greenbox.adapter.MomentAdapter;
import com.task.dd.greenbox.asyntask.NewComAsynTask;
import com.task.dd.greenbox.bean.ComBean;
import com.task.dd.greenbox.jsonpull.ComJson;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dd on 2017/3/1.
 */

public class TestFragment extends Fragment implements EasyPermissions.PermissionCallbacks, BGANinePhotoLayout.Delegate {
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
    private static final int REQUEST_CODE_ADD_MOMENT = 1;
    private static final int RESULT_OK = -1;
    private String url_1="https://api.fengqiaoju.com/v1/groups/5/topics/?page=1";

    private RecyclerView mMomentRv;
    private MomentAdapter mMomentAdapter;
    private CheckBox mDownLoadableCb;
    private Moment moment;
    private BGANinePhotoLayout.Delegate mDelegate;
    private BGANinePhotoLayout mCurrentClickNpl;
    private TextView button_add;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_moment_list,container,false);//listView 视图
       // View headview=View.inflate(getContext(),R.layout.comhead,null);
        //RecyclerViewHeader header = RecyclerViewHeader.fromXml(getContext(), R.id.header);

        mDelegate=TestFragment.this;
       // mDownLoadableCb = (CheckBox) view.findViewById(R.id.cb_moment_list_downloadable);
        mMomentRv = (RecyclerView) view.findViewById(R.id.rv_moment_list_moments);
        mMomentAdapter = new MomentAdapter(mMomentRv,mDelegate);

        button_add= (TextView) view.findViewById(R.id.add_friend);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), MomentAddActivity.class), REQUEST_CODE_ADD_MOMENT);
            }
        });

        mMomentRv.addOnScrollListener(new BGARVOnScrollListener(getActivity()));
        mMomentRv.setLayoutManager(new LinearLayoutManager(getActivity()));//柔和了xml 上的设置
      /*  RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.header);
        header.attachTo(mMomentRv,true);*/
        OkHttpClient client =new OkHttpClient();
        Request request=new Request.Builder()
                .url(url_1)
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
                String string=response.body().string();
                ComJson comJson=new ComJson();
                ComBean comBean=new ComBean();
                try {
                    moment= comJson.ComPull(string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mMomentRv.setAdapter(mMomentAdapter);
                        mMomentAdapter.setData(moment.getMoments());//Moment集合
                    }
                });

            }
        });

       /* NewComAsynTask newComAsynTask =new NewComAsynTask(mMomentRv,getContext(),mDelegate);
        newComAsynTask.execute(url_1);*/
        //启动适配器，调入数据
       // addNetImageTestData();

        return view ;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_MOMENT) {
            //执行更新数据 而不是添加头部
            String content=MomentAddActivity.getMoment(data).getContent();
            String username=MomentAddActivity.getMoment(data).getUsername();
            ArrayList<String> photos= MomentAddActivity.getMoment(data).getPhotos();
            //成功拿到数据
           /* moment.setContent(content);
            moment.setUsername(username);
            moment.setTime("12:00");
            moment.setPhotos(photos);
            mMomentAdapter.setData(moment.getMoments());*/
            MomentAddActivity.getMoment(data).setResid(R.mipmap.a);
            MomentAddActivity.getMoment(data).setTime("12:00");
            mMomentAdapter.addFirstItem(MomentAddActivity.getMoment(data));
            mMomentRv.smoothScrollToPosition(0);
        }
    }


    /**
     * 图片预览，兼容6.0动态权限
     */
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PREVIEW)
    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }

        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(getContext(), downloadDir, mCurrentClickNpl.getCurrentClickItem()));
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(getContext(), downloadDir, mCurrentClickNpl.getData(), mCurrentClickNpl.getCurrentClickItemPosition()));
            }
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PREVIEW) {
            Toast.makeText(getContext(), "您拒绝了「图片预览」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }



}
