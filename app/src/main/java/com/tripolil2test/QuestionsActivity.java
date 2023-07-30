package com.tripolil2test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import java.util.Random;


public class QuestionsActivity extends AppCompatActivity {
    Button btnA;
    Button btnB;
    Button btnC;


    TextView test;
    TextView rep_A, rep_B, rep_C;
    TextView oneOffX;
    String sol,answer ="";
    int d = 0;
    int PassedAns = 0;
    Random gen;
    // XML node names
    static final String NODE_EXAM = "exam";
    static final String NODE_QUESTION = "question";
    static final String NODE_TEST = "test";
    static final String NODE_A = "A";
    static final String NODE_B = "B";
    static final String NODE_C = "C";
    static final String NODE_SOL = "sol";
    static final String NODE_ANS = "ans";

    private AlertDialog.Builder valQuestionBuilder;// =new AlertDialog.Builder(this);
    private AlertDialog valQuestionAlert;
    XMLDOMParser parser;
    NodeList nodeList;
    private Element e;

    private int already_done[] ;

    private int count_done = 0;
    private int maxNode=0;
    private boolean done = false;
    private boolean endQuiz= false;
    MainConfigApplication myCfg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //get config Application pointer
        myCfg = (MainConfigApplication) getApplication();
        myCfg.getAppConf().ReadConfig();
        //Check the local and force it if needed
        getApplicationContext().getResources().updateConfiguration(myCfg.getAppLocal(), null);

        setContentView(R.layout.activity_questions);
        btnA = (Button)findViewById(R.id.butA);

        btnA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidateQuestion("A");
            }
        });

        btnB = (Button)findViewById(R.id.butB);
        btnB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidateQuestion("B");
            }
        });

        btnC = (Button)findViewById(R.id.butC);
        btnC.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidateQuestion("C");
            }
        });

        test = (TextView) findViewById(R.id.textViewQuestion);
        rep_A= (TextView) findViewById(R.id.textViewAnswerA);
        rep_B= (TextView) findViewById(R.id.textViewAnswerB);
        rep_C= (TextView) findViewById(R.id.textViewAnswerC);
        oneOffX = (TextView) findViewById(R.id.textViewOneOffX);
        StartQuiz();
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

        if (id == R.id.action_restart) {
            StartQuiz();
            return true;
        }
        //open application settings screen
        if (id == R.id.action_settings) {
            Intent i= new Intent(QuestionsActivity.this, AppConfigActivity.class);
            startActivity(i);
            return true;
        }
        //open help screen
        if (id == R.id.action_help) {
            Intent i= new Intent(QuestionsActivity.this, HelpActivity.class);
            startActivity(i);
            return true;
        }
        //open about screen
        if (id == R.id.action_about) {
            Intent i= new Intent(QuestionsActivity.this, AboutActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(valQuestionAlert != null)
        {
            valQuestionAlert.dismiss();
        }
    }
    private void StartQuiz(){
        parser = new XMLDOMParser();
        valQuestionBuilder =new AlertDialog.Builder(this);
        valQuestionBuilder.setCancelable(false);
        valQuestionBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                Quiz();
                dialog.dismiss();
            }
        });
        //on initialise
        d = 0;
        endQuiz= false;
        PassedAns = 0;
        count_done=0;
        AssetManager manager = getAssets();
        InputStream stream;
        already_done = new int[1000];

        gen = new Random();
        // prepare questions
        try {
            String questions;
            Intent myIntent = getIntent(); // gets the previously created intent
            questions = myIntent.getStringExtra("quiz");

            stream = manager.open(questions);
            Document doc = parser.getDocument(stream);

            // Get elements by name question
            nodeList = doc.getElementsByTagName(NODE_QUESTION);
            maxNode =nodeList.getLength();
                    Quiz();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void ValidateQuestion(String butName) {
       // Element
        e = (Element) nodeList.item(d-1);
        sol = parser.getValue(e, NODE_SOL);
        answer=parser.getValue(e, NODE_ANS);
        if (sol.equals(butName))
        {
            //Correct
            valQuestionBuilder.setMessage(answer);
            valQuestionBuilder.setTitle("Correct!!");
            PassedAns ++;
        }
        else
        {
            //InCorrect
            valQuestionBuilder.setMessage(Html.fromHtml("<font color='#FF0000'>"+ answer+ "</font>"));
            valQuestionBuilder.setTitle("Wrong!!");
        }
        valQuestionAlert = valQuestionBuilder.create();
        valQuestionAlert.show();
        if (count_done == maxNode)
            endQuiz=true;
    }
    private void Quiz() {

        if (count_done < maxNode) {
            while (!done) {
                done = true;
                d = gen.nextInt(maxNode)+1;

               for (int i = 0; i <= count_done; i++) {
                    if (already_done[i] == d) {
                        done = false;
                    }
                }

                if (done) {
                    already_done[count_done] = d;
                    count_done = count_done + 1;
                }
            }

            done = false;

            //Element
            e = (Element) nodeList.item(d-1);
            test.setText(parser.getValue(e, NODE_TEST));
            rep_A.setText(parser.getValue(e, NODE_A));
            rep_B.setText(parser.getValue(e, NODE_B));
            rep_C.setText(parser.getValue(e, NODE_C));
            oneOffX.setText((count_done) + "/" + maxNode);

        }else if (endQuiz){
            count_done=0;
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //
            builder.setCancelable(false);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int id) {
                    StartQuiz();
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int id) {
                    dialog.cancel();
                }
            });
            if (( (100* PassedAns) / nodeList.getLength())> 90 )
            {
                //Correct
                builder.setMessage("Your score is:" + ( 100* PassedAns / nodeList.getLength()));
                builder.setTitle("You have passed");
            }
            else
            {
                //InCorrect
                builder.setMessage(Html.fromHtml("<font color='#FF0000'>"+ "Your score is:" + ( 100* PassedAns / nodeList.getLength())+ "</font>"));
                builder.setTitle("You have failed!! \n Do you want to try again?");
            }
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
