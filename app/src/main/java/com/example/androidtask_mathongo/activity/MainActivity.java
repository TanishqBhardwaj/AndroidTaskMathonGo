package com.example.androidtask_mathongo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.androidtask_mathongo.adapter.QuestionAdapter;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.viewmodel.QuesAnsViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private QuesAnsViewModel quesAnsViewModel;
    private List<QuesAnsEntity> quesAnsEntities = new ArrayList<>();
    private TextView textView;
    private Spinner spinnerAttempted;
    private RecyclerView recyclerViewQuestions;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        setQuesAnsViewModel();
    }

    @SuppressLint("SetTextI18n")
    private void setQuesAnsViewModel() {
        quesAnsViewModel = new ViewModelProvider(this).get(QuesAnsViewModel.class);
        quesAnsViewModel.getQuesAnsList().observe(this, quesAnsEntityList -> {
            quesAnsEntities = new ArrayList<>(quesAnsEntityList);
            textView.setText(quesAnsEntities.size() + " Qs");
            setQuestionAdapter();
        });
    }

    private void setView() {
        textView = findViewById(R.id.text_view_no_of_ques);
        spinnerAttempted = findViewById(R.id.spinner);
        recyclerViewQuestions = findViewById(R.id.recycler_view_questions);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerViewQuestions.setHasFixedSize(true);

        String[] spinnerItems = new String[]{"Attempted", "Not Attempted"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, spinnerItems);
        spinnerAttempted.setAdapter(spinnerAdapter);
    }

    private void setQuestionAdapter() {
        questionAdapter = new QuestionAdapter(quesAnsEntities);
        recyclerViewQuestions.setAdapter(questionAdapter);

        questionAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(this, OptionActivity.class);
            intent.putExtra(String.valueOf(OptionActivity.position), position);
            startActivity(intent);
        });
    }

    private void setListeners() {

    }
}