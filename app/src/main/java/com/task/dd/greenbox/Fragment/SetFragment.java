package com.task.dd.greenbox.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.task.dd.greenbox.Activity.Set_Inf_Activity;
import com.task.dd.greenbox.Activity.Set_Message_Activity;
import com.task.dd.greenbox.Activity.Set_Report_Activity;
import com.task.dd.greenbox.Activity.Set_Set_Activity;
import com.task.dd.greenbox.Activity.Set_pot_Activity;
import com.task.dd.greenbox.MainActivity;
import com.task.dd.greenbox.R;

/**
 * Created by dd on 2016/12/9.
 */

public class SetFragment extends Fragment {
    private LinearLayout my_pot;
    private LinearLayout inf;
    private LinearLayout message;
    private LinearLayout set;
    private LinearLayout report;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_set,container,false);
        my_pot= (LinearLayout) view.findViewById(R.id.last_flower);
        inf= (LinearLayout) view.findViewById(R.id.last_information);
        message= (LinearLayout) view.findViewById(R.id.last_talk);
        set= (LinearLayout) view.findViewById(R.id.last_setting);
        report= (LinearLayout) view.findViewById(R.id.last_advise);

        my_pot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击了我的花盆",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), Set_pot_Activity.class);
                startActivity(intent);

            }
        });
        inf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击了通知",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), Set_Inf_Activity.class);
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击了私信",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), Set_Message_Activity.class);
                startActivity(intent);
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击了系统设置",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), Set_Set_Activity.class);
                startActivity(intent);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击了意见反馈",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), Set_Report_Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
