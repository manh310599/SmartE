package com.example.learn_english_smart.Model;

import java.util.List;

public class meanings {
    String partOfSpeech = "";

    List<definitions> definitions = null;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<com.example.learn_english_smart.Model.definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<com.example.learn_english_smart.Model.definitions> definitions) {
        this.definitions = definitions;
    }
}
