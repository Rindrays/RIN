package com.android.mad.assignments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView receivedTextView = findViewById(R.id.resultTextView);
        Button returnButton = findViewById(R.id.sendButton);

        // Получаем переданный текст
        String receivedText = getIntent().getStringExtra("text_key");
        receivedTextView.setText(receivedText);

        returnButton.setOnClickListener(v -> {
            // Создаем Intent для возврата данных
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result_key", "Abracadabra");

            // Устанавливаем результат и закрываем активность
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
