package com.example.androidtask_mathongo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.androidtask_mathongo.model.QuesAnsModel;
import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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

        String jsonFileString = Utils.loadJson(getApplicationContext(), "gravitation.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<QuesAnsModel>>() { }.getType();

        List<QuesAnsModel> questionAnswerList = gson.fromJson(jsonFileString, listUserType);
        assert questionAnswerList != null;
        for(QuesAnsModel quesAnsModel : questionAnswerList) {
            Log.e(TAG, "onCreate: "+  quesAnsModel.getQuestion().getQuestionText());
        }
    }


}