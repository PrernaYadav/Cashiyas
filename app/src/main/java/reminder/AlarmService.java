package reminder;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Created by Neeraj Sain on 7/20/2016.
 */
public class AlarmService extends Service
{

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        registerReceiver(new AlarmReceiver(),new IntentFilter(Intent.ACTION_TIME_TICK) );
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {

        // sendNotification(Field.messages);

    }

    @Override
    public void onDestroy() {
        startService(new Intent(getApplicationContext(), AlarmService.class));
    }


//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        Intent restartService = new Intent(getApplicationContext(),
//                this.getClass());
//        restartService.setPackage(getPackageName());
//        PendingIntent restartServicePI = PendingIntent.getService(
//                getApplicationContext(), 1, restartService,
//                PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);
//
//    }

}
