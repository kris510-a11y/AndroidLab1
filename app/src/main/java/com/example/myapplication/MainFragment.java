package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

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

        // Обработчик кнопки "Показать Toast"
        buttonShow.setOnClickListener(v -> {
            String text = editTextInput.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(getActivity(), "Поле пустое!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Вы ввели: " + text, Toast.LENGTH_SHORT).show();
                textViewResult.setText("Результат: " + text);
            }
        });

        // Обработчик кнопки "Очистить поле"
        buttonClear.setOnClickListener(v -> {
            editTextInput.setText("");
            textViewResult.setText("Результат: ");
            Toast.makeText(getActivity(), "Поле очищено", Toast.LENGTH_SHORT).show();
        });

        // Обработчик кнопки "Открыть второй фрагмент"
        buttonOpenSecond.setOnClickListener(v -> {
            if (callback != null) {
                callback.onOpenSecondFragment();
            }
        });

        return view;
    }
}