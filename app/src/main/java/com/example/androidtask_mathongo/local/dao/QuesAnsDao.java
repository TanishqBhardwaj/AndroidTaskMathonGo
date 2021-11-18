package com.example.androidtask_mathongo.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import java.util.List;

@Dao
public interface QuesAnsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<QuesAnsEntity> orderEntities);

    @Update
    void update(QuesAnsEntity orderEntity);

    @Delete
    void delete(QuesAnsEntity orderEntity);

    @Query("delete from ques_ans_table")
    void deleteAll();

    @Query("select * from ques_ans_table")
    LiveData<List<QuesAnsEntity>> getAllQuesAns();
}