package com.wetried.remindly;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordDao wordDao;
    // Сюда мы будем записывать текст поиска
    private MutableLiveData<String> searchQuery = new MutableLiveData<>("");
    // Этот список будет автоматически меняться при изменении searchQuery
    private LiveData<List<Word>> allWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordDao = WordDatabase.getInstance(application).wordDao();

        // Магия switchMap: как только меняется запрос, LiveData переключается на новый результат поиска
        allWords = Transformations.switchMap(searchQuery, query -> {
            if (query == null || query.isEmpty()) {
                return wordDao.getAllWords(); // Показываем всё, если поиск пустой
            } else {
                return wordDao.searchWords("%" + query + "%");
            }
        });
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }
}
