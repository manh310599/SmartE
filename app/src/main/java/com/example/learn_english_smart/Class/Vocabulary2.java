package com.example.learn_english_smart.Class;





public class Vocabulary2 {


    String word;
    String image;
    String means;
    long temporary_time ;
    float ef ;
    long note;


    public Vocabulary2() {
    }

    public Vocabulary2(String word, String image, String means, long temporary_time, float ef, long note) {
        this.word = word;
        this.image = image;
        this.means = means;
        this.temporary_time = temporary_time;
        this.ef = ef;
        this.note = note;
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

    public long getTemporary_time() {
        return temporary_time;
    }

    public void setTemporary_time(long temporary_time) {
        this.temporary_time = temporary_time;
    }

    public float getEf() {
        return ef;
    }

    public void setEf(float ef) {
        this.ef = ef;
    }

    public long getNote() {
        return note;
    }

    public void setNote(long note) {
        this.note = note;
    }
}
