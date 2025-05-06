package com.android.mad.assignments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private EditText editText;
    private TextView resultTextView;
    private MyViewModel viewModel;

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Инициализация ViewModel
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // Инициализация UI элементов
        editText = findViewById(R.id.editText);
        resultTextView = findViewById(R.id.resultTextView);
        Button sendButton = findViewById(R.id.sendButton);
        Button openWebButton = findViewById(R.id.openWebButton);

        // Восстановление данных
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString("saved_text");
            editText.setText(savedText);
        } else if (viewModel.getText() != null) {
            editText.setText(viewModel.getText());
        }

        // Обработчик для перехода на SecondActivity
        sendButton.setOnClickListener(v -> {
            saveData(); // Сохраняем в ViewModel перед переходом

            // Передача данных в SecondActivity
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("text_key", editText.getText().toString());
            startActivityForResult(intent, REQUEST_CODE);

            // Открытие карты (если нужно)
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:55.751244,37.618423?z=16"));
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });

        // Обработчик для открытия веб-страницы
        openWebButton.setOnClickListener(v -> {
            Intent webIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com")
            );

            Intent chooser = Intent.createChooser(webIntent, "Выберите браузер");
            if (webIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            } else {
                Toast.makeText(
                        MainActivity.this,
                        "Не найдено приложение для открытия ссылок",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void saveData() {
        viewModel.setText(editText.getText().toString());
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("saved_text", editText.getText().toString());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Обработка изменения конфигурации
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra("result_key");
            resultTextView.setText(result);
        }
    }
}