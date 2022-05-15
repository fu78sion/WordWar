package com.example.wordwar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;

public class MyViewModel extends AndroidViewModel {

    //1. 添加完构造器后，还得多写一个SavedStateHandle handle 作用：即使进程被杀死，handle数据也能存活
    SavedStateHandle handle;

    //3. 开始写,有个 application 参数，方便很多，貌似不用写context了
    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
    }
}
