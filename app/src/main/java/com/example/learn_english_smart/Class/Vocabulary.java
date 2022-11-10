package com.example.learn_english_smart.Class;

import java.util.List;

public class Vocabulary {
    String word;
    String image;
    String means;
    String temporary_time;

    public Vocabulary() {
    }




    public Vocabulary(String word, String image, String means, String temporary_time) {
        this.word = word;
        this.image = image;
        this.means = means;
        this.temporary_time = temporary_time;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    public String getTemporary_time() {
        return temporary_time;
    }

    public void setTemporary_time(String temporary_time) {
        this.temporary_time = temporary_time;
    }
}
