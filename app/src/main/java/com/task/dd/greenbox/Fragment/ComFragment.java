package com.task.dd.greenbox.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.task.dd.greenbox.R;
import com.task.dd.greenbox.adapter.ComLVAdapter;
import com.task.dd.greenbox.adapter.MomentAdapter;
import com.task.dd.greenbox.asyntask.ComAsynTask;

import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dd on 2016/12/9.
 */

public class ComFragment extends Fragment implements EasyPermissions.PermissionCallbacks, BGANinePhotoLayout.Delegate{
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;

    private static final int REQUEST_CODE_ADD_MOMENT = 1;

    private RecyclerView mMomentRv;
    private MomentAdapter mMomentAdapter;
    private String url="https://api.fengqiaoju.com/v1/groups/5/topics/?page=1";//阳台养花



 @Nullable
 @Override
 public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.activity_moment_list,container,false);

     return view;

 }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
