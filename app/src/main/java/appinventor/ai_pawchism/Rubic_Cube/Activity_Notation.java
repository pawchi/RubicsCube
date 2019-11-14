package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

public class Activity_Notation extends AppCompatActivity {

    LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        LinearLayout layoutForInjectInto = (LinearLayout) findViewById(R.id.inflate_content_vert_layout);

        //getLayoutInflater().inflate(R.layout.fragment_up_bar, layoutForInjectInto);
        //getLayoutInflater().inflate(R.layout.content_general_info, layoutForInjectInto);

        //another solution for inflating layout

        //Set left margin in inflated layout
        inflater = (LayoutInflater) this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view1 = LayoutInflater.from(this).inflate(R.layout.fragment_up_bar, layoutForInjectInto, false);
        View view2 = LayoutInflater.from(this).inflate(R.layout.content_general_info, layoutForInjectInto, false);
        layoutForInjectInto.addView(view1);
        layoutForInjectInto.addView(view2);

        //inflater.inflate(R.layout.fragment_up_bar, layoutForInjectInto);
        //inflater.inflate(R.layout.content_general_info, layoutForInjectInto);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_general_info);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 0);
        view2.setLayoutParams(layoutParams);



        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_StartPage.class));
            }
        });

        ImageView settings = (ImageView) findViewById(R.id.settings_imageview);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_Settings.class));
            }
        });

    }
}
