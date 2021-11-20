package com.example.androidtask_mathongo.local.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (tableName = "option_table", indices = {@Index(value = {"quesId"}, unique = true)})
public class OptionEntity {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    private String quesId;
    private String optionId;
    private boolean isChecked;

    public OptionEntity(String quesId, String optionId, boolean isChecked) {
        this.quesId = quesId;
        this.optionId = optionId;
        this.isChecked = isChecked;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
