package com.example.wordwar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
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

    //顶部导航栏
    NavController controller;
    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. 获取controller,这样会报错
        //controller = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        assert navHostFragment != null;
        controller = navHostFragment.getNavController();

        //2. 设置左上角导航栏
        NavigationUI.setupActionBarWithNavController(this, controller);
    }

    //3. 设置导航栏作用
    @Override
    public boolean onSupportNavigateUp() {
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //正在答题时退出
        if (controller.getCurrentDestination().getId() == R.id.fightFragment) {

            //创建对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are You Sure?"); //getString获取一个对象 转换成String类型
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    //退出时当前分数清零
                    myViewModel.getCurrentScore().setValue(0);
                    myViewModel.setToken(0);
                    controller.navigateUp();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (controller.getCurrentDestination().getId() == R.id.titleFragment) {
            finish();
        } else {
            controller.navigate(R.id.titleFragment);
        }
        return super.onSupportNavigateUp();
    }

    //4. 重写back键
    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }


}