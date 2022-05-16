package com.example.wordwar;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyViewModel extends AndroidViewModel {

    //1. 添加完构造器后，还得多写一个SavedStateHandle handle 作用：即使进程被杀死，handle数据也能存活
    SavedStateHandle handle;

    //2. 为了使用shp，需要声明一些常量
    private final static String KEY_HIGH_SCORE = "key_high_score"; //最高分
    private final static String KEY_CURRENT_SCORE = "key_current_score"; // 当前得分
    private final static String KEY_New_Word = "key_new_word"; // 新单词
    private final static String KEY_Answer = "key_answer"; // 答案
    private final static String KEY_Button_A = "key_button_a"; // 按钮
    private final static String KEY_Button_B = "key_button_b"; // 按钮
    private final static String KEY_Button_C = "key_button_c"; // 按钮
    private final static String KEY_Button_D = "key_button_d"; // 按钮
    private final static String SAVE_SHP_DATA_NAME = "save_shp_data_name"; //shp存储

    public int token = 0;
    public boolean winFlag = false;
    public ArrayList<Integer> list = new ArrayList<>();

    //主要用在每次回退时token清零，提供了一个接口
    public void setToken(int token) {
        this.token = token;
    }

    //所有单词存储进list
    List<Word> wordList = new ArrayList<>();
    MutableLiveData<List<Word>> allWordsLive;

    //创建一个livedata，用在recyclerView中
    public MutableLiveData<List<Word>> getAllWordsLive() {
        if (allWordsLive == null) {
            allWordsLive = new MutableLiveData<>();
            allWordsLive.setValue(wordList);
        }
        return allWordsLive;
    }

    //3. 开始写,有个 application 参数，方便很多，貌似不用写context了
    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCORE)) {

            //如果为空 就读取一个
            SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);

            //读完之后把数据设置进去，键值对，缺省值是0
            handle.set(KEY_HIGH_SCORE, shp.getInt(KEY_HIGH_SCORE, 0));

            //同理，意味着其他数据也没有，所以接下来进行初始化
            handle.set(KEY_CURRENT_SCORE, 0);
            handle.set(KEY_New_Word, "Are You Ready ?");
            handle.set(KEY_Button_A, "A");
            handle.set(KEY_Button_B, "B");
            handle.set(KEY_Button_C, "C");
            handle.set(KEY_Button_D, "D");
        }
        sendRequestWithOkHttp(); //加载数据
        this.handle = handle;
    }

    //写这些量的get方法 必须写成public
    //否则在xml中无法绑定，绑定时直接data点，点的不是成员变量而是get方法后边的一串东西
    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<String> getNewWord() {
        return handle.getLiveData(KEY_New_Word);
    }

    public MutableLiveData<String> getButtonA() {
        return handle.getLiveData(KEY_Button_A);
    }

    public MutableLiveData<String> getAnswer() {
        return handle.getLiveData(KEY_Answer);
    }

    public MutableLiveData<String> getButtonB() {
        return handle.getLiveData(KEY_Button_B);
    }

    public MutableLiveData<String> getButtonC() {
        return handle.getLiveData(KEY_Button_C);
    }

    public MutableLiveData<String> getButtonD() {
        return handle.getLiveData(KEY_Button_D);
    }

    // 网络部分采用OKHttp的方式
    private void sendRequestWithOkHttp() {
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

    //解析数据
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        wordList = gson.fromJson(jsonData,
                new TypeToken<List<Word>>() {
                }.getType());
        for (Word word : wordList) {
            Log.d("MainActivity", "word is: " + word.getWord());
            Log.d("MainActivity", "speak is: " + word.getSpeak());
            Log.d("MainActivity", "exp is: " + word.getExp());
        }
    }

    //题目部分
    public void generator() {
        int level = wordList.size();
        Random random = new Random();
        int x, y, z;
        x = random.nextInt(level);
        y = random.nextInt(level);
        z = random.nextInt(level);

        while (x == token) {
            x = random.nextInt(level);
        }

        while ((y == token) || (y == x)) {
            y = random.nextInt(level);
        }

        while ((z == token) || (z == x) || (z == y)) {
            z = random.nextInt(level);
        }

        //获取一个新单词
        getNewWord().setValue(wordList.get(token).getWord());

        //设置答案
        getAnswer().setValue(wordList.get(token).getExp());

        list.add(token);
        list.add(x);
        list.add(y);
        list.add(z);
        Collections.shuffle(list);


        //设置按钮 实现真正随机
        getButtonA().setValue(wordList.get(list.get(0)).getExp());
        getButtonB().setValue(wordList.get(list.get(1)).getExp());
        getButtonC().setValue(wordList.get(list.get(2)).getExp());
        getButtonD().setValue(wordList.get(list.get(3)).getExp());

        token++;
        list.clear();
    }

    //保存最高纪录
    public void save() {
        //涉及到读取文件，就用shp
        SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit = shp.edit();

        //存入数据，有不同的put方法，按照键值对的形式
        edit.putInt(KEY_HIGH_SCORE, getHighScore().getValue());
        edit.apply();
    }

    //开始答题环节
    //答对了
    public void answerRight() {

        //获取当前数据，其实获取了一个livedata对象 要获取真实值的话必须得getValue()
        //当前得分++
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);

        //超越最高分
        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            getHighScore().setValue(getCurrentScore().getValue());
            winFlag = true;
        }

        //再给他生成一道新题
        generator();
    }
}
