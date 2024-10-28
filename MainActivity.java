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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame();
            }
        });
    }

    private void playGame() {
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

        // Generate a random number between 1 and 100
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;

        // Compare numbers
        if (userNumber == randomNumber) {
            showDialog("Wygrałeś (☞ﾟヮﾟ)☞ ");
        } else {
            showDialog("Przegrałeś (┬┬﹏┬┬)! Poprawną odpowiedzią jest:" + randomNumber);
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
