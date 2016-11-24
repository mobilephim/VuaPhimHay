package customadapter;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by tuandeptrai on 20/11/2016.
 */

public class Loadingphim {
    public void LaunchBatDiaLog(Context context){
        final ProgressDialog ringProgressDialog = ProgressDialog.show(context,"","Đang tải phim chờ xíu nhé..",true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ringProgressDialog.dismiss();
            }
        }).start();

    }
}
