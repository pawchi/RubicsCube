package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity {
    private static int WELCOME_SCREEN_TIME_OUT = 3000;
    private TextView animatedText;
    private ImageView animatedImage;
    String startedLanguage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loadLocale();
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        startedLanguage = prefs.getString("My_Lang",""); //read the language in which the activity was created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        animatedImage = (ImageView) findViewById(R.id.imageView_animated);

        YoYo.with(Techniques.Tada)
                .duration(1500)
                .repeat(2)
                .playOn(animatedImage);
        /*
        YoYo.with(Techniques.Wobble)
                .duration(1500)
                .repeat(2)
                .playOn(animatedImage);
        */

        //'WELCOME SCREEN' for 4s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        },WELCOME_SCREEN_TIME_OUT);
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

