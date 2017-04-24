package com.task.dd.greenbox.bean;

import android.text.Layout;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dd on 2016/12/20.
 */

public class ComBean {
    private String content;
    private String datetime;
    private String id;
    private JSONArray url;
    private  List<String> userurl;
    private List<ComBean> comBeanList;

    private String uid;
    private List<String> username;
    private String time;

    public List<String> getUserurl() {
        //21张图片！！卧槽
        String[] url={"http://app-img.fengqiaoju.com/user/53/279153/avatar.jpeg!avatar?1472949770","http://app-img.fengqiaoju.com/user/91/277491/avatar.jpeg!avatar?1461295271","http://app-img.fengqiaoju.com/user/14/278414/avatar.jpeg!avatar?1465571031","http://app-img.fengqiaoju.com/user/5/1905/avatar.jpeg!avatar?1439263953","http://app-img.fengqiaoju.com/user/74/278874/avatar.jpeg!avatar?1469237713",
        "http://app-img.fengqiaoju.com/user/34/1434/avatar.jpeg!avatar?1436886539","http://app-img.fengqiaoju.com/user/5/1905/avatar.jpeg!avatar?1439263953","http://app-img.fengqiaoju.com/uploads/user/1/1/20161030071052_58152c7ceb34c.jpeg!thumb","http://app-img.fengqiaoju.com/user/14/278414/group/66927233ab048f943338f96cecaeba80.jpeg!thumb","http://app-img.fengqiaoju.com/user/14/278414/group/3d6d2a2f5bc22bed26b5306f79bfb6c4.jpeg!thumb"
        ,"http://app-img.fengqiaoju.com/user/4/278904/avatar.jpeg!avatar?1469709194","http://app-img.fengqiaoju.com/user/74/278874/avatar.jpeg!avatar?1469237713","http://app-img.fengqiaoju.com/user/91/277491/avatar.jpeg!avatar?1461295271","http://app-img.fengqiaoju.com/user/71/278571/group/9cf8e423cd07064f2ff0379ca561eb84.jpeg!thumb"
        ,"http://app-img.fengqiaoju.com/user/4/278904/group/91d364c8757a25dcb13bd74694d2e469.jpeg!thumb","http://app-img.fengqiaoju.com/user/74/278874/group/490fa4b68602ae6ef2dd647211357d58.jpeg!thumb","http://app-img.fengqiaoju.com/uploads/user/1/1/group/20161104145356_581c308462e8d.jpeg!thumb",
                "http://app-img.fengqiaoju.com/user/14/278414/group/66927233ab048f943338f96cecaeba80.jpeg!thumb","http://app-img.fengqiaoju.com/uploads/user/1/1/20161219191823_5857c1ff203c0.jpeg!thumb","http://app-img.fengqiaoju.com/uploads/user/1/1/group/20170123055146_5885297240bfc.jpeg!thumb","http://app-img.fengqiaoju.com/uploads/user/1/1/group/20170126150756_5889a04c3b052.jpeg!thumb","http://app-img.fengqiaoju.com/uploads/user/1/1/blog/20170224172013_58affacddae46.jpeg!thumb"};
        userurl=new ArrayList<>();
        for (int i = 0; i <url.length ; i++) {
            userurl.add(url[i]);
        }
        String[] b=new String[]{""};
        return userurl;
    }

    public void setUserurl(List<String> userurl) {
        this.userurl = userurl;
    }


    public JSONArray getUrl() {
        return url;
    }

    public void setUrl(JSONArray url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getUsername() {
        String[] userString = {"小花小草", "紫罗兰", "小蛋蛋是我", "砖石玫瑰", "阳光明媚", "小橙子", "云中漫步", "hhl199570827", "呆妞小宝", "吃货宝宝", "幽谷佳人", "非诚勿扰", "陈立彬傻逼", "我是喜洋洋", "三笠紫外", "媛媛归来", "幽谷佳人", "乱世传说", "风中的花香", "中国好孤雁", "芝兰", "背背"};
        username = new ArrayList<>();
        for (int i = 0; i < userString.length; i++) {
            username.add(userString[i]);
        }
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getUrllist() {
        return urllist;
    }

    public void setUrllist(List<String> urllist) {
        this.urllist = urllist;
    }

    private List<String> urllist;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<ComBean> getComBeanList() {
        return comBeanList;
    }

    public void setComBeanList(List<ComBean> comBeanList) {
        this.comBeanList = comBeanList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
