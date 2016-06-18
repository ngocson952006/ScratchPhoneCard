package com.example.truongngoc.scratchphonecard.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.truongngoc.scratchphonecard.MainActivity;
import com.example.truongngoc.scratchphonecard.domain.AppConstants;

/**
 * Whenever the device receives a SMS , the service will catch the event do the desired work
 * Created by Truong Ngoc Son on 6/17/2016.
 */
public class SMSBoxReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // make sure the action of intent is valid
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {

            Toast.makeText(context, "Event caught", Toast.LENGTH_SHORT).show();

            // show notification to use the content of the message
            //Notification notification = NotificationManager
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
            notificationBuilder.setContentTitle("New message event");
            notificationBuilder.setContentText("You've received new SMS");

            Intent forwardIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, forwardIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            notificationBuilder.setContentIntent(pendingIntent);
            // get notification manager
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // start to notify user
            notificationManager.notify(AppConstants.SMS_RECEIVED_NOTIFICATION_ID, notificationBuilder.build());

        }
    }
}