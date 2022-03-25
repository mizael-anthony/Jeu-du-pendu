package com.mizael.jeudupendu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mizael.jeudupendu.tools.CustomPopup;
import com.mizael.jeudupendu.tools.Popupable;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Popupable {
    private TextView guessWord, tryCounter, scoreCounter, tryLimite;
    private EditText inputWord;
    private Button valid, guess, generate;
    private Word word;
    private int counter, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initComponents();
        this.counter = 0;
        this.score = 0;

        this.guessWord.setText(this.word.hide());

        this.tryCounter.setText(
                new String(getResources().getString(R.string.try_counter) + " " + this.counter));
        this.scoreCounter.setText(
                new String(getResources().getString(R.string.score_counter) + " " + this.score));
        this.tryLimite.setText(
                new String(getResources().getString(R.string.try_limite) + " " + this.word.getWord().length()));


        this.valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter >= word.getWord().length()){
                    Toast.makeText(MainActivity.this,
                            getResources().getString(R.string.pendu),
                            Toast.LENGTH_SHORT).show();
                    generate.performClick();
                }

                else{
                    counter++;
                    tryCounter.setText(
                            new String(getResources().getString(R.string.try_counter) +  " " + counter));

                    try {
                        String firstCharacter = inputWord.getText().toString();

                        if(firstCharacter.length() != 1){
                            throw new Exception();
                        }

                        else if(word.isContent(firstCharacter.charAt(0))){
                            String wordBefore = guessWord.getText().toString();
                            guessWord.setText(word.show(firstCharacter.charAt(0), wordBefore));

                            String wordAfter = guessWord.getText().toString();
                            if(wordAfter.equals(word.getWord())){
                                score++;
                                scoreCounter.setText(
                                        new String(getResources().getString(R.string.score_counter) +  " " + score));

                                Toast.makeText(MainActivity.this,
                                        "Bravo vous avez trouvé le mot !",
                                        Toast.LENGTH_SHORT).show();

                                generate.performClick();
                            }

                        }
                        else {
                            throw new Exception();
                        }

                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this,
                                getResources().getString(R.string.introuvable),
                                Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });


        this.guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputWord.getText().toString().equals(word.getWord())){
                    score++;

                    Toast.makeText(MainActivity.this,
                            getResources().getString(R.string.uncoup),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    score = (score == 0) ? 0:score - 1;

                    Toast.makeText(MainActivity.this,
                            getResources().getString(R.string.rater),
                            Toast.LENGTH_SHORT).show();
                }
                scoreCounter.setText(
                        new String(getResources().getString(R.string.score_counter) + " " + score));
                generate.performClick();
            }
        });

        this.generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
                word.generate();
                guessWord.setText(word.hide());
                inputWord.setText("");
                inputWord.requestFocus();
                counter = 0;
                tryCounter.setText(
                        new String(getResources().getString(R.string.try_counter) + " " + counter));
                tryLimite.setText(
                        new String(getResources().getString(R.string.try_limite) + " " + word.getWord().length()));
            }
        });



    }

    public void initComponents(){
        this.guessWord = findViewById(R.id.textview_guess_word);
        this.tryCounter = findViewById(R.id.textview_try_counter);
        this.scoreCounter = findViewById(R.id.textview_score_counter);
        this.tryLimite = findViewById(R.id.textview_try_limite);

        this.inputWord = findViewById(R.id.edittext_input_word);

        this.valid = findViewById(R.id.button_valid);
        this.guess = findViewById(R.id.button_guess);
        this.generate = findViewById(R.id.button_generate);

        try {
            this.word = new Word(getAssets().open("mots.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void popup() {
        CustomPopup customPopup = new CustomPopup(MainActivity.this);
        customPopup.getTitle().setText(R.string.guessed_word);
        customPopup.getContent().setText(word.getWord());
        customPopup.getNegative().setVisibility(View.GONE);
        customPopup.getPositive().setBackgroundColor(getResources().getColor(R.color.black));
        customPopup.getPositive().setOnClickListener(v1 -> customPopup.dismiss());
        customPopup.build();

    }
}