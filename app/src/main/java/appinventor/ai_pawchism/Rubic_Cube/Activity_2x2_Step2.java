package appinventor.ai_pawchism.Rubic_Cube;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.Locale;

public class Activity_2x2_Step2 extends AppCompatActivity implements View.OnClickListener {
    InterstitialAd interstitialAd;
    String startedLanguage;
    ImageView showImage, showImageCase2, showImageCase3, showImageCase4, showImageCase5, showImageAllCases;
    private static  int[] moves2x2 = {R.drawable._2x2_l,R.drawable._2x2_l_prim,R.drawable._2x2_r,R.drawable._2x2_r_prim,R.drawable._2x2_f,
            R.drawable._2x2_f_prim,R.drawable._2x2_b,R.drawable._2x2_b_prim,R.drawable._2x2_d,R.drawable._2x2_d_prim,R.drawable._2x2_u,R.drawable._2x2_u_prim};
    private static int[] restCases_2x2 = {R.drawable.dwa_step2_case2, R.drawable.dwa_step2_case3, R.drawable.dwa_step2_case4, R.drawable.dwa_step2_case5, R.drawable.dwa_step2_case6, R.drawable.dwa_step2_case7};
    LinearLayout.LayoutParams params, layoutParams;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(getApplicationContext(), Activity_StartPage.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

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
        interstitialAd.setAdUnitId(getResources().getString(R.string.Ad_full_screen));
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
        showImage = (ImageView) findViewById(R.id._2x2_case_1_cross_show_moves);

        setParamsForMove();

        movesAllCases(0,"r","u","u","r'","u'","r","u'","r'","v");
        movesAllCases(1,"l'","u'","l","u","r","u'","l'","u","v");
        movesAllCases(2,"r","r","u","u","r","u","u","r","r");
        movesAllCases(3,"f","r","u'","r'","u'","r","u","r'","f'");
        movesAllCases(4,"r","u","u",  "r","r","u'",  "r","r","u'",   "r","r","u",   "u","r", "v");
        movesAllCases(5,"r","r","d",  "r'","u","u",  "r","d'","r'",  "u","u","r'");

        //Moves to show or hide
         //initialize all Images
        case_1();

        backButton.setOnClickListener(this);
        stepButton1.setOnClickListener(this);
        stepButton2.setOnClickListener(this);
        stepButton3.setOnClickListener(this);
        settingsImage.setOnClickListener(this);

        stepButton2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        stepButton2.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_menu_text_active)));

        LinearLayout skoczekAdd = (LinearLayout) findViewById(R.id.skoczek_dwa_2);
        skoczekAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.chilon.skoczeknew")));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.chilon.skoczeknew")));
                }
            }
        });

    }

    public void setParamsForMove(){

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10,20,10,20);
        layoutParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;

    }

    public ImageView getNewSingleMove(String move){
        int mv=0;
        switch (move){
            case "l": mv=0; break;
            case "l'": mv=1; break;
            case "r": mv=2; break;
            case "r'": mv=3; break;
            case "f": mv=4; break;
            case "f'": mv=5; break;
            case "b": mv=6; break;
            case "b'": mv=7; break;
            case "d": mv=8; break;
            case "d'": mv=9; break;
            case "u": mv=10; break;
            case "u'": mv=11; break;
            case "v": mv=12; break;
        }

        ImageView newMove = new ImageView(this);
        if (mv==12){
            newMove.setImageResource(moves2x2[0]);
            newMove.setAdjustViewBounds(true);
            newMove.setLayoutParams(params);
            newMove.setLayoutParams(layoutParams);
            newMove.setVisibility(View.INVISIBLE);
        } else {
            newMove.setImageResource(moves2x2[mv]);
            newMove.setAdjustViewBounds(true);
            newMove.setLayoutParams(params);
            newMove.setLayoutParams(layoutParams);
        }

        return newMove;
    }

    public void case_1(){
        LinearLayout childLayout_1 = new LinearLayout(this);
        childLayout_1.setLayoutParams(params);
        LinearLayout childLayout_2 = new LinearLayout(this);
        childLayout_2.setLayoutParams(params);
        LinearLayout childLayout_3 = new LinearLayout(this);
        childLayout_3.setLayoutParams(params);

        childLayout_1.addView(getNewSingleMove("r"));
        childLayout_1.addView(getNewSingleMove("u"));
        childLayout_1.addView(getNewSingleMove("r'"));

        childLayout_2.addView(getNewSingleMove("u"));
        childLayout_2.addView(getNewSingleMove("r"));
        childLayout_2.addView(getNewSingleMove("u"));

        childLayout_3.addView(getNewSingleMove("u"));
        childLayout_3.addView(getNewSingleMove("r'"));
        ImageView invisibleView = getNewSingleMove("l");
        invisibleView.setVisibility(View.INVISIBLE);
        childLayout_3.addView(invisibleView); //view needed for occupying space for evenly layout (is invisible)

        final LinearLayout parentLayout = (LinearLayout) findViewById(R.id.linear_layout_to_add_images);
        parentLayout.setVisibility(View.GONE);
        parentLayout.addView(childLayout_1);
        parentLayout.addView(childLayout_2);
        parentLayout.addView(childLayout_3);


        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parentLayout.getVisibility()==View.GONE){
                    parentLayout.setVisibility(View.VISIBLE);
                    showImage.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                } else {
                    parentLayout.setVisibility(View.GONE);
                    showImage.setImageResource(android.R.drawable.ic_menu_add);
                }
            }
        });
    }

    public void movesAllCases(int caseImage, String...moves){

        final LinearLayout case_child_image = new LinearLayout(this);
        case_child_image.setLayoutParams(params);
        case_child_image.setGravity(Gravity.CENTER_HORIZONTAL);
        ImageView case_1 = new ImageView(this);
        case_1.setImageResource(restCases_2x2[caseImage]);
        case_child_image.addView(case_1);


        final ImageView showImageCase = new ImageView(this);
        showImageCase.setImageResource(android.R.drawable.ic_menu_add);
        case_child_image.addView(showImageCase);

        LinearLayout case_child_moves_1 = new LinearLayout(this);
        case_child_moves_1.setLayoutParams(params);
        LinearLayout case_child_moves_2 = new LinearLayout(this);
        case_child_moves_2.setLayoutParams(params);
        LinearLayout case_child_moves_3 = new LinearLayout(this);
        case_child_moves_3.setLayoutParams(params);
        LinearLayout case_child_moves_4 = new LinearLayout(this);
        case_child_moves_4.setLayoutParams(params);
        LinearLayout case_child_moves_5 = new LinearLayout(this);
        case_child_moves_5.setLayoutParams(params);

        case_child_moves_1.addView(getNewSingleMove(moves[0]));
        case_child_moves_1.addView(getNewSingleMove(moves[1]));
        case_child_moves_1.addView(getNewSingleMove(moves[2]));

        case_child_moves_2.addView(getNewSingleMove(moves[3]));
        case_child_moves_2.addView(getNewSingleMove(moves[4]));
        case_child_moves_2.addView(getNewSingleMove(moves[5]));

        case_child_moves_3.addView(getNewSingleMove(moves[6]));
        case_child_moves_3.addView(getNewSingleMove(moves[7]));
        case_child_moves_3.addView(getNewSingleMove(moves[8]));



        final LinearLayout parentLayoutMoves = new LinearLayout(this);
        parentLayoutMoves.setOrientation(LinearLayout.VERTICAL);
        parentLayoutMoves.setVisibility(View.GONE);
        parentLayoutMoves.addView(case_child_moves_1);
        parentLayoutMoves.addView(case_child_moves_2);
        parentLayoutMoves.addView(case_child_moves_3);

        if (moves.length>9){
            case_child_moves_4.addView(getNewSingleMove(moves[9]));
            case_child_moves_4.addView(getNewSingleMove(moves[10]));
            case_child_moves_4.addView(getNewSingleMove(moves[11]));
            parentLayoutMoves.addView(case_child_moves_4);
        }

        if (moves.length>12){
            case_child_moves_5.addView(getNewSingleMove(moves[12]));
            case_child_moves_5.addView(getNewSingleMove(moves[13]));
            case_child_moves_5.addView(getNewSingleMove(moves[14]));
            parentLayoutMoves.addView(case_child_moves_5);
        }

        //**** Divider - black line
        View line = new View(this);
        ViewGroup.LayoutParams lineParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lineParams.height = 4;
        line.setLayoutParams(lineParams);
        line.setBackgroundColor(Color.BLACK);
        //****

        final LinearLayout parentLayoutRestCases = (LinearLayout) findViewById(R.id.dwa_step2_rest_cases_layout);
        parentLayoutRestCases.addView(case_child_image);
        parentLayoutRestCases.addView(parentLayoutMoves);
        parentLayoutRestCases.addView(line);


        showImageCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parentLayoutMoves.getVisibility()==View.GONE){
                    parentLayoutMoves.setVisibility(View.VISIBLE);
                    showImageCase.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                } else {
                    parentLayoutMoves.setVisibility(View.GONE);
                    showImageCase.setImageResource(android.R.drawable.ic_menu_add);
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
                    finish();
                }
                break;
            case R.id.button2_2x2:
                startActivity(new Intent(getApplicationContext(), Activity_2x2_Step2.class));
                finish();
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
                    finish();
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
                    finish();
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
