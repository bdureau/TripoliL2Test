package com.tripolil2test;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class TripoliL2Test extends AppCompatActivity {
    Button btnStartQuiz;
    Button btnExitQuiz;
    RadioButton radioButtonQuiz1, radioButtonQuiz2;
    MainConfigApplication myCfg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get config Application pointer
        myCfg = (MainConfigApplication) getApplication();
        myCfg.getAppConf().ReadConfig();
        //Check the local and force it if needed
        getApplicationContext().getResources().updateConfiguration(myCfg.getAppLocal(), null);
        setContentView(R.layout.activity_tripoli_l2_test);

        radioButtonQuiz1 = (RadioButton) findViewById(R.id.radioButtonQuiz1);
        radioButtonQuiz2 = (RadioButton) findViewById(R.id.radioButtonQuiz2);
        btnStartQuiz = (Button)findViewById(R.id.butStartQuiz);

        btnStartQuiz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String questions= "";
                if(radioButtonQuiz1.isChecked()){
                    questions = "questions";
                }
                else if(radioButtonQuiz2.isChecked()){
                    questions = "questions_m1";
                } else {
                    questions = "questions";
                }
                if(myCfg.getAppConf().getQuestionLanguage().equals("0")) {
                    questions=questions +".xml";
                }
                else if(myCfg.getAppConf().getQuestionLanguage().equals("1")) {
                    questions=questions +"_fr.xml";
                }
                else if(myCfg.getAppConf().getQuestionLanguage().equals("2"))  {
                    questions=questions +"_es.xml";
                }
                else {
                    questions=questions +".xml";
                }
                Intent i= new Intent(TripoliL2Test.this, QuestionsActivity.class);
                i.putExtra("quiz",questions);
                startActivity(i);
            }
        });
        btnExitQuiz = (Button)findViewById(R.id.butExitQuiz);
        btnExitQuiz.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View v)
        {
            finish();      //exit the about activity
        }
    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //open application settings screen
        if (id == R.id.action_settings) {
           Intent i= new Intent(TripoliL2Test.this, AppConfigActivity.class);
            startActivity(i);
            return true;
        }
        //open help screen
        if (id == R.id.action_help) {
            Intent i= new Intent(TripoliL2Test.this, HelpActivity.class);
            startActivity(i);
            return true;
        }
        //open about screen
        if (id == R.id.action_about) {
            Intent i= new Intent(TripoliL2Test.this, AboutActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
