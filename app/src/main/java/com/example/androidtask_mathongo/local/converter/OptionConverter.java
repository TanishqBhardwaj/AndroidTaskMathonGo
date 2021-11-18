package com.example.androidtask_mathongo.local.converter;

import androidx.room.TypeConverter;
import com.example.androidtask_mathongo.model.QuesAnsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class OptionConverter {
        @TypeConverter
        public static List<QuesAnsModel.Option> fromString(String value) {
            Type listType = new TypeToken<List<QuesAnsModel.Option>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }
        @TypeConverter
        public static String fromArrayList(List<QuesAnsModel.Option> list) {
            Gson gson = new Gson();
            return gson.toJson(list);
        }
}