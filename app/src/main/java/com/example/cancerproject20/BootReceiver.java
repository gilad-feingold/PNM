package com.example.cancerproject20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Re-schedule the daily alarm
            Utils utils = new Utils();
            utils.scheduleDailyReminder(context);
        }
    }
}
