package com.example.liebherr_365_gesundheitsapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

//Notification wird von einem service gestarten (läuf im Hintergrund)
public class Notification extends Service {

    //WICHTIG! Jede Notification bekommt eine eigene ID
    final int NOTIFICATION_ID = 16;

    public Notification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //hier wird Noticication ausgegeben.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Ausführung der Notification
        displayNotification("liebherr_365", "Wiegen nicht vergessen");
        stopSelf();
        //Hier wird die Nofification ausgesendet
        return super.onStartCommand(intent, flags, startId);
    }

    //Methode wie Notification ausgesendet wird, 2 Parameter Titel und Text für die Beschreibung der Notifivcation
    private void displayNotification(String title, String text){

        //Startet die MainAcrivity beim drücken der Notification
        Intent notificationIntent =new Intent(this, MainActivity.class);
        //Parameter die hinzugefügt werden können, z.B. von welchem Bereich der App die Notification kommt
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        //Nofification Einstellungen, Atribute der Notification
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.waage)
                //.setLargeIcon(BitmapFactory.decodeResource(R.mipmap.xyz))
                .setColor(getResources().getColor(R.color.colorAccent))
                .setVibrate(new long[]{0, 300, 300, 300})
                //.setSound()
                .setLights(Color.WHITE, 1000, 5000)
                //.setWhen(System.currentTimeMillis())
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle() .bigText(text));

        //Notification anzeigen, jede Notification braucht eine eigene ID
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }
}
