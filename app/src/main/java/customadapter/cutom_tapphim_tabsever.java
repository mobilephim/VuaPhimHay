package customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.team2_mobilephim.team2_mobilephiem.R;

import java.util.ArrayList;

/**
 * Created by tuandeptrai on 04/12/2016.
 */

public class cutom_tapphim_tabsever extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<String> phims = new ArrayList<>();

    public cutom_tapphim_tabsever(Context context, int resource, ArrayList<String> phims) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource, parent, false);
        Button btn = (Button) convertView.findViewById(R.id.button);
        btn.setText("Tập"+(position+1));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,phims.get(position),Toast.LENGTH_SHORT).show();

            }
        });


        return convertView;
    }
}
