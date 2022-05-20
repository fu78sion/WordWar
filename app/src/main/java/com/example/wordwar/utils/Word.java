package com.example.wordwar.utils;

import java.util.Objects;

public class Word {

    private String word;
    private String speak;
    private String exp;
    private boolean visible = false; //中文不可见

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSpeak() {
        return speak;
    }

    public void setSpeak(String speak) {
        this.speak = speak;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return visible == word1.visible && Objects.equals(word, word1.word) && Objects.equals(speak, word1.speak) && Objects.equals(exp, word1.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, speak, exp, visible);
    }
}
