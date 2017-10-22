package com.tripolil2test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {
    Button btnDismiss;
    MainConfigApplication myCfg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get config Application pointer
        myCfg = (MainConfigApplication) getApplication();
        myCfg.getAppConf().ReadConfig();
        //Check the local and force it if needed
        getApplicationContext().getResources().updateConfiguration(myCfg.getAppLocal(), null);
        setContentView(R.layout.activity_about);

        btnDismiss = (Button)findViewById(R.id.butDismiss);

        btnDismiss.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();      //exit the about activity
            }
        });
    }
}
