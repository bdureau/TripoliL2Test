package com.tripolil2test;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import java.util.Locale;

public class HelpActivity extends AppCompatActivity {
Button btnDismiss;
    WebView webView;
    MainConfigApplication myCfg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get config Application pointer
        myCfg = (MainConfigApplication) getApplication();
        myCfg.getAppConf().ReadConfig();
        //Check the local and force it if needed
        getApplicationContext().getResources().updateConfiguration(myCfg.getAppLocal(), null);
        setContentView(R.layout.activity_help);
        webView = (WebView)findViewById(R.id.webView);

        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        //myBT.getAppLocal() == Locale.FRENCH

      // if ( myBT.getAppLocal().locale==Locale.FRENCH)
           webView.loadUrl("file:///android_asset/help_fr.html");
      // else
      //     webView.loadUrl("file:///android_asset/help.html");


        btnDismiss = (Button)findViewById(R.id.butClose);
        btnDismiss.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();      //exit the help activity
            }
        });
    }
    private class WebViewClient extends android.webkit.WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
