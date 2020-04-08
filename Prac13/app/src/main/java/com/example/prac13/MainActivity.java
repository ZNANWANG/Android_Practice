package com.example.prac13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private Button sendRequest;
    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendRequest = findViewById(R.id.button);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendRequestWithHttpURLConnectionGet();
//                sendRequestWithHttpURLConnectionPost();
//                sendRequestWithOkHttpGet();
//                sendRequestWithOkHttpPost();
                sendRequestWithOkHttp();
            }
        });
        responseText = findViewById(R.id.textView);
    }

    public void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                BufferedReader bufferedReader = null;
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://192.168.2.171/get_data.xml").build(); //访问本机地址（ipconfig）
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
//                    parseXMLWithPull(responseData);
                    parseXMLWithSAX(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void parseXMLWithSAX(String responseData){
        try{
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
            ContentHandler contentHandler = new ContentHandler();
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(new InputSource(new StringReader(responseData)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseXMLWithPull(String responseData){
        try{
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(responseData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while(eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch(eventType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        } else if("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        } else if("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case  XmlPullParser.END_TAG: {
                        if("app".equals(nodeName)){
                            Log.d(TAG, "id is: " + id);
                            Log.d(TAG, "name is: " + name);
                            Log.d(TAG, "version is: " + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendRequestWithOkHttpGet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://www.baidu.com").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendRequestWithOkHttpPost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient okHttpClient = new OkHttpClient();
                    JSONObject jsonObject = new JSONObject();
                    RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
                    Request request = new Request.Builder().url("http:\\www.baidu.com").post(requestBody).build();
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendRequestWithHttpURLConnectionPost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL("http://www.baidu.com");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream.writeBytes("username=admin&password=123456");
                    dataOutputStream.close();
                    int responseCode = httpURLConnection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuffer response = new StringBuffer();
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        showResponse(response.toString());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendRequestWithHttpURLConnectionGet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL("http://www.baidu.com");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    int responseCode = httpURLConnection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuffer response = new StringBuffer();
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        showResponse(response.toString());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    if(bufferedReader != null){
                        try{
                            bufferedReader.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }

    public void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
