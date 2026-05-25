package com.wetried.remindly;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words_table")
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String word;
    private String translate;

    public Word(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }
}


