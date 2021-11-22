package com.example.androidtask_mathongo.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.androidtask_mathongo.local.entity.OptionEntity;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.model.QuesAnsModel;
import com.example.androidtask_mathongo.repository.QuesAnsRepository;

import java.util.ArrayList;
import java.util.List;

public class QuesAnsViewModel extends AndroidViewModel {

    private QuesAnsRepository quesAnsRepository;

    public QuesAnsViewModel(@NonNull Application application) {
        super(application);
        quesAnsRepository = new QuesAnsRepository(application);
    }

    public LiveData<List<QuesAnsEntity>> getQuesAnsList() {
        return quesAnsRepository.getAllQuesAns();
    }

    //returns all the local list of selected options
    public LiveData<List<OptionEntity>> getAllOptions() {
        return quesAnsRepository.getOptionEntityList();
    }

    //adds new selected option
    public void addSelectedOption(QuesAnsEntity quesAnsEntity, int position) {
        quesAnsRepository.insertOption(new OptionEntity(quesAnsEntity.getId(),
                quesAnsEntity.getOptionList().get(position).getId(), false));
    }

    public void updateSelectedOption(QuesAnsEntity quesAnsEntity, List<OptionEntity> optionEntities) {
        for(OptionEntity optionEntity : optionEntities) {
            if(optionEntity.getQuesId().equals(quesAnsEntity.getId())) {
                quesAnsRepository.insertOption(new OptionEntity(optionEntity.getQuesId(),
                        optionEntity.getOptionId(), true));
            }
        }
    }

    //tells whether the answer is checked or not
    public boolean isAnswerChecked(List<OptionEntity> optionEntities, QuesAnsEntity quesAnsEntity) {
        for (OptionEntity optionEntity : optionEntities) {
            if(quesAnsEntity.getId().equals(optionEntity.getQuesId())) {
                return optionEntity.isChecked();
            }
        }
        return false;
    }

    public boolean isAnyOptionSelected(List<OptionEntity> optionEntities, QuesAnsEntity quesAnsEntity) {
        for (OptionEntity optionEntity : optionEntities) {
            if(quesAnsEntity.getId().equals(optionEntity.getQuesId())) {
                return true;
            }
        }
        return false;
    }

    //update local selected option list in order to reflect in adapter
    public List<OptionEntity> getUpdatedOptionList(List<OptionEntity> optionEntities, QuesAnsEntity quesAnsEntity,
                                                   int position) {
        List<OptionEntity> optionEntityList = new ArrayList<>();
        boolean flag = false;
        for(OptionEntity optionEntity : optionEntities) {
            if(quesAnsEntity.getId().equals(optionEntity.getQuesId())) {
                optionEntity.setOptionId(quesAnsEntity.getOptionList().get(position).getId());
                flag = true;
            }
            optionEntityList.add(optionEntity);
        }
        //this is the case when we need to add new entry of optionEntity object because it was not already present in
        //the optionEntities list
        if(!flag) {
            optionEntityList.add(new OptionEntity(quesAnsEntity.getId(),
                    quesAnsEntity.getOptionList().get(position).getId(), false));
        }
        return optionEntityList;
    }

    //updates check answer field in order to reflect in adapter
    public List<OptionEntity> getUpdateCheckAnswerOptionList(QuesAnsEntity quesAnsEntity,
                                                             List<OptionEntity> optionEntities) {
        List<OptionEntity> optionEntityList = new ArrayList<>();

        for(OptionEntity optionEntity : optionEntities) {
            if(optionEntity.getQuesId().equals(quesAnsEntity.getId())) {
                optionEntityList.add(new OptionEntity(optionEntity.getQuesId(), optionEntity.getOptionId(),
                        true));
            }
            else {
                optionEntityList.add(optionEntity);
            }
        }

        return optionEntityList;
    }
}
