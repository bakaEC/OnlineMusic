package com.u21.a0903_onlinemusic.entity;

public class Word {
    private String word;
    private String explain;

    public Word(String word, String explain) {
        this.word = word;
        this.explain = explain;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
