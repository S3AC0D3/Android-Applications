// Name: Werner Ordonez
// Date: 11/14/19
// File: MainActivity.java

package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    TextView questionLabel, questionCountLabel, scoreLabel;
    EditText answerEdt;
    Button submitButton;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArrayList;

    int currentPosition = 0;
    int numberOfCorrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GUI stuff
        questionCountLabel = findViewById(R.id.noQuestion);
        questionLabel = findViewById(R.id.question);
        scoreLabel = findViewById(R.id.score);
        answerEdt = findViewById(R.id.answer);
        submitButton = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress);

        // question list
        questionModelArrayList = new ArrayList<>();

        setUpQuestion();
        setData();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer();

            }
        });

    }

    public void checkAnswer(){
        String answerString  = answerEdt.getText().toString().trim();

        // if answer is correct show correct pop up and increment score and question #
        if(answerString.equalsIgnoreCase(questionModelArrayList.get(currentPosition).getAnswer())){
            numberOfCorrectAnswer ++;

            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Good job!")
                    .setContentText("Right Asswer")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            currentPosition ++;

                            setData();
                            answerEdt.setText("");
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();


        }
        // otherwise show incorrect popup with correct answer and just increment question #
        else {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Wrong Answer")
                    .setContentText("The right answer is : "+questionModelArrayList.get(currentPosition).getAnswer())
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();

                            currentPosition ++;

                            setData();
                            answerEdt.setText("");
                        }
                    })
                    .show();
        }

        // position and progress bar
        int x = ((currentPosition+1) * 100) / questionModelArrayList.size();
        progressBar.setProgress(x);

    }

    // list of questions and answers
    public void setUpQuestion(){
        questionModelArrayList.add(new QuestionModel(  "What is 1+2 ? ",  "3"));
        questionModelArrayList.add(new QuestionModel(  "What is 12/2 ? ",  "6"));
        questionModelArrayList.add(new QuestionModel(  "What is 9-7 ? ",  "2"));
        questionModelArrayList.add(new QuestionModel(  "What is 3*6 ? ",  "18"));
        questionModelArrayList.add(new QuestionModel(  "What is 7+7 ? ",  "14"));
    }

    // data for question number and score
    public void setData(){
        questionLabel.setText(questionModelArrayList.get(currentPosition).getQuestionString());
        questionCountLabel.setText("Question No : " + (currentPosition+1));
        scoreLabel.setText("Score : " + numberOfCorrectAnswer +"/" + questionModelArrayList.size());
    }
}
