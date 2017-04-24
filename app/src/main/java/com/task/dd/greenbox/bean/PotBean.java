package com.task.dd.greenbox.bean;

import com.task.dd.greenbox.Activity.LoginActivity;

import java.util.List;

/**
 * Created by dd on 2016/9/26.
 */
public class PotBean {
    private String temperature;
    private String humidity;
    private String sunshine;
    private String result;
    private List<String> name_List;

    public List<String> getName_List() {
        return name_List;
    }

    public void setName_List(List<String> name_List) {
        this.name_List = name_List;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSunshine() {
        return sunshine;
    }

    public void setSunshine(String sunshine) {
        this.sunshine = sunshine;
    }


}
