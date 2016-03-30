package com.yantur.notificationtask;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final static int NOTIFICATION_ID = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayNotification();
            }
        });
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
}
