package com.task.dd.greenbox.bean;

import java.util.List;

import static android.R.id.list;

/**单例
 * Created by dd on 2017/5/6.
 */

public class SingleBean {
    private List<String> switch_list;
    private static  SingleBean sSingleBean;


    public static SingleBean get(List<String> switch_list ){
        if (sSingleBean==null){
            sSingleBean=new SingleBean(switch_list);
        }
        return sSingleBean;
    }
    private SingleBean(List<String> switch_list){
        this.switch_list=switch_list;
    }

    public List<String> getSwitch_list() {
        return switch_list;
    }
}
