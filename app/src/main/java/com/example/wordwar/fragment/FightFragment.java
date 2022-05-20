package com.example.wordwar.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wordwar.MyViewModel;
import com.example.wordwar.R;
import com.example.wordwar.databinding.FragmentFightBinding;
import com.example.wordwar.service.MyService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FightFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FightFragment newInstance(String param1, String param2) {
        FightFragment fragment = new FightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_fight, container, false);
        //2. 定义变量 myViewModel 没什么大变化
        MyViewModel myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.generator(); //生成题目

        // binding变化很大
        FragmentFightBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fight, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());

        binding.buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(binding.buttonA.getText()).equals(myViewModel.getAnswer().getValue())) {
                    myViewModel.answerRight();
                } else { //失败，有两种情况，一种是超越记录，一种是没超越 这里会涉及到fragment的跳转
                    NavController controller = Navigation.findNavController(view);
                    if (myViewModel.winFlag) { //胜利
                        controller.navigate(R.id.action_fightFragment_to_winFragment);
                        myViewModel.winFlag = false; //恢复
                        myViewModel.save(); //保存
                    } else { //失败
                        controller.navigate(R.id.action_fightFragment_to_loseFragment);
                    }
                }
            }
        });

        binding.buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(binding.buttonB.getText()).equals(myViewModel.getAnswer().getValue())) {
                    myViewModel.answerRight();
                } else { //失败，有两种情况，一种是超越记录，一种是没超越 这里会涉及到fragment的跳转
                    NavController controller = Navigation.findNavController(view);
                    if (myViewModel.winFlag) { //胜利
                        controller.navigate(R.id.action_fightFragment_to_winFragment);
                        myViewModel.winFlag = false; //恢复
                        myViewModel.save(); //保存
                    } else { //失败
                        controller.navigate(R.id.action_fightFragment_to_loseFragment);
                    }
                }
            }
        });

        binding.buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(binding.buttonC.getText()).equals(myViewModel.getAnswer().getValue())) {
                    myViewModel.answerRight();
                } else { //失败，有两种情况，一种是超越记录，一种是没超越 这里会涉及到fragment的跳转
                    NavController controller = Navigation.findNavController(view);
                    if (myViewModel.winFlag) { //胜利
                        controller.navigate(R.id.action_fightFragment_to_winFragment);
                        myViewModel.winFlag = false; //恢复
                        myViewModel.save(); //保存
                    } else { //失败
                        controller.navigate(R.id.action_fightFragment_to_loseFragment);
                    }

                }
            }
        });

        binding.buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(binding.buttonD.getText()).equals(myViewModel.getAnswer().getValue())) {
                    myViewModel.answerRight();
                } else { //失败，有两种情况，一种是超越记录，一种是没超越 这里会涉及到fragment的跳转
                    NavController controller = Navigation.findNavController(view);
                    if (myViewModel.winFlag) { //胜利
                        controller.navigate(R.id.action_fightFragment_to_winFragment);
                        myViewModel.winFlag = false; //恢复
                        myViewModel.save(); //保存
                    } else { //失败
                        controller.navigate(R.id.action_fightFragment_to_loseFragment);
                    }
                }
            }
        });

        binding.imageButtonUk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), MyService.class);
                intent.putExtra("query", "https://dict.youdao.com/dictvoice?type=1&audio=" + myViewModel.getNewWord().getValue());
                requireActivity().startService(intent);
                //requireActivity().stopService(intent);
            }
        });

        binding.imageButtonUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), MyService.class);
                intent.putExtra("query", "https://dict.youdao.com/dictvoice?type=0&audio=" + myViewModel.getNewWord().getValue());
                requireActivity().startService(intent);
            }
        });

        return binding.getRoot();
    }
}