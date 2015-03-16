package com.example.android.newsvocabdictionary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.newsvocab.dictionary.R;

/**
 * Created by Android on 11-03-2015.
 */
public class MyNotification extends Service {
    private static final int NOTIFY_ME_ID=1337;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
      //  Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
      //  Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_SHORT).show();


        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification note=new Notification(R.drawable.icon_home, "News Vocab",
                System.currentTimeMillis());

        // This pending intent will open after notification click
        PendingIntent i=PendingIntent.getActivity(this, 0,
                new Intent(this, WordofDayActivity.class),
                0);

        note.setLatestEventInfo(this, "News Vocab",
                "New Words for you !!!", i);

        //After uncomment this line you will see number of notification arrived
        //note.number=2;
        note.flags = Notification.FLAG_AUTO_CANCEL;
        mgr.notify(NOTIFY_ME_ID, note);



    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

}
