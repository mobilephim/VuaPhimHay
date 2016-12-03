package com.example.team2_mobilephim.team2_mobilephiem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Activity_xemphim extends AppCompatActivity {
    TextView ten, ten1, theloai, nam, mota;
    ImageView img;
    Button btnxemphim;
    String name, type, year, decs, thumb, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemphim);
        ten = (TextView) findViewById(R.id.txttenphim);
        ten1 = (TextView) findViewById(R.id.txttenphim1);
        theloai = (TextView) findViewById(R.id.txttheloai);
        nam = (TextView) findViewById(R.id.txtnam);
        mota = (TextView) findViewById(R.id.txtnoidung);
        img = (ImageView) findViewById(R.id.imageView2);
        btnxemphim = (Button) findViewById(R.id.btnxemphim);

        ten1.setText(name);
        name = getIntent().getStringExtra("name");
        thumb = getIntent().getStringExtra("thumb");
        ten.setText(name);
        type = getIntent().getStringExtra("type");
        theloai.setText(type);
        year = getIntent().getStringExtra("year");
        nam.setText(year);
        decs = getIntent().getStringExtra("decs");
        url = getIntent().getStringExtra("urls");
        Log.d("aaaa", "" + url);
        mota.setText(decs);
        Picasso.with(getApplicationContext()).load(thumb).resize(340, 450).centerCrop().into(img);

        btnxemphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Content.class);
                intent.putExtra("urls", url);
                intent.putExtra("name", name);

                intent.putExtra("type", type);
                intent.putExtra("year", year);
                intent.putExtra("decs", decs);

//                FilmMaster filmMaster = new FilmMaster();
//                int ID =0;
//                int luotxem = 0;
//                filmMaster.setiD(ID);


                startActivity(intent);
            }
        });
    }

}
