package com.example.learn_english_smart.Model;

import java.util.List;

public class APIResponse {
    String word = "";
    List<phonetics> phonetics = null;
    List<meanings> meanings = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<com.example.learn_english_smart.Model.phonetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<com.example.learn_english_smart.Model.phonetics> phonetics) {
        this.phonetics = phonetics;
    }

    public List<com.example.learn_english_smart.Model.meanings> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<com.example.learn_english_smart.Model.meanings> meanings) {
        this.meanings = meanings;
    }
}
