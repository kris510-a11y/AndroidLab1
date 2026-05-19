package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends BaseFragment {

    // Интерфейс для связи с Activity
    public interface OnButtonClickListener {
        void onOpenSecondFragment();
    }

    private EditText editTextInput;
    private TextView textViewResult;
    private Button buttonShow;
    private Button buttonClear;
    private OnButtonClickListener callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickListener) {
            callback = (OnButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Находим элементы разметки
        editTextInput = view.findViewById(R.id.editTextInput);
        textViewResult = view.findViewById(R.id.textViewResult);
        buttonShow = view.findViewById(R.id.buttonShow);
        buttonClear = view.findViewById(R.id.buttonClear);
        Button buttonOpenSecond = view.findViewById(R.id.buttonOpenSecond);
        Button buttonOpenActivity = view.findViewById(R.id.buttonOpenActivity);

        // Кнопка "Показать Toast"
        buttonShow.setOnClickListener(v -> {
            String text = editTextInput.getText().toString();
            if (text.isEmpty()) {
                showToast("Поле пустое!");
            } else {
                showToast("Вы ввели: " + text);
                textViewResult.setText("Результат: " + text);
            }
        });

        // Кнопка "Очистить поле"
        buttonClear.setOnClickListener(v -> {
            editTextInput.setText("");
            textViewResult.setText("Результат: ");
            showToast("Поле очищено");
        });

        // Кнопка "Открыть второй фрагмент"
        buttonOpenSecond.setOnClickListener(v -> {
            if (callback != null) {
                callback.onOpenSecondFragment();
            }
        });

        // Кнопка "Открыть SecondActivity"
        buttonOpenActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            startActivity(intent);
        });

        return view;
    }
}