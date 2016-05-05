package com.example.a13051_000.buffetmealsystem;

import android.test.mock.MockApplication;
import android.util.Log;
import android.util.StringBuilderPrinter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by shubin on 2016/4/29.
 */
public class HttpUtils {
    //function:发送Post请求到服务器
    //
    private static StringBuffer getRequestData(Map<String,String> params,String encode){
        //请求数据的处理；
        StringBuffer stringBuffer = new StringBuffer();
        try{
            for (Map.Entry<String,String> entry: params.entrySet()
                 ) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue()))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
catch (Exception e){
    e.printStackTrace();
}
        return stringBuffer;
    }
    public static String dealResponseResult(InputStream inputStream){
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String line ;
        try {
            while((line = reader.readLine()) != null){
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
    public static String submitPostData(String strUrlPath, Map<String,String> params,String encode){
        byte[] data = getRequestData(params,encode).toString().getBytes();
        try{
            URL url = new URL(strUrlPath);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(3000);
            httpUrlConnection.setDoInput(true);//打开输入流
            httpUrlConnection.setDoOutput(true);//打开输出流
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpUrlConnection.setRequestProperty("Content-Length",String.valueOf(data.length));
            httpUrlConnection.disconnect();
            OutputStream outputStream = httpUrlConnection.getOutputStream();
            outputStream.write(data);
            int response= httpUrlConnection.getResponseCode();
            if(response == 200) {
                InputStream inputStream = httpUrlConnection.getInputStream();
                return dealResponseResult(inputStream);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }
}
