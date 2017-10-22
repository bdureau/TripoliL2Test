package com.tripolil2test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TripoliL2Test extends AppCompatActivity {
    Button btnStartQuiz;
    Button btnExitQuiz;
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

        btnStartQuiz = (Button)findViewById(R.id.butStartQuiz);

        btnStartQuiz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //finish();      //exit the about activity
                Intent i= new Intent(TripoliL2Test.this, QuestionsActivity.class);
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
