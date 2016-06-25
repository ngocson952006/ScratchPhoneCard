package com.example.truongngoc.scratchphonecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class
            .getSimpleName(); // tag


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up icon for action bar
        ActionBar actionBar = this.getSupportActionBar();

//        // make sure we have valid action bar, in case incompatible android version
//        if (actionBar != null) {
//            actionBar.setTitle("");
//            actionBar.setLogo(R.drawable.app); // we do not use title anymore , instead, using main_logo to display
//            actionBar.setDisplayUseLogoEnabled(true);
//        }

        // floating action button zone
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent guideIntent = new Intent(MainActivity.this, UserGuideActivity.class);
                startActivity(guideIntent);
            }

        });

        final ListView recentCardsListView = (ListView) this.findViewById(R.id.recentcards_listview);
        recentCardsListView.setEmptyView(this.findViewById(R.id.empty_list_view_holder)); // handle empty list view

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
