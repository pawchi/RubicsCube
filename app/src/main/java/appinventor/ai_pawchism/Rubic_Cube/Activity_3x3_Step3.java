package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.Locale;

public class Activity_3x3_Step3 extends AppCompatActivity implements View.OnClickListener {
    InterstitialAd interstitialAd;
    String startedLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        startedLanguage = prefs.getString("My_Lang",""); //read the language in which the activity was created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);


        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        final LinearLayout layoutForInjectInto = (LinearLayout) findViewById(R.id.inflate_content_vert_layout);
        LinearLayout upBar = (LinearLayout) findViewById(R.id.up_bar_for_all);

        getLayoutInflater().inflate(R.layout.fragment_up_bar, layoutForInjectInto);
        getLayoutInflater().inflate(R.layout.layout_buttons_for_3x3, layoutForInjectInto);
        getLayoutInflater().inflate(R.layout.content_3x3_step3, layoutForInjectInto);

        Button stepButton1 = (Button) findViewById(R.id.button1_3x3);
        Button stepButton2 = (Button) findViewById(R.id.button2_3x3);
        final Button stepButton3 = (Button) findViewById(R.id.button3_3x3);
        Button stepButton4 = (Button) findViewById(R.id.button4_3x3);
        Button stepButton5 = (Button) findViewById(R.id.button5_3x3);
        Button stepButton6 = (Button) findViewById(R.id.button6_3x3);
        Button stepButton7 = (Button) findViewById(R.id.button7_3x3);
        ImageView settingsImage = (ImageView) findViewById(R.id.settings_imageview);
        ImageView backButton = (ImageView) findViewById(R.id.back_button);

        backButton.setOnClickListener(this);
        stepButton1.setOnClickListener(this);
        stepButton2.setOnClickListener(this);
        stepButton3.setOnClickListener(this);
        stepButton4.setOnClickListener(this);
        stepButton5.setOnClickListener(this);
        stepButton6.setOnClickListener(this);
        stepButton7.setOnClickListener(this);
        settingsImage.setOnClickListener(this);

        stepButton3.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_menu_active)));
        stepButton3.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_menu_text_active)));
        final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view_buttons_3x3);

        hsv.post(new Runnable() {
            @Override
            public void run() {
                hsv.scrollTo(stepButton3.getLeft(), stepButton3.getTop());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1_3x3:
                startActivity(new Intent(getApplicationContext(), Activity_3x3_Step1.class));
                break;
            case R.id.button2_3x3:
                startActivity(new Intent(getApplicationContext(), Activity_3x3_Step2.class));
                break;
            case R.id.button3_3x3:
                startActivity(new Intent(getApplicationContext(), Activity_3x3_Step3.class));
                break;
            case R.id.button4_3x3:
                startActivity(new Intent(getApplicationContext(), Activity_3x3_Step4.class));
                break;
            case R.id.button5_3x3:
                startActivity(new Intent(getApplicationContext(), Activity_3x3_Step5.class));
                break;
            case R.id.button6_3x3:
                startActivity(new Intent(getApplicationContext(), Activity_3x3_Step6.class));
                break;
            case R.id.button7_3x3:
                startActivity(new Intent(getApplicationContext(), Activity_3x3_Step7.class));
                break;
            case R.id.settings_imageview:
                startActivity(new Intent(getApplicationContext(), Settings.class));
                break;
            case R.id.back_button:
                finish();
                break;
        }
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
