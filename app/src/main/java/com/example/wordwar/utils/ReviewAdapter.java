package com.example.wordwar.utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordwar.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>{

    List<Word> allWords = new ArrayList<>();

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    //创建viewHolder来呼叫
    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //从文件中加载view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_card_without_cn,parent,false);
        return new ReviewAdapter.MyViewHolder(itemView); //创建ViewHolder
    }


    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {
        // 获取一个cell的单词
        Word word = allWords.get(position);
        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getExp());

        //最开始是false 汉语不显示
        if(word.isVisible()){
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.aSwitch.setChecked(true);
        }else{
            holder.textViewChinese.setVisibility(View.GONE);
            holder.aSwitch.setChecked(false);
        }

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setVisible(true);
                }else{
                    holder.textViewChinese.setVisibility(View.GONE);
                    word.setVisible(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWords.size(); //返回数量
    }

    //用来管理cell_card
    static class MyViewHolder extends RecyclerView.ViewHolder{ //加static 防止内存泄露

        TextView textViewNumber,textViewEnglish,textViewChinese;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch aSwitch;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //这里绑定又有点不一样了 itemView理解为最小单元
            textViewNumber = itemView.findViewById(R.id.textView_number);
            textViewEnglish = itemView.findViewById(R.id.textView_english);
            textViewChinese = itemView.findViewById(R.id.textView_chinese);
            aSwitch = itemView.findViewById(R.id.switch_cn);
        }
    }
}
