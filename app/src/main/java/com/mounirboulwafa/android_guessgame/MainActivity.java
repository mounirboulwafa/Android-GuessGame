package com.mounirboulwafa.android_guessgame;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonOK;
    private EditText editTextNumber;
    private TextView textViewIndication;
    private TextView textViewTentative;
    private TextView textViewScore;
    private ProgressBar progressBarTentative;
    private ListView listViewHistorique;
    private ArrayAdapter<String> model;
    private int secret;
    private int counter;
    private int Tentatives = 10;
    private int score;
    private List<String> listHistorique = new ArrayList<>();
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOK = findViewById(R.id.buttonOK);
        editTextNumber = findViewById(R.id.editTextNumber);
        textViewScore = findViewById(R.id.textViewScore);
        textViewIndication = findViewById(R.id.textViewIndication);
        textViewTentative = findViewById(R.id.textViewTentative);
        //progressBarTentative = findViewById(R.id.progressBarTentative);
        listViewHistorique = findViewById(R.id.listViewHistorique);

        model = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listHistorique);
        listViewHistorique.setAdapter(model);

        Initialisation();

        buttonOK.setOnClickListener((evt)-> {
            number = Integer.parseInt(editTextNumber.getText().toString());

            if (number > secret){
                textViewIndication.setText(getString(R.string.Value_sup));

            } else if (number < secret){
                textViewIndication.setText(getString(R.string.Value_inf));

            } else if (number == secret){
                textViewIndication.setText(getString(R.string.Value_win));
                score = score + 10;
                textViewScore.setText(String.valueOf(score));
                replay();

            } else{
                textViewIndication.setText("xxxxx");
            }
            listHistorique.add("Turn " + counter + " -> " + number);
            model.notifyDataSetChanged();                           //Notify the view that the data is changed

            --Tentatives;
            ++counter;
            textViewTentative.setText(String.valueOf(Tentatives));
            //progressBarTentative.setProgress(counter);

            if (Tentatives == 0){
                replay();
            }

        });

    }

    @SuppressLint("ResourceAsColor")
    private void replay() {
        Log.i("MyLog   :   ","Rejouer ..");
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        if (number == secret)
            alertDialog.setTitle("YOU WON !");
        else
            alertDialog.setTitle("YOU LOST !");

        alertDialog.setMessage(getString(R.string.rejouer));
        //alertDialog.getWindow().setLayout(1000, 1000);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Initialisation();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.quitter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.show();

        Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(R.color.colorPrimaryDark);

        Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

    }

    private void Initialisation() {
        secret = (int) (1 + (Math.random()*100));
        counter = 1;
        Tentatives = 10;
        textViewTentative.setText(String.valueOf(Tentatives));
        //progressBarTentative.setProgress(counter);
        //progressBarTentative.setMax(maxTentatives);
        textViewScore.setText(String.valueOf(score));

        listHistorique.clear();                                            // Clear the history list
        model.notifyDataSetChanged();

        textViewIndication.setText("");

    }
}
