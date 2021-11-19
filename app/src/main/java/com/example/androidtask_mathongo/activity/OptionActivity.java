package com.example.androidtask_mathongo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.adapter.OptionAdapter;
import com.example.androidtask_mathongo.adapter.QuestionAdapter;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.viewmodel.QuesAnsViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OptionActivity extends AppCompatActivity {

    public static final int position = 0;

    private QuesAnsViewModel quesAnsViewModel;
    private List<QuesAnsEntity> quesAnsEntities;

    private ImageView imageViewBack;
    private TextView textViewNoOfQues;
    private TextView textViewQuestion;
    private TextView textViewTag;
    private RecyclerView recyclerViewOptions;
    private OptionAdapter optionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

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
        recyclerViewOptions.setLayoutManager(new LinearLayoutManager(this) {
                                                 @Override
                                                 public boolean canScrollVertically() {
                                                     return false;
                                                 }
                                             });
        recyclerViewOptions.setHasFixedSize(true);
    }

    private void setQuesAnsViewModel() {
        quesAnsViewModel = new ViewModelProvider(this).get(QuesAnsViewModel.class);
        quesAnsViewModel.getQuesAnsList().observe(this, quesAnsEntityList -> {
            quesAnsEntities = new ArrayList<>(quesAnsEntityList);
            setUI(getIntent().getIntExtra(String.valueOf(position), 0));
        });
    }

    private void setUI(int position) {
        textViewNoOfQues.setText("Q" + (position+1) + " (Single Correct)");
        textViewQuestion.setText(quesAnsEntities.get(position).getQuestionText());
        textViewTag.setText(quesAnsEntities.get(position).getQuesTag());

        optionAdapter = new OptionAdapter(quesAnsEntities, position);
        recyclerViewOptions.setAdapter(optionAdapter);
    }

    private void setListeners() {
        imageViewBack.setOnClickListener((view) -> {
            onBackPressed();
        });
    }
}