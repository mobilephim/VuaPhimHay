package com.example.team2_mobilephim.team2_mobilephiem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import controller.FilmMaster;
import customadapter.CustomList;

public class Activity_HanhDong extends android.support.v4.app.Fragment {
    ArrayList<FilmMaster> listfilm = new ArrayList<>();
    private SearchView searchView;
    GridView gridView ;
    CustomList customList;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_hanh_dong,container,false);

        gridView=(GridView)view.findViewById(R.id.gridView_hanhdong);
        new Activity_HanhDong.DogetData().execute("http://hoangthong.website/app/");

        return view;

    }
    class DogetData extends AsyncTask<String,Integer,ArrayList<FilmMaster>> {
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
                result="";
                int byteCharacter;
                while((byteCharacter = is.read())!= -1){
                    result += (char) byteCharacter;


                }

                JSONArray jsonArray = new JSONArray(result);
                String chuoi="";
                int length = jsonArray.length();
                for(int i = 0 ; i< jsonArray.length(); i++){
                    JSONObject jsonObject= jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String link = jsonObject.getString("thumb");
                    String type = jsonObject.getString("type");

                    String year = jsonObject.getString("year");
                    String decs = jsonObject.getString("decs");
                    if(type.equals("Phim Hành Động")){

                        FilmMaster phimhot = new FilmMaster();
                        phimhot.setName(name);
                        phimhot.setThumb(link);
                        phimhot.setType(type);
                        phimhot.setYear(year);
                        phimhot.setDecs(decs);

                        listfilm.add(phimhot);


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
            customList = new CustomList(getContext(),R.layout.activity_customfilm,listfilm);
            gridView.setAdapter(customList);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "" + listfilm.get(position).getLink(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Activity_Content.class);
                    intent.putExtra("urls", listfilm.get(position).getLink());
                    intent.putExtra("name",listfilm.get(position).getName());

                    intent.putExtra("type",listfilm.get(position).getType());
                    intent.putExtra("year",listfilm.get(position).getYear());
                    intent.putExtra("decs",listfilm.get(position).getDecs());

                    startActivity(intent);
                }
            });



        }



    }
}
