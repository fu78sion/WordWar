package com.example.wordwar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.wordwar.MyViewModel;
import com.example.wordwar.R;
import com.example.wordwar.utils.MyAdapter;
import com.example.wordwar.utils.ReviewAdapter;
import com.example.wordwar.utils.Word;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //设置viewModel 方便存储数据
        // MainActivity.mainActivity方便共享同一个viewModel，不然新的activity数据显示不出来
        myViewModel = new ViewModelProvider(MainActivity.mainActivity).get(MyViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //设置RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_review);

        //设置adapter 主要是把最小单元格设置进去
        ReviewAdapter reviewAdapter = new ReviewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reviewAdapter);



        //用liveData来监听数据
        myViewModel.getAllWordsLiveReview().observe(this, new Observer<List<Word>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Word> words) {

                //设置数据
                reviewAdapter.setAllWords(words);

                //调用
                reviewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
        startActivity(intent);
    }
}