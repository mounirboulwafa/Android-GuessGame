package com.mounirboulwafa.android_guessgame;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private int maxTentatives = 10;
    private int score;
    private List<String> listHistorique = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOK = findViewById(R.id.buttonOK);
        editTextNumber = findViewById(R.id.editTextNumber);
        textViewScore = findViewById(R.id.textViewScore);
        textViewIndication = findViewById(R.id.textViewIndication);
        textViewTentative = findViewById(R.id.textViewTentative);
        progressBarTentative = findViewById(R.id.progressBarTentative);
        listViewHistorique = findViewById(R.id.listViewHistorique);

        Initialisation();

        model = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listHistorique);
        listViewHistorique.setAdapter(model);


        buttonOK.setOnClickListener((evt)-> {
            int number = Integer.parseInt(editTextNumber.getText().toString());

            if (number > secret){
                textViewIndication.setText(getString(R.string.Value_sup));

            } else if (number < secret){
                textViewIndication.setText(getString(R.string.Value_inf));

            } else if (number == secret){
                textViewIndication.setText(getString(R.string.Value_win));
                score = score + 10;
                textViewScore.setText(String.valueOf(score));

            } else{
                textViewIndication.setText("xxxxx");
            }
            listHistorique.add(counter + " -> " + number);
            model.notifyDataSetChanged();

            ++counter;
            textViewTentative.setText(String.valueOf(counter));
            progressBarTentative.setProgress(counter);

            if (counter > maxTentatives){
                replay();
            }

        });

    }

    private void replay() {
        Log.i("MyLog   :   ","Rejouer ..");
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Rejouer");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Initialisation();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.show();

    }

    private void Initialisation() {
        secret = (int) (1 + (Math.random()*100));
        counter = 0;
        textViewTentative.setText(String.valueOf(counter));
        progressBarTentative.setProgress(counter);
        progressBarTentative.setMax(maxTentatives);
        textViewScore.setText(String.valueOf(score));
    }
}
