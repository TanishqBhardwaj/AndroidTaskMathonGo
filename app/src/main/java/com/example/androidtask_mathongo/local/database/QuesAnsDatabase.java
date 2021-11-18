package com.example.androidtask_mathongo.local.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.androidtask_mathongo.local.dao.QuesAnsDao;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;

@Database(entities = {QuesAnsEntity.class}, version = 1, exportSchema = false)
public abstract class QuesAnsDatabase extends RoomDatabase {

    private static QuesAnsDatabase instance;

    public abstract QuesAnsDao quesAnsDao();

    public static synchronized QuesAnsDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, QuesAnsDatabase.class, "ques_ans_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}