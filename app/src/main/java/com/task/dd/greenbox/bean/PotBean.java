package com.task.dd.greenbox.bean;

import com.task.dd.greenbox.Activity.LoginActivity;

import java.util.List;

/**花盆个数信息
 * Created by dd on 2016/9/26.
 */
public class PotBean {
    private List<String> id_list;
    private List<String> name_list;

    public List<String> getName_list() {
        return name_list;
    }

    public void setName_list(List<String> name_list) {
        this.name_list = name_list;
    }

    public List<String> getId_list() {

        return id_list;
    }

    public void setId_list(List<String> id_list) {
        this.id_list = id_list;
    }
}
