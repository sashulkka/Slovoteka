package com.wetried.remindly;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.wetried.remindly.databinding.LibraryBinding;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    private LibraryBinding binding;
    private WordAdapter adapter;
    private WordViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.add.setOnClickListener(v -> {
            Intent intent = new Intent(LibraryActivity.this, CreateWordActivity.class);
            startActivity(intent);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        adapter = new WordAdapter();
        binding.recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.@NonNull ViewHolder viewHolder, RecyclerView.@NonNull ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.@NonNull ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                Word wordToDelete = adapter.getWordAt(position);

                WordDatabase.getInstance(LibraryActivity.this).wordDao().delete(wordToDelete);

                Snackbar.make(binding.getRoot(), "Слово удалено", Snackbar.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(binding.recyclerView);

        WordDatabase.getInstance(this).wordDao().getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.setWords(words);
            }
        });

        viewModel = new ViewModelProvider(this).get(WordViewModel.class);
        viewModel.getAllWords().observe(this, words -> {
            adapter.setWords(words);
        });

        binding.searchBadge.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

    }




    }

