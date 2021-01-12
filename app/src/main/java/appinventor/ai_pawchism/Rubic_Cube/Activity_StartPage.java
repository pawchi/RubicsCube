package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import java.util.Locale;

public class Activity_StartPage extends AppCompatActivity {
    String startedLanguage;
    InterstitialAd interstitialAd;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(getApplicationContext(), Activity_StartPage.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadLocale();
        SharedPreferences prefs = getSharedPreferences("Activity_Settings",Activity.MODE_PRIVATE);
        startedLanguage = prefs.getString("My_Lang",""); //read the language in which the activity was created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        //PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        //publisherAdView.loadAd(adRequest);

        //Full screen ads
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.Ad_full_screen));
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);

        LinearLayout layoutToInjectXml = (LinearLayout) findViewById(R.id.frame_up_bar);
        getLayoutInflater().inflate(R.layout.fragment_up_bar, layoutToInjectXml);

        LinearLayout layoutForInjectionStartXml = (LinearLayout) findViewById(R.id.frame_layout_fragment_two);
        getLayoutInflater().inflate(R.layout.activity_main_4buttons, layoutForInjectionStartXml);

        LinearLayout layout_3x3 = (LinearLayout) findViewById(R.id.layout_button_3x3);
        LinearLayout layoutGeninfo = (LinearLayout) findViewById(R.id.layout_button_general_info);
        LinearLayout layout_2x2 = (LinearLayout) findViewById(R.id.layout_button_2x2);
        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        LinearLayout layoutTimer = (LinearLayout) findViewById(R.id.layout_button_timer);

        layout_3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                    interstitialAd.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            startActivity(new Intent(Activity_StartPage.this, Activity_3x3_Step1.class));
                        }
                    });
                } else {
                    startActivity(new Intent(Activity_StartPage.this, Activity_3x3_Step1.class));
                    finish();
                }
            }
        });

        layout_2x2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                    interstitialAd.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            startActivity(new Intent(getApplicationContext(), Activity_2x2_Step1.class));
                        }
                    });
                } else {
                    startActivity(new Intent(getApplicationContext(), Activity_2x2_Step1.class));
                    finish();
                }
            }
        });

        layoutGeninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                    interstitialAd.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            startActivity(new Intent(Activity_StartPage.this, Activity_Notation.class));
                        }
                    });
                } else {
                    startActivity(new Intent(Activity_StartPage.this, Activity_Notation.class));
                    finish();
                }
            }
        });

        layoutTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_timer.class));
                finish();
            }
        });

        ImageView settingsImage = (ImageView) findViewById(R.id.settings_imageview);
        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_StartPage.this, Activity_Settings.class));
            }
        });

    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Activity_Settings", Activity.MODE_PRIVATE);
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
        SharedPreferences prefs = getSharedPreferences("Activity_Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        if(!language.equals(startedLanguage)){ //check weather language is changed
            recreate();
            startedLanguage = language;
        }
    }
}
