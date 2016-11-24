package customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.team2_mobilephim.team2_mobilephiem.R;

import java.util.ArrayList;

import controller.TapPhim;

/**
 * Created by Hoàng Thông on 24/11/2016.
 */

public class CustomTapPhim extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<TapPhim> phims = new ArrayList<>();

    public CustomTapPhim(Context context, int resource, ArrayList<TapPhim> phims) {
        this.context = context;
        this.resource = resource;
        this.phims = phims;
    }

    @Override
    public int getCount() {
        return phims.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource, parent, false);
        TextView tap = (TextView) convertView.findViewById(R.id.cus_tapphim);
        tap.setText(phims.get(position).getTentap());
        return convertView;
    }
}
