package com.tripolil2test;

import android.app.Application;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;

import java.io.IOException;

import java.io.InputStream;
import java.util.Locale;



/**
 * Created by BDUREAU on 24/06/2016.
 */
public class MainConfigApplication extends Application {



    private globalConfig AppConf=null;
    @Override
    public void onCreate() {

        super.onCreate();
        AppConf = new globalConfig();
    }

    public Configuration getAppLocal() {

        Locale locale=null;

        if(AppConf.getApplicationLanguage().equals("0")) {
            //locale = Locale.FRENCH;//new Locale("fr_FR");
            locale = Locale.ENGLISH;//new Locale("en_US");
        }
        else if(AppConf.getApplicationLanguage().equals("1")) {
            //locale = Locale.ENGLISH;//new Locale("en_US");
            locale = Locale.FRENCH;//new Locale("fr_FR");
        }
        else if(AppConf.getApplicationLanguage().equals("2"))  {
            locale =Locale.getDefault();
        }



        Configuration config = new Configuration();

        config.locale= locale;
        return config;

    }



    public globalConfig getAppConf() {
        return AppConf;
    }

    public void setAppConf(globalConfig value) {
        AppConf=value;
    }


    public class globalConfig {

        SharedPreferences appConfig =null;
        SharedPreferences.Editor edit = null;
        //application language
        private String applicationLanguage ="0";
        private String questionsLanguage ="0";
        //graph font size
        private String fontSize="10";

        public globalConfig ()
        {
            appConfig  = getSharedPreferences("TripoliTestCfg", MODE_PRIVATE);
            edit = appConfig.edit();
        }

        public void ResetDefaultConfig() {

            applicationLanguage ="0";
            questionsLanguage ="0";

            fontSize="10";

            edit.clear();
            edit.putString("AppLanguage","0");
            edit.putString("QuestLanguage", "0");

            edit.putString("FontSize", "10");

        }

        public void ReadConfig() {
            try {
                String appLang;
                appLang = appConfig.getString("AppLanguage", "");
                if (!appLang.equals(""))
                    setApplicationLanguage(appLang);

                //Application Units
                String questLang;
                questLang = appConfig.getString("QuestLanguage", "");
                if (!questLang.equals(""))
                    setQuestionLanguage(questLang);

                //Font size
                String fontSize;
                fontSize =appConfig.getString("FontSize","");
                if (!fontSize.equals(""))
                    setFontSize(fontSize);

            } catch (Exception e) {

            }
        }

        public void SaveConfig() {
            edit.putString("AppLanguage", getApplicationLanguage());
            edit.putString("QuestLanguage", getQuestionLanguage());
            edit.putString("FontSize", getFontSize());

        }
        public String getFontSize() {
            return fontSize;
        }

        public void setFontSize(String value){
            fontSize=value;
        }

        public String getApplicationLanguage() {

           return applicationLanguage;
        }

        public void setApplicationLanguage(String value) {

            applicationLanguage=value;
        }

        public String getQuestionLanguage() {

            return questionsLanguage;
        }

        public void setQuestionLanguage(String value) {

            questionsLanguage=value;
        }

        public int ConvertFont(int font) {
            return font+8;
        }
        public int ConvertColor(int col) {

            int mycolor=0;

            switch (col) {

                case 0:
                    mycolor = Color.BLACK;
                    break;

                case 1:
                    mycolor = Color.WHITE;
                    break;
                case 2:
                    mycolor = Color.MAGENTA;
                    break;
                case 3:
                    mycolor = Color.BLUE;
                    break;
                case 4:
                    mycolor = Color.YELLOW;
                    break;
                case 5:
                    mycolor = Color.GREEN;
                    break;
                case 6:
                    mycolor = Color.GRAY;
                    break;
                case 7:
                    mycolor = Color.CYAN;
                    break;
                case 8:
                    mycolor =  Color.DKGRAY;
                    break;
                case 9:
                    mycolor =  Color.LTGRAY;
                    break;
                case 10:
                    mycolor =  Color.RED;
                    break;
            }
            return mycolor;
        }
    }
}
