package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
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
        loadLocale();
        SharedPreferences prefs = getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        language = prefs.getString("My_Lang","");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        languageRadioGroup = (RadioGroup) findViewById(R.id.language_radioGroup);
        Button languageOkButton = (Button) findViewById(R.id.button_language_ok);
        Button languageCancelButton = (Button) findViewById(R.id.button_language_cancel);

        //Set screen in %: (int)(width*.9) = 90%
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .7), (int) (height * .8));

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
                    setLanguageForApp(radioButtonResult);
                    //recreate();
                    finish();
                    startActivity(getIntent());
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
                radioButtonResult = "pl";
                //radioButtonResult = "pl_PL";
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

        }
    }

    private void setLanguageForApp(String languageCode) {
        Locale locale = new Locale(languageCode);
        //Locale.setDefault(locale);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        saveLocale(languageCode);
    }

    private void saveLocale(String languageCode) {

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", languageCode);
        editor.commit();
    }

    //load language saved in shared preferences
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLanguageForApp(language);
    }
}
