package com.tripolil2test;

import android.content.res.Configuration;
import android.graphics.Color;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

/*
In this activity you should be able to choose the application languages and looks and feel
 */
public class AppConfigActivity extends AppCompatActivity {
    Button btnDismiss, btnSave, bdtDefault;
    private Spinner spAppLanguage,  spQuestLanguage,  spFontSize;

    MainConfigApplication myCfg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get config Application pointer
        myCfg = (MainConfigApplication) getApplication();
        //Check the local and force it if needed
        getApplicationContext().getResources().updateConfiguration(myCfg.getAppLocal(), null);

        setContentView(R.layout.activity_app_config);

        myCfg.getAppConf().ReadConfig();
        btnDismiss = (Button)findViewById(R.id.butDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();      //exit the application configuration activity
            }
        });

        btnSave = (Button)findViewById(R.id.butSave);
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //save the application configuration
                SaveConfig();

            }
        });

        bdtDefault= (Button)findViewById(R.id.butDefault);
        bdtDefault.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //restore the application default configuration
               RestoreToDefault();


            }
        });


        //Language
        spAppLanguage = (Spinner)findViewById(R.id.spinnerLanguage);
        String[] items = new String[]{ "English", "French","Spanish","Phone language"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spAppLanguage.setAdapter(adapter);


        //units
        spQuestLanguage = (Spinner)findViewById(R.id.spinnerQuestLanguage);
        String[] items2 = new String[]{ "English", "French","Spanish"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        spQuestLanguage.setAdapter(adapter2);

        //font size
        spFontSize = (Spinner)findViewById(R.id.spinnerFontSize);
        String[] itemsFontSize = new String[]{"8","9", "10", "11", "12","13",
                "14", "15", "16", "17", "18", "19", "20"};
        ArrayAdapter<String> adapterFontSize = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsFontSize);
        spFontSize.setAdapter(adapterFontSize);
        ReadConfig();
    }

    void ReadConfig() {
        myCfg.getAppConf().ReadConfig();
        spAppLanguage.setSelection(Integer.parseInt(myCfg.getAppConf().getApplicationLanguage()));
        spQuestLanguage.setSelection(Integer.parseInt(myCfg.getAppConf().getQuestionLanguage()));
        spFontSize.setSelection((Integer.parseInt(myCfg.getAppConf().getFontSize())-8));
    }

    void SaveConfig() {
        myCfg.getAppConf().setApplicationLanguage(""+spAppLanguage.getSelectedItemId()+"");
        myCfg.getAppConf().setQuestionLanguage(""+spQuestLanguage.getSelectedItemId()+"");
        myCfg.getAppConf().setFontSize(""+(spFontSize.getSelectedItemId()+8)+"");
        myCfg.getAppConf().SaveConfig();
        finish();
    }

    void RestoreToDefault() {
        myCfg.getAppConf().ResetDefaultConfig();
        spAppLanguage.setSelection(Integer.parseInt(myCfg.getAppConf().getApplicationLanguage()));
        spQuestLanguage.setSelection(Integer.parseInt(myCfg.getAppConf().getQuestionLanguage()));
        spFontSize.setSelection(Integer.parseInt(myCfg.getAppConf().getFontSize())-8);
    }

}
