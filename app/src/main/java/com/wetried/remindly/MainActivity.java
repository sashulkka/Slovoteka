package com.wetried.remindly;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.wetried.remindly.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.dictionaryWebBtn.setOnClickListener( v -> {
            startActivity(WebActivity.newIntent(this, "https://dictionary.cambridge.org/ru/"));
        });

        binding.libraryBtn.setOnClickListener(v -> {
            Intent intent =new Intent(MainActivity.this, LibraryActivity.class);
            startActivity(intent);
        });

        binding.startBtn.setOnClickListener( v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });
    }
}
