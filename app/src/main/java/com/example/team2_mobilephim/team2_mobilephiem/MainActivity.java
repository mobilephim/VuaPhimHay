package com.example.team2_mobilephim.team2_mobilephiem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import controller.FilmMaster;
import customadapter.CustomList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    FragmentManager fragmentManager;
    GridView view;
    ArrayList<FilmMaster> listfilm = new ArrayList<FilmMaster>();
    CustomList customList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentManager = getSupportFragmentManager();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // thuc thi kiem tra ket noi internet
        isConnected();

        view = (GridView) findViewById(R.id.grid_view);
        // thuc thi clas docJSON
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://hoangthong.website/app");
            }
        });

    }

    class docJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            try {
                JSONArray mangjson = new JSONArray(s);
                for (int i = 0; i < mangjson.length(); i++) {
                    JSONObject obj = mangjson.getJSONObject(i);
                    listfilm.add(new FilmMaster(
                            obj.getString("name"),
                            obj.getString("thumb"),
                            obj.getString("url"),
                            obj.getString("type"),
                            obj.getString("year"),
                            obj.getString("decs")
                    ));
                }
                //Toast.makeText(getApplicationContext(),""+listfilm.size(),Toast.LENGTH_SHORT).show();
                customList = new CustomList(
                        getApplicationContext(),
                        R.layout.activity_customfilm,
                        listfilm
                );
                customList.notifyDataSetChanged();
                view.setAdapter(customList);
                view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(), "" + listfilm.get(position).getLink(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Activity_Content.class);
                        intent.putExtra("urls", listfilm.get(position).getLink());
                        intent.putExtra("name",listfilm.get(position).getName());
                        intent.putExtra("type",listfilm.get(position).getType());
                        intent.putExtra("year",listfilm.get(position).getYear());
                        intent.putExtra("decs",listfilm.get(position).getDecs());
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int i = item.getItemId();
        switch (i) {
            case R.id.phimhot:
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Phim_hot phimhot = new Phim_hot();

                transaction.replace(R.id.content_main, phimhot);
                drawer.closeDrawer(GravityCompat.START);
                transaction.commit();
                ;
                break;
            case R.id.phimle:
                ;
                break;
            case R.id.phimbo:
                ;
                break;
            case R.id.phimchieurap:
                ;
                break;
            case R.id.hoathinh:
                ;
                break;
            case R.id.hanhdong:
                ;
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // khoi tao ham doc noi dung json
    private static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // kiem tra ket noi mang
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    // tao diag log kiem tra mang
    public void showDialog(Activity activity, String msg, int a) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        // Định dạng chiều cao và chiều rộng cho dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // căn giữa các đối tượng
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        ImageView imgIcon = (ImageView) dialog.findViewById(R.id.imgIcon);
        if (a == 1) {
            // đổi màu backgroud cho imageview dialog
            imgIcon.setBackgroundColor(Color.rgb(60, 190, 57));
            // đổi ảnh trong ImageView trong Dialog
            imgIcon.setImageResource(R.drawable.ic_collections_3x);
        } else {
            imgIcon.setBackgroundColor(Color.rgb(218, 95, 106));
            imgIcon.setImageResource(R.drawable.ic_collections_2x);
        }

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
