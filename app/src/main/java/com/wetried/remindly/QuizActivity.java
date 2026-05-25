package com.wetried.remindly;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.wetried.remindly.databinding.ActivityQuizBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private ActivityQuizBinding binding;
    private List<Word> quizList = new ArrayList<>();
    private int currentIndex = 0;
    private Word currentWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Thread(() -> {
            quizList = WordDatabase.getInstance(this).wordDao().getAllWordsSync();

            runOnUiThread(() -> {
                if (quizList != null && !quizList.isEmpty()) {
                    Collections.shuffle(quizList);
                    showNextWord();
                } else {

                    binding.translateWordText.setText("Библиотека пуста");
                    binding.answerInput.setEnabled(false);
                    binding.checkBtn.setEnabled(false);

                    showEmptyLibraryDialog();
                }
            });
        }).start();

        binding.checkBtn.setOnClickListener(v -> checkAnswer());

        binding.backBtn.setOnClickListener(v -> showExitDialog());
    }

    private void showEmptyLibraryDialog() {
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("В библиотеке нет слов")
                .setMessage("Чтобы начать тест, добавьте хотя бы одно слово в библиотеку.")
                .setCancelable(false)
                .setPositiveButton("Ок", (d, which) -> finish())
                .create();

        dialog.show();

        int color = getResources().getColor(R.color.blue);

        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(color);
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(color);
    }

    private void showExitDialog() {
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Выход из теста")
                .setMessage("Вы точно хотите выйти? Прогресс текущей тренировки будет потерян.")
                .setPositiveButton("Да, выйти", (d, which) -> finish())
                .setNegativeButton("Отмена", null)
                .create();

        dialog.show();

        int color = getResources().getColor(R.color.blue);

        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(color);
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(color);
    }

    private void animateColorChange() {
        int colorFrom = getResources().getColor(R.color.white);
        int colorTo = android.graphics.Color.parseColor("#8BC34A");

        android.animation.ValueAnimator colorAnimation = android.animation.ValueAnimator.ofObject(
                new android.animation.ArgbEvaluator(), colorFrom, colorTo);

        colorAnimation.setDuration(400);

        colorAnimation.addUpdateListener(animator ->
                binding.cardView.setCardBackgroundColor((int) animator.getAnimatedValue())
        );

        colorAnimation.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                android.animation.ValueAnimator colorBack = android.animation.ValueAnimator.ofObject(
                        new android.animation.ArgbEvaluator(), colorTo, colorFrom);
                colorBack.setDuration(600);
                colorBack.addUpdateListener(animator ->
                        binding.cardView.setCardBackgroundColor((int) animator.getAnimatedValue())
                );
                colorBack.start();
            }
        });

        colorAnimation.start();
    }

    private void showNextWord() {
        if (currentIndex < quizList.size()) {
            currentWord = quizList.get(currentIndex);
            binding.translateWordText.setText(currentWord.getTranslate());
            binding.answerInput.setText("");
            binding.wordsCounter.setText("Осталось: " + (quizList.size() - currentIndex));
        } else {
            Snackbar.make(binding.getRoot(), "Отлично! Все слова пройдены.", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.blue))
                    .setTextColor(Color.WHITE)
                    .show();
            new Handler().postDelayed(this::finish, 2500);
        }
    }

    private void checkAnswer() {
        String userAnswer = binding.answerInput.getText().toString().trim();
        String correctAnswer = currentWord.getWord().trim();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            animateColorChange();

            new Handler().postDelayed(() -> {
                currentIndex++;
                showNextWord();
            }, 600);

        } else {
            binding.answerInputLayout.setError("Неверно");
            new Handler().postDelayed(() -> binding.answerInputLayout.setError(null), 2000);
        }
    }
}

