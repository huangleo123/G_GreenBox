package com.task.dd.greenbox.tool;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**get方式的访问网络的封装
 * Created by dd on 2017/4/16.
 */

public class HttpConnection {
    public static  String Httpget(String path) throws IOException {
        URL url =new URL(path);
        HttpURLConnection connection =(HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setConnectTimeout(5000);
        int code =connection.getResponseCode();
        if(code==200){
            return "200";
        }
       return null;
    }
}
