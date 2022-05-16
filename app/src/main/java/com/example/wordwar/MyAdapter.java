package com.example.wordwar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. extends RecyclerView.Adapter
 * 2. 自定义viewHolder
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //存放数据
    List<Word> allWords = new ArrayList<>();

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    //创建viewHolder来呼叫
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        //从文件中加载view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_card,parent,false);
        return new MyViewHolder(itemView); //创建ViewHolder
    }

    //视图和数据绑定的时候呼叫
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // 获取一个cell的单词
        Word word = allWords.get(position);
        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getExp());
    }

    @Override
    public int getItemCount() {
        return allWords.size(); //返回数量
    }

    //用来管理cell_card
    static class MyViewHolder extends RecyclerView.ViewHolder{ //加static 防止内存泄露

        TextView textViewNumber,textViewEnglish,textViewChinese;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //这里绑定又有点不一样了 itemView理解为最小单元
            textViewNumber = itemView.findViewById(R.id.textView_number);
            textViewEnglish = itemView.findViewById(R.id.textView_english);
            textViewChinese = itemView.findViewById(R.id.textView_chinese);
        }
    }
}
