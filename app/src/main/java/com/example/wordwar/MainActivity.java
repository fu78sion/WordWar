package com.example.wordwar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //button = findViewById(R.id.button);
       //button.setOnClickListener(new View.OnClickListener() {
       //    @Override
       //    public void onClick(View view) {

       //        sendRequestWithOkHttp();
       //    }
       //});
    }

    // 网络部分采用OKHttp的方式
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //1. 创建OKHTTPClient实例
                    OkHttpClient client = new OkHttpClient();

                    //2. 创建Request对象,设置目标网络地址
                    Request request = new Request.Builder()

                            //这里降低了targetSdk版本 不然会报错，无法解析
                            .url("http://10.0.2.2/word_plant.js")
                            .build();

                    //3. 发送请求获取返回数据
                    Response response = client.newCall(request).execute();

                    //4. 获得具体内容
                    String responseData = Objects.requireNonNull(response.body()).string();

                    //5. 自定义操作
                    parseJSONWithGSON(responseData); //用JSONObject解析json

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<Word> wordList = gson.fromJson(jsonData,
                new TypeToken<List<Word>>() {
                }.getType());
        for (Word word : wordList) {
            Log.d("MainActivity", "word is: " + word.getWord());
            Log.d("MainActivity", "speak is: " + word.getSpeak());
            Log.d("MainActivity", "exp is: " + word.getExp());
        }
    }
}