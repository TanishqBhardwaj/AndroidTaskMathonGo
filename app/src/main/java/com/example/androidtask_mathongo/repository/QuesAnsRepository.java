package com.example.androidtask_mathongo.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.androidtask_mathongo.local.dao.QuesAnsDao;
import com.example.androidtask_mathongo.local.database.QuesAnsDatabase;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.model.QuesAnsModel;
import com.example.androidtask_mathongo.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class QuesAnsRepository {

    private QuesAnsDao quesAnsDao;
    private Context context;
    private LiveData<List<QuesAnsEntity>> allQuesAns;

    public QuesAnsRepository(Context context) {
        this.context = context;
        quesAnsDao = QuesAnsDatabase.getInstance(context).quesAnsDao();
        fetchDataFromJson();
        allQuesAns = quesAnsDao.getAllQuesAns();
    }

    public void fetchDataFromJson() {
        String jsonFileString = "", quesNo = "", quesTag = "";
        int count = 0;
        jsonFileString = Utils.loadJson(context, "gravitation.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<QuesAnsModel>>() { }.getType();

        List<QuesAnsModel> questionAnswerList = gson.fromJson(jsonFileString, listUserType);
        List<QuesAnsEntity> quesAnsEntityList = new ArrayList<>();
        assert questionAnswerList != null;

        //converting json list to local list form
        for(QuesAnsModel quesAnsModel : questionAnswerList) {
            ++count;

            QuesAnsEntity quesAnsEntity = new QuesAnsEntity(
                    quesAnsModel.getId(),
                    getQuesNo(count),
                    getQuesTag(quesAnsModel),
                    quesAnsModel.getQuestion().getQuestionText(),
                    quesAnsModel.getQuestion().getImage(),
                    quesAnsModel.getOptions()
            );

            quesAnsEntityList.add(quesAnsEntity);
        }

        //storing data to local
        insertQuesAnsToLocal(quesAnsEntityList);
    }

    private String getQuesNo(int count) {
        if(count > 9) {
            return String.valueOf(count);
        }
        else {
            return "0" + count;
        }
    }

    //concatenate exam and paper year
    private String getQuesTag(QuesAnsModel quesAnsModel) {
        return quesAnsModel.getExams().get(0) + " " + quesAnsModel.getPreviousYearPapers().get(0);
    }

    public void insertQuesAnsToLocal(List<QuesAnsEntity> quesAnsEntityList) {
        try {
            Completable.fromAction(() -> {
                quesAnsDao.insert(quesAnsEntityList);
            }).subscribeOn(Schedulers.io())
                    .subscribe();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<QuesAnsEntity>> getAllQuesAns() {
        return allQuesAns;
    }
}
