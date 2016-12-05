package com.example.team2_mobilephim.team2_mobilephiem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import controller.FilmMaster;
import customadapter.CustomList;


public class Activity_Home extends android.support.v4.app.Fragment implements SearchView.OnQueryTextListener {

    ArrayList<FilmMaster> listfilm = new ArrayList<>();
    GridView gridView;
    CustomList customList;
    int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        new DogetData().execute("http://hoangthong.website/app/");
        setHasOptionsMenu(true);
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //inflater.inflate(R.menu.main, menu); // removed to not double the menu items
        MenuItem item = menu.findItem(R.id.menu_seach);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(this);



        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return true;
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


    class DogetData extends AsyncTask<String, Integer, ArrayList<FilmMaster>> {
        String urllink;
        String result;
        ProgressDialog pbloading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbloading = new ProgressDialog(getContext());
            pbloading.setMessage("Đang tải phim chờ xíu nhé..");
            pbloading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pbloading.setCancelable(true);
            pbloading.setCanceledOnTouchOutside(false);
            pbloading.show();
        }

        @Override
        protected ArrayList<FilmMaster> doInBackground(String... params) {
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
                for (int i = 0; i < 10; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String thumb = jsonObject.getString("thumb");
                    String type = jsonObject.getString("type");
                    String urll = jsonObject.getString("url");
                    String year = jsonObject.getString("year");
                    String decs = jsonObject.getString("decs");

                    FilmMaster phimhot = new FilmMaster();
                    phimhot.setName(name);
                    phimhot.setThumb(thumb);
                    phimhot.setLink(urll);
                    phimhot.setType(type);
                    phimhot.setYear(year);
                    phimhot.setDecs(decs);

                    listfilm.add(phimhot);
                    Date myDate = new Date();
                    SimpleDateFormat sm = new SimpleDateFormat("mm-dd-yyyy");
                    // myDate is the java.util.Date in yyyy-mm-dd format
                    // Converting it into String using formatter
                    String strDate = sm.format(myDate);
                    if (year.equals("strDate")) {

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        PendingIntent Penmainhome = PendingIntent.getActivity(getContext(), 0, intent, 0);

                        count++;

                        Notification notification = new NotificationCompat.Builder(getContext())
                                .setContentTitle("Vua Phim hay")
                                .setContentText("" + " Thông báo  Phim mới")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setNumber(count)
                                .setAutoCancel(true)
                             .setContentIntent(Penmainhome)
                                //  .addAction(R.mipmap.ic_launcher,"Mở",Penmainhomedelect)
                                .build();
                        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, notification);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return listfilm;
        }

        @Override
        protected void onPostExecute(ArrayList<FilmMaster> values) {
            super.onPostExecute(values);
            pbloading.dismiss();
            customList = new CustomList(getContext(), R.layout.activity_customfilm, listfilm);
            gridView.setAdapter(customList);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "" + listfilm.get(position).getLink(), Toast.LENGTH_SHORT).show();




                        Intent intent = new Intent(getContext(), Activity_Content.class);
                        intent.putExtra("urls", listfilm.get(position).getLink());
                        intent.putExtra("name", listfilm.get(position).getName());
                        intent.putExtra("type", listfilm.get(position).getType());
                        intent.putExtra("year", listfilm.get(position).getYear());
                        intent.putExtra("decs", listfilm.get(position).getDecs());


                        startActivity(intent);

                }
            });
        }
    }
}