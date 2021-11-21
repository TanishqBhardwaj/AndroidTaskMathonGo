package com.example.androidtask_mathongo.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.androidtask_mathongo.local.entity.OptionEntity;
import java.util.List;

@Dao
public interface OptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<OptionEntity> optionEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OptionEntity optionEntity);

    @Update
    void update(OptionEntity optionEntity);

    @Delete
    void delete(OptionEntity optionEntity);

    @Query("delete from option_table")
    void deleteAll();

    @Query("select * from option_table")
    List<OptionEntity> getAllOptions();
}
