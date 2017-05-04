package com.task.dd.greenbox.jsonpull;

import com.task.dd.greenbox.bean.Pot_Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**处理花盆信息的Json解析类
 * Created by dd on 2017/5/1.
 */

public class PotMessageJson {
    private Pot_Message pot_message;
    private String temperature;//温度
    private String humidity;//环境湿度
    private String soil_humidity;//土壤湿度
    private String sun;//光照
    private String water_level;//水槽水位
    private List<String> switvh;
    public Pot_Message  messagePull(String jsonString) throws JSONException {
        JSONObject jsonObject=new JSONObject(jsonString);
        JSONObject valueObject=jsonObject.getJSONObject("value");
        temperature=valueObject.getString("environment_temperature");
        humidity=valueObject.getString("environment_humidity");
        soil_humidity=valueObject.getString("soil_humidity");
        sun=valueObject.getString("light_intensity");
        water_level=valueObject.getString("water_level");
        pot_message=new Pot_Message();

        pot_message.setTemperature(temperature);
        pot_message.setHumidity(humidity);
        pot_message.setSoil_humidity(soil_humidity);
        pot_message.setSun(sun);
        pot_message.setWater_level(water_level);

        return pot_message;
    }
    public List<String> SwitvhPull (String jsonString) throws JSONException {
        switvh=new ArrayList<>();
        JSONObject jsonObject=new JSONObject(jsonString);
        JSONObject valueJsonObject=jsonObject.getJSONObject("value");
        String lignt_string=valueJsonObject.getString("light");
        String pumb_string=valueJsonObject.getString("pumb");
        switvh.add(lignt_string);
        switvh.add(pumb_string);
        return switvh;
    }

}
