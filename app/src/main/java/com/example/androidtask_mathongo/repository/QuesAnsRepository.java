package com.example.androidtask_mathongo.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.androidtask_mathongo.local.dao.OptionDao;
import com.example.androidtask_mathongo.local.dao.QuesAnsDao;
import com.example.androidtask_mathongo.local.database.QuesAnsDatabase;
import com.example.androidtask_mathongo.local.entity.OptionEntity;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.model.QuesAnsModel;
import com.example.androidtask_mathongo.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class QuesAnsRepository {

    private QuesAnsDao quesAnsDao;
    private OptionDao optionDao;
    private Context context;
    private LiveData<List<QuesAnsEntity>> allQuesAns;
    private LiveData<List<OptionEntity>> allOptions;

    public QuesAnsRepository(Context context) {
        this.context = context;
        quesAnsDao = QuesAnsDatabase.getInstance(context).quesAnsDao();
        optionDao = QuesAnsDatabase.getInstance(context).optionDao();
        fetchDataFromJson();
        allQuesAns = quesAnsDao.getAllQuesAns();
        allOptions = optionDao.getAllOptions();
    }

    public void fetchDataFromJson() {
        String jsonFileString = Utils.loadJson(context, "gravitation.json");
        int count = 0;
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
                    quesAnsModel.getOptions(),
                    quesAnsModel.getSolution().getText(),
                    quesAnsModel.getSolution().getImage()
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

    public void insertOption(OptionEntity optionEntity) {
        try {
            Completable.fromAction(() -> {
                optionDao.insert(optionEntity);
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

//    public List<OptionEntity> getOptionEntityList() {
//        List<OptionEntity> optionEntities = new ArrayList<>();
//        try {
//            Completable.fromAction(() -> {
//                optionEntities.addAll(optionDao.getAllOptions());
//            }).subscribeOn(Schedulers.io())
//                    .subscribe();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return optionEntities;
//    }

    public LiveData<List<OptionEntity>> getOptionEntityList() {
        return allOptions;
    }
}
