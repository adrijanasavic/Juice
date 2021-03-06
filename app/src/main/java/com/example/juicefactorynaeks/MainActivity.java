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

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.juice_listview)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ButterKnife.bind( this );

        String[] juices = getResources().getStringArray( R.array.juice_names );
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1, juices );

        mListView.setAdapter( adapter );
        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    launchDetailActivityTransition( position, view );
                } else {

                    launchDetailActivity( position );
                }
            }
        } );
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent( this, DetailActivity.class );
        intent.putExtra( DetailActivity.EXTRA_POSITION, position );
        startActivity( intent );
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void launchDetailActivityTransition(int position, View view) {
        Intent intent = new Intent( this, DetailActivity.class );
        intent.putExtra( DetailActivity.EXTRA_POSITION, position );

        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                this,
                view,
                getString( R.string.transition_move )
        ).toBundle();

        startActivity( intent, bundle );
    }
}
