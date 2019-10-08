package appinventor.ai_pawchism.Rubic_Cube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

public class Activity_3x3_Step6 extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);


        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        final LinearLayout layoutForInjectInto = (LinearLayout) findViewById(R.id.inflate_content_vert_layout);
        LinearLayout upBar = (LinearLayout) findViewById(R.id.up_bar_for_all);

        getLayoutInflater().inflate(R.layout.fragment_up_bar, layoutForInjectInto);
        getLayoutInflater().inflate(R.layout.layout_buttons_for_3x3, layoutForInjectInto);
        getLayoutInflater().inflate(R.layout.content_3x3_step6, layoutForInjectInto);

        Button stepButton1 = (Button) findViewById(R.id.button1_3x3);
        Button stepButton2 = (Button) findViewById(R.id.button2_3x3);
        Button stepButton3 = (Button) findViewById(R.id.button3_3x3);
        Button stepButton4 = (Button) findViewById(R.id.button4_3x3);
        Button stepButton5 = (Button) findViewById(R.id.button5_3x3);
        Button stepButton6 = (Button) findViewById(R.id.button6_3x3);
        Button stepButton7 = (Button) findViewById(R.id.button7_3x3);

        stepButton1.setOnClickListener(this);
        stepButton2.setOnClickListener(this);
        stepButton3.setOnClickListener(this);
        stepButton4.setOnClickListener(this);
        stepButton5.setOnClickListener(this);
        stepButton6.setOnClickListener(this);
        stepButton7.setOnClickListener(this);
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
        }
    }
}
