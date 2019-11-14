package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        loadLocale();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Activity_Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLanguageForApp(language);
    }

    public void setLanguageForApp(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }
}
