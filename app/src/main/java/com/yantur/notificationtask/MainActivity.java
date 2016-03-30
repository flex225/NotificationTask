package com.yantur.notificationtask;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final static int NOTIFICATION_ID = 5;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = new ArrayList<>();
        data.add("Click to go with deep link");

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView listView = (ListView) findViewById(R.id.left_drawer);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
        listView.setOnItemClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
    }


    public void displayNotification() {

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("Timer");
        builder.setContentText("Your time: ");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("ART", "OK");
                int progress;
                int maxProgress = 100;
                for (progress = 0; progress <= maxProgress; progress++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    builder.setProgress(maxProgress, progress, false);
                    manager.notify(NOTIFICATION_ID, builder.build());
                }
                builder.setProgress(0, 0, false);
                builder.setContentText("Minute is over.");
                manager.notify(NOTIFICATION_ID, builder.build());
            }
        });
        thread.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("example://yantur"));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button:
                displayNotification();
                break;
            case R.id.button1:
                Intent intent = new Intent(this, ShareActivity.class);
                startActivity(intent);
                break;
        }
    }
}
