package com.example.juicefactorynaeks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity  {

    private SharedPreferences prefs;
    private boolean splash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        prefs = PreferenceManager.getDefaultSharedPreferences( this );
        splash = prefs.getBoolean( getString( R.string.splash_key ), true );

        if (splash) {
            setContentView( R.layout.splash_screen );

            ImageView imageView = findViewById( R.id.imageSplash );
            InputStream is;
            imageView.animate().rotation( 1800 ).alpha( 0 ).setDuration( 10000 );
            imageView.animate().scaleX( 0.5f ).scaleY( 0.5f ).setDuration( 2000 );
           // imageView.animate().translationXBy( 1000 ).rotation( 3600 ).setDuration( 3000 );
            try {
                is = getAssets().open( "na_eks1.jpg" );
                Drawable drawable = Drawable.createFromStream( is, null );
                imageView.setImageDrawable( drawable );
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Timer().schedule( new TimerTask() {
                @Override
                public void run() {
                    startActivity( new Intent( SplashScreen.this, MainActivity.class ) );
                    finish();
                }
            }, 2500 );
        } else {
            startActivity( new Intent( SplashScreen.this, MainActivity.class ) );
            finish();
        }
    }
}
