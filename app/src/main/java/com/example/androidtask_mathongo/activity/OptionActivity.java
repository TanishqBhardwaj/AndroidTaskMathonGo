package com.example.androidtask_mathongo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.adapter.OptionAdapter;
import com.example.androidtask_mathongo.local.entity.OptionEntity;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.util.Constants;
import com.example.androidtask_mathongo.viewmodel.QuesAnsViewModel;

import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class OptionActivity extends AppCompatActivity {

    public static final int position = 0;

    private QuesAnsViewModel quesAnsViewModel;
    private List<QuesAnsEntity> quesAnsEntities = new ArrayList<>();
    private List<OptionEntity> optionEntities = new ArrayList<>();
    private OptionAdapter optionAdapter;

    private ImageView imageViewBack;
    private TextView textViewNoOfQues;
    private TextView textViewQuestion;
    private TextView textViewTag;
    private RecyclerView recyclerViewOptions;
    private Button buttonCheckAnswer;
    private ImageView imageViewNextQues;
    private ImageView imageViewPrevQues;
    private TextView textViewHardcodeSol;
    private TextView textViewSolution;

    private int quesPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        quesPos = getIntent().getIntExtra(String.valueOf(position), 0);

        setView();
        setListeners();
        setQuesAnsViewModel();
    }

    private void setView() {
        imageViewBack = findViewById(R.id.image_view_back);
        textViewNoOfQues = findViewById(R.id.text_view_ques_no_detail);
        textViewQuestion = findViewById(R.id.text_view_question_detail);
        textViewTag = findViewById(R.id.text_view_tag_detail);
        recyclerViewOptions = findViewById(R.id.recycler_view_options);
        buttonCheckAnswer = findViewById(R.id.check_ans_button);
        imageViewNextQues = findViewById(R.id.image_view_next_ques);
        imageViewPrevQues = findViewById(R.id.image_view_prev_ques);
        textViewHardcodeSol = findViewById(R.id.text_view_hardcode_sol);
        textViewSolution = findViewById(R.id.text_view_sol);
        recyclerViewOptions.setLayoutManager(new LinearLayoutManager(this) {
                                                 @Override
                                                 public boolean canScrollVertically() {
                                                     return false;
                                                 }
                                             });
        recyclerViewOptions.setHasFixedSize(true);
    }

    private void setListeners() {
        imageViewBack.setOnClickListener(view -> {
            onBackPressed();
        });

        buttonCheckAnswer.setOnClickListener(view -> {
            onClickCheckAnswer();
        });

        imageViewNextQues.setOnClickListener(view -> {
            setUI(++quesPos);
        });

        imageViewPrevQues.setOnClickListener(view -> {
            setUI(--quesPos);
        });
    }

    private void onClickCheckAnswer() {
        textViewHardcodeSol.setVisibility(View.VISIBLE);
        textViewSolution.setVisibility(View.VISIBLE);
        buttonCheckAnswer.setEnabled(false);
        quesAnsViewModel.updateSelectedOption(quesAnsEntities.get(quesPos), optionEntities);
        List<OptionEntity> optionEntityList = quesAnsViewModel.getUpdateCheckAnswerOptionList(
                quesAnsEntities.get(quesPos), optionEntities);
        optionAdapter.updateList(quesAnsEntities, optionEntityList, Constants.CHECK_ANSWER);
    }

    private void setQuesAnsViewModel() {
        quesAnsViewModel = new ViewModelProvider(this).get(QuesAnsViewModel.class);
//        optionEntities = quesAnsViewModel.getAllOptions();
        quesAnsViewModel.getAllOptions().observe(this, optionEntityList -> {
            optionEntities = new ArrayList<>(optionEntityList);
        });
        quesAnsViewModel.getQuesAnsList().observe(this, quesAnsEntityList -> {
            quesAnsEntities = new ArrayList<>(quesAnsEntityList);
            setUI(quesPos);
        });
    }

    private void setUI(int position) {
        //check whether pos in range when called from setListeners()
        if(position<quesAnsEntities.size() && position>=0) {
            textViewNoOfQues.setText("Q" + (position+1) + " (Single Correct)");
            textViewQuestion.setText(quesAnsEntities.get(position).getQuestionText());
            textViewTag.setText(quesAnsEntities.get(position).getQuesTag());
            textViewSolution.setText(quesAnsEntities.get(position).getSolutionText());

            imageViewNextQues.setImageResource(R.drawable.ic_next_button);
            imageViewPrevQues.setImageResource(R.drawable.ic_back_button);

            setCheckAnswerState(position);
            setOptionAdapter(position);

        }

        //adjust prev and next button
        setPrevAndNextButton(position);

        //adjusting pos value
        adjustPosValue(position);
    }

    private void setCheckAnswerState(int position) {
        //checks whether answer is checked or not
        if(quesAnsViewModel.isAnswerChecked(optionEntities, quesAnsEntities.get(position))) {
            textViewHardcodeSol.setVisibility(View.VISIBLE);
            textViewSolution.setVisibility(View.VISIBLE);
            buttonCheckAnswer.setEnabled(false);
        }
        else {
            //checks whether any option is selected or not
            buttonCheckAnswer.setEnabled(quesAnsViewModel.isAnyOptionSelected(
                    optionEntities, quesAnsEntities.get(position)));
            textViewHardcodeSol.setVisibility(View.GONE);
            textViewSolution.setVisibility(View.GONE);
        }
    }

    private void setPrevAndNextButton(int position) {
        //disable prev button when on 1st ques
        if(position==0) {
            imageViewPrevQues.setImageResource(R.drawable.ic_prev_button_disabled);
        }

        //disable next button when on last ques
        if(position==(quesAnsEntities.size()-1)) {
            imageViewNextQues.setImageResource(R.drawable.ic_next_button_disabled);
        }
    }

    private void adjustPosValue(int position) {
        if(position>=quesAnsEntities.size()) {
            --quesPos;
        }

        if(position<0) {
            ++quesPos;
        }
    }

    private void setOptionAdapter(int position) {
//        optionEntities = quesAnsViewModel.getAllOptions();
        optionAdapter = new OptionAdapter(quesAnsEntities, position, this, optionEntities);
        recyclerViewOptions.setAdapter(optionAdapter);

        optionAdapter.setOnItemClickListener((optionPos, isClickable) -> {
            if(isClickable) {
                quesAnsViewModel.addSelectedOption(quesAnsEntities.get(position), optionPos);
                updateOptionList(position, optionPos);
                buttonCheckAnswer.setEnabled(true);
            }
            else {
                buttonCheckAnswer.setEnabled(false);
            }
        });
    }

    //adjust card state
    private void updateOptionList(int position, int optionPos) {
//        optionEntities = quesAnsViewModel.getAllOptions();
        List<OptionEntity> optionEntityList = quesAnsViewModel.getUpdatedOptionList(optionEntities,
                quesAnsEntities.get(position), optionPos);
        optionAdapter.updateList(quesAnsEntities, optionEntityList, Constants.OPTION_SELECTED);
    }
}