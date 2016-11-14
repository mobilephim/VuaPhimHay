package com.example.team2_mobilephim.team2_mobilephiem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tuandeptrai on 12/11/2016.
 */

public class Phim_hot extends android.support.v4.app.Fragment {

Button btncheckNetWork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.layout_phimhot,container,false);




        return view;



    }
    // tao dialog


}