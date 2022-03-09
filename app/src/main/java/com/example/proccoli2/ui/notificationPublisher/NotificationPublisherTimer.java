package com.example.proccoli2.ui.notificationPublisher;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.R;
import com.example.proccoli2.smileyFaceSurveyView;

public class NotificationPublisherTimer extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        if ((Intent.ACTION_BOOT_COMPLETED).equals(intent.getAction())){
            // reset all alarms
        }
        else{
            // perform your scheduled task here (eg. send alarm notification)
            Log.d("RECIEVED REMINDER", "onReceive: I RECIEVED A REMINDER");
            showNotification(context);
        }
    }

    private void showNotification(Context context) {
        final Intent intent = new Intent(context, smileyFaceSurveyView.class);
        //Need these intent attributes to be set in order for the back stack to be preserved
        //And for the smiley face survey view to be able to launch correctly
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"12345")
                .setSmallIcon(R.drawable.intro_foreground)
                .setContentTitle("Time's up!")
                .setContentText("Don't forget to set new timer!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
        Log.d("showNotification", "showNotification: finished build");

    }

}
