package com.wetried.remindly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder> {
    private List<Word> words = new ArrayList<>();

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter, parent, false);
        return new WordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        Word currentWord = words.get(position);
        holder.wordText.setText(currentWord.getWord());
        holder.translateText.setText(currentWord.getTranslate());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    public Word getWordAt(int position) {
        return words.get(position);
    }

    class WordHolder extends RecyclerView.ViewHolder {
        private final TextView wordText;
        private final TextView translateText;

        public WordHolder(@NonNull View itemView) {
            super(itemView);
            wordText = itemView.findViewById(R.id.wordChapter);
            translateText = itemView.findViewById(R.id.translateChapter);
        }
    }
}
