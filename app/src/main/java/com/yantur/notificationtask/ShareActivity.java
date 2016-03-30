package com.yantur.notificationtask;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShareActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter<String> adapter;
    private List<String> data;
    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        data = new ArrayList<>();
        data.add("item 1");
        data.add("item 2");
        data.add("item 3");
        data.add("item 4");
        data.add("item 5");
        data.add("item 6");
        data.add("item 7");
        data.add("item 8");
        data.add("item 9");
        data.add("item 10");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        update();
                    }
                }
        );
    }

    private void update() {
        new Thread(new Runnable() {
            @Override
            synchronized public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data.add(0, "Refresh result " + i);
                        i++;
                        adapter.notifyDataSetChanged();
                    }
                });


                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();

    }
}
