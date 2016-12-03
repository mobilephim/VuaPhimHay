package customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team2_mobilephim.team2_mobilephiem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import controller.FilmMaster;

/**
 * Created by Hoàng Thông on 13/11/2016.
 */

public class CustomList extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<FilmMaster> list;

    public CustomList(Context context, int resource, ArrayList<FilmMaster> list) {
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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

        TextView name = (TextView) convertView.findViewById(R.id.cus_name);
        TextView neww = (TextView) convertView.findViewById(R.id.type1);
        if (list.get(position).getYear().equals("2016")) {
            neww.setText("HOT *");

        }
        if (list.get(position).getYear().equals("2017")) {
            neww.setText("New ***");

        }
        TextView type = (TextView) convertView.findViewById(R.id.type);
        ImageView img = (ImageView) convertView.findViewById(R.id.cus_imageview);
        type.setText(list.get(position).getType());
        name.setText(list.get(position).getName());
        Picasso.with(context).load(list.get(position).getThumb()).resize(520, 480).centerCrop().into(img);
        return convertView;
    }

    public void setFilter(ArrayList<FilmMaster> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }


}
