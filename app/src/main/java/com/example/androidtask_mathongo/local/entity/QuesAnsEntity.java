package com.example.androidtask_mathongo.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.androidtask_mathongo.local.converter.OptionConverter;
import com.example.androidtask_mathongo.model.QuesAnsModel;
import java.util.List;

@Entity(tableName = "ques_ans_table", indices = {@Index(value = {"id"}, unique = true)})
public class QuesAnsEntity {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    private String id;
    private String quesNo;
    private String quesTag;
    private String questionText;
    private String questionImage;

    @TypeConverters({OptionConverter.class})
    private List<QuesAnsModel.Option> optionList;

    @Ignore
    public QuesAnsEntity() {}

    public QuesAnsEntity(String id, String quesNo, String quesTag, String questionText, String questionImage,
                         List<QuesAnsModel.Option> optionList) {
        this.id = id;
        this.quesNo = quesNo;
        this.quesTag = quesTag;
        this.questionText =  questionText;
        this.questionImage =  questionImage;
        this.optionList = optionList;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuesNo() {
        return quesNo;
    }

    public void setQuesNo(String quesNo) {
        this.quesNo = quesNo;
    }

    public String getQuesTag() {
        return quesTag;
    }

    public void setQuesTag(String quesTag) {
        this.quesTag = quesTag;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public List<QuesAnsModel.Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuesAnsModel.Option> optionList) {
        this.optionList = optionList;
    }
}