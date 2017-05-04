package com.task.dd.greenbox.bean;

/**花盆状态的bean
 * Created by dd on 2017/5/1.
 */

public class Pot_Message {
    private String temperature;//温度
    private String humidity;//环境湿度
    private String soil_humidity;//土壤湿度
    private String sun;//光照
    private String water_level;//水槽水位
    private String light;
    private String water;

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSoil_humidity() {
        return soil_humidity;
    }

    public void setSoil_humidity(String soil_humidity) {
        this.soil_humidity = soil_humidity;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWater_level() {
        return water_level;
    }

    public void setWater_level(String water_level) {
        this.water_level = water_level;
    }
}
