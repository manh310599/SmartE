package com.example.learn_english_smart.Class;

public class Vocabulary {

    String word;
    String image;
    String means;

    public Vocabulary() {
    }

    public Vocabulary(String word, String image, String means) {
        this.word = word;
        this.image = image;
        this.means = means;
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
}
