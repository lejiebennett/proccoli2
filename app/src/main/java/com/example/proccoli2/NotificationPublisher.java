package com.example.proccoli2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.proccoli2.MainActivity;

/**
 * https://gist.github.com/BrandonSmith/6679223
 */
public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        if ((Intent.ACTION_BOOT_COMPLETED).equals(intent.getAction())){
            // reset all alarms
        }
        else{
            String bigGoal = (String) intent.getExtras().get("bigGoal");
            // perform your scheduled task here (eg. send alarm notification)
            Log.d("RECIEVED REMINDER", "onReceive: I RECIEVED A REMINDER");
            showNotification(context, bigGoal);

        }
    }

    private void showNotification(Context context,String content) {
        //THIS IS ALL CORRECT and IS PUSHED AND COMMITTED
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"12345")
                .setSmallIcon(R.drawable.intro_foreground)
                .setContentTitle("Are you ready to study " + content)
                .setContentText("Don't forget to set your timer while studying!")
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

