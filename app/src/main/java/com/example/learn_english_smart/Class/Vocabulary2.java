package com.example.learn_english_smart.Class;


import java.util.HashMap;
import java.util.Map;

public class Vocabulary2 {


    String word;
    String image;
    String means;
    long temporary_time ;
    float ef ;
    long note;
    long repetitions;
    long interval;

    public Vocabulary2() {
    }

    public Vocabulary2(String word, String image, String means, long temporary_time, float ef, long note, long repetitions, long interval) {
        this.word = word;
        this.image = image;
        this.means = means;
        this.temporary_time = temporary_time;
        this.ef = ef;
        this.note = note;
        this.repetitions = repetitions;
        this.interval = interval;
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

    public long getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(long repetitions) {
        this.repetitions = repetitions;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public Map<String,Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("repetitions",repetitions);
        return  result;
    }

    public Map<String,Object> toMap1() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("interval",interval);
        return  result;
    }

    public Map<String,Object> toMap2() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("temporary_time",temporary_time);
        return  result;
    }

    public Map<String,Object> toMap3() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("ef",ef);
        return  result;
    }

}
