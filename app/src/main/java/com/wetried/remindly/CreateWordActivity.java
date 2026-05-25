package com.wetried.remindly;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.wetried.remindly.databinding.CreatewordBinding;

public class CreateWordActivity extends AppCompatActivity {
    private CreatewordBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreatewordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addWord.setOnClickListener(v -> {
            String word = binding.wordInput.getText().toString();
            String translate = binding.translateInput.getText().toString();
            if (word.isEmpty() || translate.isEmpty()) {
                Snackbar.make(binding.getRoot(), "Пустые поля", Snackbar.LENGTH_SHORT).show();
            }else {
                // Создаем объект слова
                Word newWord = new Word(word, translate);

                // Сохраняем в базу
                WordDatabase.getInstance(this).wordDao().insert(newWord);
                Log.d("MY_DB", "Слово сохранено: " + word);
                // Закрываем активити после сохранения
                finish();
            }
        });
    }
}
