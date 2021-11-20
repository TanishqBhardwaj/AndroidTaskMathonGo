package com.example.androidtask_mathongo.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.androidtask_mathongo.local.entity.OptionEntity;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.repository.QuesAnsRepository;
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

    public void updateOption(QuesAnsEntity quesAnsEntity, int position) {
        quesAnsRepository.insertOption(new OptionEntity(quesAnsEntity.getId(),
                quesAnsEntity.getOptionList().get(position).getId(), true));
    }

    public LiveData<List<OptionEntity>> getAllOptions() {
        return quesAnsRepository.getOptionEntityList();
    }
}
