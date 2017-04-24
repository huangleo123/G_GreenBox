package com.task.dd.greenbox.bean;

import java.util.List;

/**
 * Created by dd on 2016/12/11.
 */

public class KnowBean {
    private String id;
    private String createtime;
    private String title;
    private String viewnum;
    private String content;
    private String thumb;//图片地址
    private String url;

    private List<KnowBean> listKnowBean;

    public List<KnowBean> getListKnowBean() {
        return listKnowBean;
    }

    public void setListKnowBean(List<KnowBean> listKnowBean) {
        this.listKnowBean = listKnowBean;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewnum() {
        return viewnum;
    }

    public void setViewnum(String viewnum) {
        this.viewnum = viewnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
