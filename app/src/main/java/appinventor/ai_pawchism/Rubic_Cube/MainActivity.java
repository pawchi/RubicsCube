package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String startedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadLocale();
        SharedPreferences prefs = getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        startedLanguage = prefs.getString("My_Lang",""); //read the language in which the activity was created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

/*
        Button step1 = (Button) findViewById(R.id.step4_to_step1_button);
        step1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Step1.class));
            }
        });

        Button step2 = (Button) findViewById(R.id.step4_to_step2_button);
        step2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Step2.class));
            }
        });

        Button step3 = (Button) findViewById(R.id.step4_to_step3_button);
        step3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Step3.class));
            }
        });

        Button step4 = (Button) findViewById(R.id.step4_to_step4_button);
        step4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Step4.class));
            }
        });

        Button step5 = (Button) findViewById(R.id.step4_to_step5_button);
        step5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Step5.class));
            }
        });*/

        ImageView settingsImage = (ImageView) findViewById(R.id.step1_settings_imageview);
        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Settings.class));
            }
        });

        ForHomepage forHomepage = new ForHomepage();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.frame_layout_fragment_one, forHomepage, forHomepage.getTag())
                .commit();

        ForHomepage2 forHomepage2 = new ForHomepage2();
        FragmentManager manager2 = getSupportFragmentManager();
        manager2.beginTransaction()
                .replace(R.id.frame_layout_fragment_two, forHomepage2, forHomepage2.getTag())
                .commit();


    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        if(!language.equals(startedLanguage)){ //check weather language is changed
            recreate();
            startedLanguage = language;
        }
    }
}
