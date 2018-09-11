package org.chilon.rubics_cube;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

public class Step1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);

        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        Button homeButton = (Button) findViewById(R.id.step1_to_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step1.this,MainActivity.class));
            }
        });

        Button step1 = (Button) findViewById(R.id.step1_to_step1_button);
        step1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step1.this,Step1.class));
            }
        });

        Button step2 = (Button) findViewById(R.id.step1_to_step2_button);
        step2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step1.this,Step2.class));
            }
        });

        Button step3 = (Button) findViewById(R.id.step1_to_step3_button);
        step3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step1.this,Step3.class));
            }
        });

        Button step4 = (Button) findViewById(R.id.step1_to_step4_button);
        step4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step1.this,Step4.class));
            }
        });

        Button step5 = (Button) findViewById(R.id.step1_to_step5_button);
        step5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step1.this,Step5.class));
            }
        });

        ImageView settingsImage = (ImageView) findViewById(R.id.step1_settings_imageview);
        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step1.this,Settings.class));
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recreate();
    }
}
