package appinventor.ai_pawchism.Rubic_Cube;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

public class GeneralInfo extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        final LinearLayout layoutForInjectInto = (LinearLayout) findViewById(R.id.inflate_content_vert_layout);

        getLayoutInflater().inflate(R.layout.fragment_up_bar, layoutForInjectInto);
        getLayoutInflater().inflate(R.layout.content_general_info, layoutForInjectInto);

    }
}
