/*
 *  Copyright 2018 Soojeong Shin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.juicefactorynaeks;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.juicefactorynaeks.model.Juice;
import com.example.juicefactorynaeks.utils.JsonUtils;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.tv_kompanija)
    TextView mtvKompanija;
    @BindView(R.id.tv_poreklo)
    TextView mtvPoreklo;
    @BindView(R.id.tv_opis)
    TextView mtvOpis;
    @BindView(R.id.tv_sastojci)
    TextView mtvSastojci;

    @BindView(R.id.o_kompaniji)
    TextView mKompanija;
    @BindView(R.id.poreklo)
    TextView mPoreklo;
    @BindView(R.id.opis)
    TextView mOpis;
    @BindView(R.id.sastojci)
    TextView mSastojci;

    @BindView(R.id.image_iv)
    ImageView mImage;

    @BindView(R.id.toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.app_bar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        ButterKnife.bind( this );

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra( EXTRA_POSITION, DEFAULT_POSITION );
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] juice = getResources().getStringArray( R.array.juice_details );
        String json = juice[position];
        Juice juice1 = JsonUtils.parseSandwichJson( json );
        if (juice1 == null) {
            closeOnError();
            return;
        }

        setTypeface();
        populateUI( juice1 );
        Picasso.with( this )
                .load( juice1.getImage() )
                .into( mImage );

        collapsingToolbarLayout.setTitle( juice1.getMainName() );

        setSupportActionBar( mToolbar );
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled( true );
            actionBar.setDisplayHomeAsUpEnabled( true );
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText( this, R.string.detail_error_message, Toast.LENGTH_SHORT ).show();
    }

    private void populateUI(Juice sandwich) {

        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (alsoKnownList != null) {

            String alsoKnown = TextUtils.join( getString( R.string.new_line ), alsoKnownList );

            mtvKompanija.setText( alsoKnown );
        } else {

            mtvKompanija.setText( getString( R.string.message_not_available ) );
        }

        String originString = sandwich.getPlaceOfOrigin();

        if (originString != null) {
            mtvPoreklo.setText( originString );
        } else {

            mtvPoreklo.setText( getString( R.string.message_not_available ) );
        }

        mtvOpis.setText( sandwich.getDescription() );


        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList != null) {

            String ingredients = TextUtils.join( getString( R.string.new_line ), ingredientsList );

            mtvSastojci.setText( ingredients );
        }

    }


    private void setTypeface() {

        Typeface raleway = ResourcesCompat.getFont( this, R.font.raleway_regular );
        Typeface righteous = ResourcesCompat.getFont( this, R.font.righteous_regular );

        mtvKompanija.setTypeface( raleway );
        mtvPoreklo.setTypeface( raleway );
        mtvSastojci.setTypeface( raleway );
        mtvOpis.setTypeface( raleway );

        mKompanija.setTypeface( righteous );
        mPoreklo.setTypeface( righteous );
        mSastojci.setTypeface( righteous );
        mOpis.setTypeface( righteous );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
