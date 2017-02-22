package com.test.emptydemo;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isDialog = intent.getBooleanExtra(ThirdActivity.DIALOG, false);
        if (isDialog){

            AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("我來自服务").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
            Window window = alertDialog.getWindow();
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alertDialog.show();

        }else{

            Toast.makeText(this, "from service", Toast.LENGTH_LONG).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
