package com.example.team2_mobilephim.team2_mobilephiem;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import controller.FilmMaster;
import customadapter.CustomList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    private SearchView searchView;
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
        checkInternetConnection();

        view = (GridView) findViewById(R.id.grid_view);
        // thuc thi clas docJSON

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Activity_Home phimhot = new Activity_Home();

        transaction.replace(R.id.content_main, phimhot);
        drawer.closeDrawer(GravityCompat.START);
        transaction.commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<FilmMaster> newlist = new ArrayList<>();
        for (FilmMaster a : listfilm) {
            String name = a.getName().toLowerCase();
            if (name.contains(newText)) {
                newlist.add(a);
            }
            customList.setFilter(newlist);


        }

        return true;
    }

//    class docJSON extends AsyncTask<String, Integer, String> {
//        ProgressDialog pbloading;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pbloading = new ProgressDialog(MainActivity.this);
//            pbloading.setMessage("Đang tải phim...");
//            pbloading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            pbloading.setCancelable(true);
//            pbloading.setCanceledOnTouchOutside(false);
//            pbloading.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            return docNoiDung_Tu_URL(params[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//            try {
//                pbloading.dismiss();
//                JSONArray mangjson = new JSONArray(s);
//                for (int i = 0; i < mangjson.length(); i++) {
//                    JSONObject obj = mangjson.getJSONObject(i);
//                    listfilm.add(new FilmMaster(
//                            obj.getString("name"),
//                            obj.getString("thumb"),
//                            obj.getString("url"),
//                            obj.getString("type"),
//                            obj.getString("year"),
//                            obj.getString("decs")
//                    ));
//                }
//                //Toast.makeText(getApplicationContext(),""+listfilm.size(),Toast.LENGTH_SHORT).show();
//                customList = new CustomList(
//                        getApplicationContext(),
//                        R.layout.activity_customfilm,
//                        listfilm
//                );
//                customList.notifyDataSetChanged();
//                view.setAdapter(customList);
//                view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        //Toast.makeText(getApplicationContext(), "" + listfilm.get(position).getLink(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, Activity_Content.class);
//                        intent.putExtra("urls", listfilm.get(position).getLink());
//                        intent.putExtra("name",listfilm.get(position).getName());
//                        intent.putExtra("type",listfilm.get(position).getType());
//                        intent.putExtra("year",listfilm.get(position).getYear());
//                        intent.putExtra("decs",listfilm.get(position).getDecs());
//                        pbloading.dismiss();
//                        startActivity(intent);
//                    }
//                });
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemSearch = menu.findItem(R.id.menu_seach);
        searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
        //set OnQueryTextListener cho search view để thực hiện search theo text
        searchView.setOnQueryTextListener(this);
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
                Activity_Home trangchu = new Activity_Home();

                transaction.replace(R.id.content_main, trangchu);
                drawer.closeDrawer(GravityCompat.START);
                transaction.commit();
                ;
                break;
            case R.id.phimle:
                ;
                break;
            case R.id.phimbo:
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                Activity_Phimbo phimhot1 = new Activity_Phimbo();

                transaction1.replace(R.id.content_main, phimhot1);
                drawer.closeDrawer(GravityCompat.START);
                transaction1.commit();
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

    private boolean checkInternetConnection() {
        // Lấy ra bộ quản lý kết nối.
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Thông tin mạng đang kích hoạt.
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "Không có kết nối vui lòng thử lại", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Đã kết nối", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Kết nối không khả dụng", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
