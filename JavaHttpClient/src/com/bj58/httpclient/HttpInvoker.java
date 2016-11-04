package com.bj58.httpclient;

//import com.bj58.jwdf.framework.util.JsonHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpInvoker {

//    public static final String GET_URL = "http://www.sina.com.cn";
    public static final String GET_URL = "http://10.252.24.180/api/list/ershoufang?&action=getHouseOnMapInfo&localname=bj&maptype=2&circlelat=40.220787&circlelon=116.257275&startlat=40.192079&startlon=116.199783&endlat=40.249484&endlon=116.314766&type=2&localId=1150&lastRecord=7417%2C5153%2C5152%2C5830%2C5829%2C5154%2C15509%2C5832%2C6058";

    public static final String POST_URL = "http://localhost:8080/welcome1";

    public static void readContentFromGet() {
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
//        String getURL = GET_URL + "?username=" + URLEncoder.encode("fat man", "utf-8");
        String getURL = GET_URL ;
        URL getUrl = null;
        try {
            getUrl = new URL(getURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) getUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
        // 服务器
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 取得输入流，并使用Reader读取
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=============================");
        System.out.println("Contents of get request");
        System.out.println("=============================");
        String lines;
        StringBuffer content = new StringBuffer("");
        try {
            while ((lines = reader.readLine()) != null) {
    //            System.out.println(lines);
                content.append(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Map map = JsonHelper.parseObject(content.toString(), HashMap.class);
//        System.out.println(map.get("result").toString());
//        System.out.println(content.toString());
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 断开连接
        connection.disconnect();
        System.out.println("=============================");
        System.out.println("Contents of get request ends");
        System.out.println("=============================");
    }

    public static void readContentFromPost() throws IOException {
        // Post请求的url，与get不同的是不需要带参数
        URL postUrl = new URL(POST_URL);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        // Output to the connection. Default is
        // false, set to true because post
        // method must write something to the
        // connection
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // Set the post method. Default is GET
        connection.setRequestMethod("POST");
        // Post cannot use caches
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        // This method takes effects to
        // every instances of this class.
        // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
        // connection.setFollowRedirects(true);

        // This methods only
        // takes effacts to this
        // instance.
        // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
        connection.setInstanceFollowRedirects(true);
        // Set the content type to urlencoded,
        // because we will write
        // some URL-encoded content to the
        // connection. Settings above must be set before connect!
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        // The URL-encoded contend
        // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
        String content = "firstname=" + URLEncoder.encode("一个大肥人", "utf-8");
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
        out.writeBytes(content);

        out.flush();
        out.close(); // flush and close
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        System.out.println("=============================");
        System.out.println("Contents of post request");
        System.out.println("=============================");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("=============================");
        System.out.println("Contents of post request ends");
        System.out.println("=============================");
        reader.close();
        connection.disconnect();
    }

    /** */
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        try {
            readContentFromGet();
//            readContentFromPost();
//        } catch (IOException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

}
