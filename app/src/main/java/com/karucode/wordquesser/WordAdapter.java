package com.karucode.wordquesser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder>{

    private static final String TAG = "WordAdapter";

    private HashMap<Integer, Word> list = WordQuesserUtilities.getInstance().getWordsAndDefinitions();

    private OnWordListener onWordListener;
//    private Context context;

    public WordAdapter(OnWordListener onWordListener) {
        this.onWordListener = onWordListener;
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item_layout, parent, false);
        WordHolder wordHolder = new WordHolder(view,  onWordListener);
        return wordHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder wordHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called");

        wordHolder.textViewWord.setText(list.get(i).getWord());
        wordHolder.textViewdDefinition.setText(list.get(i).getDefinition());
        wordHolder.textViewAttempts.setText(Integer.toString(list.get(i).getAttempts()));

        // here i can add an onclicklistener


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewWord;
        TextView textViewdDefinition;
        TextView textViewAttempts;
        OnWordListener onWordListener;

        public WordHolder(@NonNull View itemView, OnWordListener onWordListener) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.text_view_word);
            textViewAttempts = itemView.findViewById(R.id.text_view_attempts);
            textViewdDefinition = itemView.findViewById(R.id.text_view_definition);
            this.onWordListener = onWordListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        onWordListener.OnWordClick(getAdapterPosition());
        }
    }

    public interface OnWordListener{
        void OnWordClick(int position);

    }
}
