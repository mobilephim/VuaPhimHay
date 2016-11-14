package com.example.team2_mobilephim.team2_mobilephiem;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.MediaController;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.VideoView;

public class Activity_Content extends AppCompatActivity  {
    TabHost tabhost;
    VideoView vview;
    MediaController mediaController;
    TextView ten, theloai, nam, mota;
    String name, type, year, decs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        ten = (TextView) findViewById(R.id.tvphim);
        theloai = (TextView) findViewById(R.id.tvtheloai);
        nam = (TextView) findViewById(R.id.tvnamsx);
        mota = (TextView) findViewById(R.id.tvmota);

        try {
            String link = getIntent().getStringExtra("urls");
            vview = (VideoView) findViewById(R.id.videoView);
            vview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            mediaController = new MediaController(Activity_Content.this);
                            vview.setMediaController(mediaController);
                            mediaController.setAnchorView(vview);
                        }
                    });
                }
            });
            Uri video = Uri.parse(link);
            vview.setVideoURI(video);
            vview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        host.setup();

        TabHost.TabSpec tab1 = host.newTabSpec("Information");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Information");
        host.addTab(tab1);

        tab1 = host.newTabSpec("Server");
        tab1.setContent(R.id.tab2);
        tab1.setIndicator("Server");
        host.addTab(tab1);

        name = getIntent().getStringExtra("name");
        ten.setText(name);
        type = getIntent().getStringExtra("type");
        theloai.setText(type);
        year = getIntent().getStringExtra("year");
        nam.setText(year);
        decs = getIntent().getStringExtra("decs");
        mota.setText(decs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.content, menu);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
