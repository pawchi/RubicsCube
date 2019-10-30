package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

        LinearLayout layoutToInjectXml = (LinearLayout) findViewById(R.id.frame_up_bar);
        getLayoutInflater().inflate(R.layout.fragment_up_bar, layoutToInjectXml);

        LinearLayout layoutForInjectionStartXml = (LinearLayout) findViewById(R.id.frame_layout_fragment_two);
        getLayoutInflater().inflate(R.layout.activity_main_4buttons, layoutForInjectionStartXml);

        LinearLayout layout_3x3 = (LinearLayout) findViewById(R.id.layout_button_3x3);
        LinearLayout layoutGeninfo = (LinearLayout) findViewById(R.id.layout_button_general_info);
        LinearLayout layout_2x2 = (LinearLayout) findViewById(R.id.layout_button_2x2);
        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        LinearLayout layoutTimer = (LinearLayout) findViewById(R.id.layout_button_timer);

        //backButton.setVisibility(View.INVISIBLE);


        layout_3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Activity_3x3_Step1.class));
            }
        });

        layout_2x2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_2x2.class));
            }
        });

        layoutGeninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GeneralInfo.class));
            }
        });

        layoutTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_timer.class));
            }
        });





        ImageView settingsImage = (ImageView) findViewById(R.id.settings_imageview);
        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Settings.class));
            }
        });

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
