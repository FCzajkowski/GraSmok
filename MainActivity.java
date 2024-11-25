package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private Button button;
    private TextView textView;
    private int difficultyLevel = 0; // 0: Not selected, 1: Easy, 2: Hard
    private boolean easyCompleted = false; // Tracks if the user completed Easy level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView1);

        selectDifficulty();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame();
            }
        });
    }

    private void selectDifficulty() {
        new AlertDialog.Builder(this)
                .setTitle("Wybierz poziom trudności")
                .setItems(new CharSequence[]{"Łatwy (1-10)", "Trudny (1-100)"}, (dialog, which) -> {
                    if (which == 0) {
                        difficultyLevel = 1; // Easy
                        easyCompleted = false;
                        textView.setText("Wybrano: Łatwy");
                    } else if (which == 1) {
                        if (easyCompleted) {
                            difficultyLevel = 2; // Hard
                            textView.setText("Wybrano: Trudny");
                        } else {
                            showDialog("Musisz ukończyć poziom Łatwy przed dostępem do Trudnego!");
                            selectDifficulty();
                        }
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void playGame() {
        if (difficultyLevel == 0) {
            showDialog("Proszę wybrać poziom trudności!");
            return;
        }

        String input = editTextNumber.getText().toString();

        // Validate input
        if (input.isEmpty()) {
            showDialog("Proszę wpisz liczbę *-* !");
            return;
        }

        int userNumber;
        try {
            userNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showDialog("Zła liczba >:C!");
            return;
        }

        // Set range based on difficulty
        int range = difficultyLevel == 1 ? 10 : 100;
        Random random = new Random();
        int randomNumber = random.nextInt(range) + 1;

        // Compare numbers
        if (userNumber == randomNumber) {
            if (difficultyLevel == 1) {
                easyCompleted = true; // Mark Easy as completed
            }
            showDialog("Wygrałeś (☞ﾟヮﾟ)☞ ");
        } else {
            showDialog("Przegrałeś (┬┬﹏┬┬)! Poprawną odpowiedzią jest: " + randomNumber);
        }
    }

    private void showDialog(String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Wynik:")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(false)
                .show();
    }
}
