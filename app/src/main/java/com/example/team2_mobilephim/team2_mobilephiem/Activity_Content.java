package com.example.team2_mobilephim.team2_mobilephiem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import controller.TapPhim;
import customadapter.Loadingphim;
import customadapter.cutom_tapphim_tabsever;

public class Activity_Content extends AppCompatActivity {
    TabHost tabhost;
    VideoView vview;
    ArrayList<TapPhim> listfilm = new ArrayList<>();
    MediaController mediaController;
    TextView ten, theloai, nam, mota;
    String name, type, year, decs;
    GridView lv;
    String dieukien;
    String link;

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
        lv  = (GridView) findViewById(R.id.lvsevetapphim);
        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            dieukien = bd.getString("name");
        }

        try {
            Loadingphim loadingphim = new Loadingphim();
            loadingphim.LaunchBatDiaLog(Activity_Content.this);

            link = getIntent().getStringExtra("urls");
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

        TabHost.TabSpec tab2 = host.newTabSpec("Server");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Server");
        host.addTab(tab2);

        name = getIntent().getStringExtra("name");
        ten.setText(name);
        type = getIntent().getStringExtra("type");
        theloai.setText(type);
        year = getIntent().getStringExtra("year");
        nam.setText(year);
        decs = getIntent().getStringExtra("decs");
        mota.setText(decs);

        new Activity_Content.DogetData().execute("http://hoangthong.website/app/filmep.php");


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
        if (id == R.id.menu_share) {
            String texttoShare = "Các bạn đang xem phim " + name + " - " + " Vua Phim Hay";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
            intent.putExtra(Intent.EXTRA_TEXT, texttoShare + "" + link);

            // See if official Facebook app is found
            boolean facebookAppFound = false;
            List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                    intent.setPackage(info.activityInfo.packageName);
                    facebookAppFound = true;
                    break;
                }
            }
            startActivity(Intent.createChooser(intent,"Share using"));

        }

        return super.onOptionsItemSelected(item);
    }
    class DogetData extends AsyncTask<String, Integer, ArrayList<TapPhim>> {
        String urllink;
        String result;
        ProgressDialog pbloading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbloading = new ProgressDialog(Activity_Content.this);
            pbloading.setMessage("Đang tải phim chờ xíu nhé..");
            pbloading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pbloading.setCancelable(true);
            pbloading.setCanceledOnTouchOutside(false);
            pbloading.show();
        }

        @Override
        protected ArrayList<TapPhim> doInBackground(String... params) {
            urllink = params[0];
            try {
                URL url = new URL(urllink);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                result = "";
                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    result += (char) byteCharacter;
                }

                JSONArray jsonArray = new JSONArray(result);
                String chuoi = "";
                int length = jsonArray.length();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String tentap = jsonObject.getString("episode");
                    String link = jsonObject.getString("url");

                    if (name.equals(dieukien)) {
                        TapPhim tapPhim = new TapPhim();
                        tapPhim.setName(name);
                        tapPhim.setTentap(name+" - "+tentap);
                        tapPhim.setLink(link);

                        listfilm.add(tapPhim);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listfilm;
        }

        @Override
        protected void onPostExecute(ArrayList<TapPhim> values) {
            super.onPostExecute(values);
            pbloading.dismiss();
            cutom_tapphim_tabsever cutom_tapphim_tabsever = new cutom_tapphim_tabsever(Activity_Content.this,R.layout.cutom_tapphim,listfilm);
lv.setAdapter(cutom_tapphim_tabsever);


        }
    }



}
