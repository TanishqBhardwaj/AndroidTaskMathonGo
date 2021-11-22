package com.example.androidtask_mathongo.model;

import java.util.List;

public class QuesAnsModel {

    private String id;
    private List<String> exams;
    private List<String> previousYearPapers;
    private Question question;
    private List<Option> options;
    private Solution solution;

    public QuesAnsModel(String id, List<String> exams, List<String> previousYearPapers,
                        Question question, List<Option> options, Solution solution) {
        this.id = id;
        this.exams = exams;
        this.previousYearPapers = previousYearPapers;
        this.question = question;
        this.options = options;
        this.solution = solution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getExams() {
        return exams;
    }

    public void setExams(List<String> exams) {
        this.exams = exams;
    }

    public List<String> getPreviousYearPapers() {
        return previousYearPapers;
    }

    public void setPreviousYearPapers(List<String> previousYearPapers) {
        this.previousYearPapers = previousYearPapers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public static class Option {
        private String id;
        private String text;
        private String image;
        private boolean isCorrect;

        public Option(String id, String text, String image, boolean isCorrect) {
            this.id = id;
            this.text = text;
            this.image = image;
            this.isCorrect = isCorrect;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOptionText() {
            return text;
        }

        public void setOptionText(String text) {
            this.text = text;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            isCorrect = correct;
        }
    }

    public static class Question {
        private String text;
        private String image;

        public Question(String text, String image) {
            this.text = text;
            this.image = image;
        }

        public String getQuestionText() {
            return text;
        }

        public void setQuestionText(String text) {
            this.text = text;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class Solution {
        private String text;
        private String image;

        public Solution(String text, String image) {
            this.text = text;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
