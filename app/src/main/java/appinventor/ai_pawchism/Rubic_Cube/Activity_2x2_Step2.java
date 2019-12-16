package appinventor.ai_pawchism.Rubic_Cube;

import android.content.Intent;
import android.content.res.ColorStateList;
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

public class Activity_2x2_Step2 extends AppCompatActivity implements View.OnClickListener {
    InterstitialAd interstitialAd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        //Full screen ads
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
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
                startActivity(new Intent(getApplicationContext(), Activity_StartPage.class));
                break;
        }
    }
}
