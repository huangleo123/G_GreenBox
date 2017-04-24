package com.task.dd.greenbox.bean;

import java.util.List;

/**花盆的listView 的bean
 * 现在的数据都是自己加上去的，以后要联网获取用户的数据，然后更新一下
 * Created by dd on 2017/4/14.
 */

public class Pot_User_Bean {

    private List<String> list_name;//一个用户的花盆昵称集合

    public List<String> getList_name() {
        return list_name;
    }

    public void setList_name(List<String> list_name) {
        this.list_name = list_name;
    }
}
