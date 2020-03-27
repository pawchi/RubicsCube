package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import java.util.Locale;

public class Activity_2x2_Step3 extends AppCompatActivity implements View.OnClickListener {
    InterstitialAd interstitialAd;
    String startedLanguage;
    ImageView showMovesCrossCase1, showMovesCrossCase2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        loadLocale();
        SharedPreferences prefs = getSharedPreferences("Activity_Settings", Activity.MODE_PRIVATE);
        startedLanguage = prefs.getString("My_Lang",""); //read the language in which the activity was created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        //Full screen ads
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-9832953507407797/7879023431");
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);

        final LinearLayout layoutForInjectInto = (LinearLayout) findViewById(R.id.inflate_content_vert_layout);

        getLayoutInflater().inflate(R.layout.fragment_up_bar, layoutForInjectInto);
        getLayoutInflater().inflate(R.layout.layout_buttons_for_2x2, layoutForInjectInto);
        getLayoutInflater().inflate(R.layout.content_2x2_step3, layoutForInjectInto);

        final Button stepButton1 = (Button) findViewById(R.id.button1_2x2);
        Button stepButton2 = (Button) findViewById(R.id.button2_2x2);
        Button stepButton3 = (Button) findViewById(R.id.button3_2x2);
        ImageView settingsImage = (ImageView) findViewById(R.id.settings_imageview);
        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        showMovesCrossCase1 = (ImageView) findViewById(R.id.dwa_step3_case_1_cross_show_moves);
        showMovesCrossCase2 = (ImageView) findViewById(R.id.dwa_step3_case_2_cross_show_moves);

        backButton.setOnClickListener(this);
        stepButton1.setOnClickListener(this);
        stepButton2.setOnClickListener(this);
        stepButton3.setOnClickListener(this);
        settingsImage.setOnClickListener(this);

        stepButton3.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        stepButton3.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_menu_text_active)));

        LinearLayout case_1_moves_layout = (LinearLayout) findViewById(R.id.dwa_step3_case1_moves);
        LinearLayout case_2_moves_layout = (LinearLayout) findViewById(R.id.dwa_step3_case2_moves);

        ShowMoves_2x2 showMoves_2x2 = new ShowMoves_2x2();
        final LinearLayout case1 = showMoves_2x2.movesAllCases(this,"r","u","u","r'",  "u'","r","u","u",  "l'","u","r'","u'",  "l","v","v","v");
        final LinearLayout case2 = showMoves_2x2.movesAllCases(this,"r","u'","r'","u'",  "f","f","u'","r",  "u","r'","u","f",  "f","v","v","v");

        case_1_moves_layout.addView(case1);
        case_2_moves_layout.addView(case2);
        case1.setVisibility(View.GONE);
        case2.setVisibility(View.GONE);

        showMovesCrossCase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (case1.getVisibility()==View.GONE){
                    case1.setVisibility(View.VISIBLE);
                    showMovesCrossCase1.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                } else {
                    case1.setVisibility(View.GONE);
                    showMovesCrossCase1.setImageResource(android.R.drawable.ic_menu_add);
                }
            }
        });

        showMovesCrossCase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (case2.getVisibility()==View.GONE){
                    case2.setVisibility(View.VISIBLE);
                    showMovesCrossCase2.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                } else {
                    case2.setVisibility(View.GONE);
                    showMovesCrossCase2.setImageResource(android.R.drawable.ic_menu_add);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1_2x2:
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
                }
                break;
            case R.id.button2_2x2:
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                    interstitialAd.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            startActivity(new Intent(getApplicationContext(), Activity_2x2_Step2.class));
                        }
                    });
                } else {
                    startActivity(new Intent(getApplicationContext(), Activity_2x2_Step2.class));
                }
                break;
            case R.id.button3_2x2:
                startActivity(new Intent(getApplicationContext(), Activity_2x2_Step3.class));
                break;
            case R.id.settings_imageview:
                startActivity(new Intent(getApplicationContext(), Activity_Settings.class));
                break;
            case R.id.back_button:
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                    interstitialAd.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            startActivity(new Intent(getApplicationContext(), Activity_StartPage.class));
                        }
                    });
                } else {
                    startActivity(new Intent(getApplicationContext(), Activity_StartPage.class));
                }
                break;
        }
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
