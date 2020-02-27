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

public class Activity_2x2_Step2 extends AppCompatActivity implements View.OnClickListener {
    InterstitialAd interstitialAd;
    String startedLanguage;
    ImageView showImage;
    ImageView step2Test;
    private static  int[] moves2x2 = {R.drawable._2x2_l,R.drawable._2x2_l_prim,R.drawable._2x2_r,R.drawable._2x2_r_prim,R.drawable._2x2_f,
            R.drawable._2x2_f_prim,R.drawable._2x2_b,R.drawable._2x2_b_prim,R.drawable._2x2_d,R.drawable._2x2_d_prim,R.drawable._2x2_u,R.drawable._2x2_u_prim};
    LinearLayout layoutToInflate;

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
        getLayoutInflater().inflate(R.layout.content_2x2_step2, layoutForInjectInto);

        final Button stepButton1 = (Button) findViewById(R.id.button1_2x2);
        Button stepButton2 = (Button) findViewById(R.id.button2_2x2);
        Button stepButton3 = (Button) findViewById(R.id.button3_2x2);
        ImageView settingsImage = (ImageView) findViewById(R.id.settings_imageview);
        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        step2Test = (ImageView) findViewById(R.id.gridView);
        step2Test.setVisibility(View.GONE);
        showImage = (ImageView) findViewById(R.id._2x2_case_1_cross_show_moves);

        //Moves to show or hide
        //*********************************************
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMarginEnd(10);
        params.setMarginStart(10);
        params.gravity = 11; // 11="center"


        ImageView l = new ImageView(this);
        l.setImageResource(moves2x2[0]);
        ImageView l_prim = new ImageView(this);
        l_prim.setImageResource(moves2x2[1]);
        ImageView r = new ImageView(this);
        r.setImageResource(moves2x2[2]);
        ImageView r_prim = new ImageView(this);
        r_prim.setImageResource(moves2x2[3]);
        ImageView f = new ImageView(this);
        f.setImageResource(moves2x2[4]);
        ImageView f_prim = new ImageView(this);
        f_prim.setImageResource(moves2x2[5]);



        LinearLayout childLayout_1 = new LinearLayout(this);
        childLayout_1.setLayoutParams(params);
        LinearLayout childLayout_2 = new LinearLayout(this);
        childLayout_2.setLayoutParams(params);




        childLayout_1.setOrientation(LinearLayout.HORIZONTAL);
        childLayout_1.setGravity(View.TEXT_ALIGNMENT_CENTER);
        childLayout_2.setOrientation(LinearLayout.HORIZONTAL);
        childLayout_2.setGravity(View.TEXT_ALIGNMENT_CENTER);

        childLayout_1.addView(l);
        childLayout_1.addView(l_prim);
        childLayout_1.addView(r);

        childLayout_2.addView(r_prim);
        childLayout_2.addView(f);
        childLayout_2.addView(f_prim);

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.linear_layout_to_add_images);
        parentLayout.addView(childLayout_1);
        parentLayout.addView(childLayout_2);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;

        l.setLayoutParams(layoutParams);
        l_prim.setLayoutParams(layoutParams);
        r.setLayoutParams(layoutParams);
        r_prim.setLayoutParams(layoutParams);
        f.setLayoutParams(layoutParams);
        f_prim.setLayoutParams(layoutParams);



        //*********************************************

        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step2Test.getVisibility()==View.GONE){
                    step2Test.setVisibility(View.VISIBLE);
                    showImage.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                } else {
                    step2Test.setVisibility(View.GONE);
                    showImage.setImageResource(android.R.drawable.ic_menu_add);
                }

            }
        });

        backButton.setOnClickListener(this);
        stepButton1.setOnClickListener(this);
        stepButton2.setOnClickListener(this);
        stepButton3.setOnClickListener(this);
        settingsImage.setOnClickListener(this);

        stepButton2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        stepButton2.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_menu_text_active)));

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
                startActivity(new Intent(getApplicationContext(), Activity_2x2_Step2.class));
                break;
            case R.id.button3_2x2:
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                    interstitialAd.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            startActivity(new Intent(getApplicationContext(), Activity_2x2_Step3.class));
                        }
                    });
                } else {
                    startActivity(new Intent(getApplicationContext(), Activity_2x2_Step3.class));
                }
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
