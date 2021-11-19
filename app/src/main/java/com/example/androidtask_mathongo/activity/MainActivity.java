package com.example.androidtask_mathongo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidtask_mathongo.adapter.QuestionAdapter;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.model.QuesAnsModel;
import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.util.Utils;
import com.example.androidtask_mathongo.viewmodel.QuesAnsViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private QuesAnsViewModel quesAnsViewModel;
    private List<QuesAnsEntity> quesAnsEntities;
    private TextView textView;
    private Spinner spinnerAttempted;
    private RecyclerView recyclerViewQuestions;
    private QuestionAdapter questionAdapter;

//    private String doubleEscapeTeX(String s) {
//        String t="";
//        for (int i=0; i < s.length(); i++) {
//            if (s.charAt(i) == '\'') t += '\\';
//            if (s.charAt(i) != '\n') t += s.charAt(i);
//            if (s.charAt(i) == '\\') t += "\\";
//        }
//        return t;
//    }
//
//    public void onClick(View v) {
//        if (v == findViewById(R.id.button2)) {
//            WebView w = (WebView) findViewById(R.id.webview);
//            EditText e = (EditText) findViewById(R.id.edit);
//            w.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
//                    +doubleEscapeTeX(e.getText().toString())+"\\\\]';");
//            w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
//        }
//        else if (v == findViewById(R.id.button3)) {
//            WebView w = (WebView) findViewById(R.id.webview);
//            EditText e = (EditText) findViewById(R.id.edit);
//            e.setText("");
//            w.loadUrl("javascript:document.getElementById('math').innerHTML='';");
//            w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
//        }
//    }
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        WebView w = (WebView) findViewById(R.id.webview);
//        w.getSettings().setJavaScriptEnabled(true);
//        w.getSettings().setBuiltInZoomControls(true);
//        w.loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
//                +"MathJax.Hub.Config({ "
//                +"showMathMenu: false, "
//                +"jax: ['input/TeX','output/HTML-CSS'], "
//                +"extensions: ['tex2jax.js'], "
//                +"TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
//                +"'noErrors.js','noUndefined.js'] } "
//                +"});</script>"
//                +"<script type='text/javascript' "
//                +"src='file:///android_asset/MathJax/MathJax.js'"
//                +"></script><span id='math'></span>","text/html","utf-8","");
//        EditText e = (EditText) findViewById(R.id.edit);
//        e.setBackgroundColor(Color.LTGRAY);
//        e.setTextColor(Color.BLACK);
//        e.setText("");
//        Button b = (Button) findViewById(R.id.button2);
//        b.setOnClickListener(this);
//        b = (Button) findViewById(R.id.button3);
//        b.setOnClickListener(this);
//
//    }

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