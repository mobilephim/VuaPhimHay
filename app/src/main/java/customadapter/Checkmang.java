package customadapter;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team2_mobilephim.team2_mobilephiem.R;

/**
 * Created by tuandeptrai on 03/12/2016.
 */

public class Checkmang {
    public boolean checkInternetConnection(Context context) {
        // Lấy ra bộ quản lý kết nối.
        ConnectivityManager connManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Thông tin mạng đang kích hoạt.
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null|| !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialogmang);
            dialog.setTitle("Loanding Thất bại ");

            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.text);
            text.setText(" Hãy Mở kết nối mạng và thử lại ");
            ImageView image = (ImageView) dialog.findViewById(R.id.image);
            image.setImageResource(R.drawable.ic_collections_2x);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            return false;
        }



        return true;
    }
}
