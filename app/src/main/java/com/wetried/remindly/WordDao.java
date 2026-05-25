package com.wetried.remindly;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insert(Word word);

    @Delete
    void delete(Word word);

    @Query("SELECT * FROM words_table ORDER BY id DESC")
    LiveData<List<Word>> getAllWords();

    @Query("SELECT * FROM words_table WHERE word LIKE :searchQuery OR translate LIKE :searchQuery ORDER BY id DESC")
    LiveData<List<Word>> searchWords(String searchQuery);

    @Query("SELECT * FROM words_table")
    List<Word> getAllWordsSync();

}