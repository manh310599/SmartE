package com.example.learn_english_smart.Model;

import java.util.List;

public class definitions {
    String definition = "";
    String example = "";
    ////////////////////từ trái nghĩa////////////////
    List<String> synonyms = null;
    ////////////////từ đồng nghĩa//////////////////
    List<String> antonyms = null;

    public List<String> getSynonyn() {
        return synonyms;
    }

    public void setSynonyn(List<String> synonyn) {
        this.synonyms = synonyn;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
