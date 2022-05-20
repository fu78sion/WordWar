package com.example.wordwar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.wordwar.MyViewModel;
import com.example.wordwar.R;

import java.util.Objects;

public class PracticeActivity extends AppCompatActivity {

    //顶部导航栏
    NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //1. 获取controller,这样会报错
        //controller = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        controller = navHostFragment.getNavController();

        //2. 设置左上角导航栏
        NavigationUI.setupActionBarWithNavController(this, controller);


        //myViewModel = new ViewModelProvider(MainActivity.mainActivity).get(MyViewModel.class);

        Log.d("test", "onCreate: succeed");

    }

    //3. 设置导航栏作用
    @Override
    public boolean onSupportNavigateUp() {

        if(Objects.requireNonNull(controller.getCurrentDestination()).getId() == R.id.webFragment) {
            controller.navigate(R.id.practiceFragment);
        }else {
            Intent intent = new Intent(PracticeActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onSupportNavigateUp();
    }

    //4. 重写back键
    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}