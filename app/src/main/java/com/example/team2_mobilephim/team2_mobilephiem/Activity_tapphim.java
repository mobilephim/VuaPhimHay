package com.example.team2_mobilephim.team2_mobilephiem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import controller.TapPhim;
import customadapter.CustomTapPhim;

public class Activity_TapPhim extends AppCompatActivity {
    ArrayList<TapPhim> listfilm = new ArrayList<>();
    ArrayList<String> mangtapphim = new ArrayList<>();
    ListView lv;
    String dieukien;
    CustomTapPhim tapPhim;
    String name, type, year, decs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapphim);
        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            dieukien = bd.getString("name");
        }
        lv = (ListView) findViewById(R.id.lv_tapphim);
        new Activity_TapPhim.DogetData().execute("http://hoangthong.website/app/filmep.php");
        name = getIntent().getStringExtra("name");

        type = getIntent().getStringExtra("type");

        year = getIntent().getStringExtra("year");

        decs = getIntent().getStringExtra("decs");


    }

    class DogetData extends AsyncTask<String, Integer, ArrayList<TapPhim>> {
        String urllink;
        String result;
        ProgressDialog pbloading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbloading = new ProgressDialog(Activity_TapPhim.this);
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
                        mangtapphim.add(tentap);
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

            tapPhim = new CustomTapPhim(Activity_TapPhim.this, R.layout.activity_custom_tapphim, listfilm);
            lv.setAdapter(tapPhim);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), Activity_Content.class);
                    intent.putExtra("urls", listfilm.get(position).getLink());
                    intent.putExtra("name",listfilm.get(position).getName());

                    intent.putExtra("type",type);
                    intent.putExtra("year",year);
                    intent.putExtra("decs",decs);


                    startActivity(intent);


                }
            });
        }
    }

}

