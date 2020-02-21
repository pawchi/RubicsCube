package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class SelectLanguage extends AppCompatActivity {

    RadioGroup languageRadioGroup;
    RadioButton radioButton;
    String radioButtonResult;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //loadLocale();
        //SharedPreferences prefs = getSharedPreferences("Activity_Settings",Activity.MODE_PRIVATE);
        //language = prefs.getString("My_Lang","");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        languageRadioGroup = (RadioGroup) findViewById(R.id.language_radioGroup);
        Button languageOkButton = (Button) findViewById(R.id.button_language_ok);
        Button languageCancelButton = (Button) findViewById(R.id.button_language_cancel);
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        //Set screen in %: (int)(width*.9) = 90%

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .7), (int) (height * .9));
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        languageCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        languageOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonResult != (null)) {
                    //setLanguageForApp(radioButtonResult);
                    saveLocale(radioButtonResult);
                    //recreate();
                    finish();
                } else {
                    radioButtonResult = "";
                    finish();
                }
            }
        });
    }

    public void rbSelLanguageOnClick(View view) {

        int languageCheckRadioGroup = languageRadioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(languageCheckRadioGroup);

        switch (radioButton.getId()) {
            case R.id.language_en:
                radioButtonResult = "en";
                break;
            case R.id.language_de:
                radioButtonResult = "de";
                break;
            case R.id.language_pl:
                //radioButtonResult = "pl";
                radioButtonResult = "pl_PL";
                break;
            case R.id.language_es:
                radioButtonResult = "es";
                break;
            case R.id.language_fr:
                radioButtonResult = "fr";
                break;
            case R.id.language_pt:
                radioButtonResult = "pt";
                break;
            case R.id.language_ru:
                radioButtonResult = "ru";
                break;
            case R.id.language_zh:
                radioButtonResult = "zh";
                break;
            case R.id.language_vi:
                radioButtonResult = "vi";
                break;
            case R.id.language_cz:
                radioButtonResult = "cs";
                break;
            case R.id.language_it:
                radioButtonResult = "it";
                break;
            case R.id.language_tur:
                radioButtonResult = "tr";
                break;
            case R.id.language_taj:
                radioButtonResult = "th";
                break;
            case R.id.language_hg:
                radioButtonResult = "hu";
                break;
            case R.id.language_rum:
                radioButtonResult = "ro";
                break;
            case R.id.language_jap:
                radioButtonResult = "ja";
                break;
            case R.id.language_grec:
                radioButtonResult = "el";
                break;
            case R.id.language_hindi:
                radioButtonResult = "hi";
                break;
            case R.id.language_bengal:
                radioButtonResult = "bn";
                break;
            case R.id.language_slovak:
                radioButtonResult = "sk";
                break;
        }
    }

    private void saveLocale(String languageCode) {

        SharedPreferences.Editor editor = getSharedPreferences("Activity_Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", languageCode);
        editor.commit();
    }
}
