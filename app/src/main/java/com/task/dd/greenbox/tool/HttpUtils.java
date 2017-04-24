package com.task.dd.greenbox.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**网路协议
 * Created by dd on 2016/5/10.
 * 1.参数为网络地址
 */
public class HttpUtils {
    
    public static String getJsonContent(String path){
        try{
            URL url =new URL(path);
            HttpURLConnection connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            int code =connection.getResponseCode();
            if(code==200){
                return changeInputStream(connection.getInputStream());
            }
        }catch(Exception e){

        }
        return "";
    }

    private static String changeInputStream(InputStream inputStream) {
        String jsonString="";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len=0;
        byte[] date =new byte[1024];
        try {
            while((len=inputStream.read(date))!=-1){
                outputStream.write(date,0,len);
            }
            jsonString=new String (outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
