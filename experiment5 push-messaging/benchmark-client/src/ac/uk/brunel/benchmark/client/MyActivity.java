package ac.uk.brunel.benchmark.client;

import ac.uk.brunel.benchmark.client.battery.BatteryLevel;
import ac.uk.brunel.benchmark.client.task.urbanairship.UrbanAirshipTask;
import ac.uk.brunel.benchmark.client.task.xmpp.XMPPTask;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.googlecode.sc2dm.log.SC2DMLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MyActivity extends Activity {
    private static final int TIMER_DELAY = 60000;
    //    private static final int TIMER_PERIOD = 1200000; // 20 minutes
    //    private static final int TIMER_PERIOD = 900000; // 15 minutes
    private static final int TIMER_PERIOD = 600000; // 10 minutes
    //        private static final int TIMER_PERIOD = 300000; // 5 minutes
    //    private static final int TIMER_PERIOD = 120000; // 2 minutes

    private boolean started = false;
    private final Map<Timer, TimerTask> timers = new HashMap<Timer, TimerTask>();
    private final Handler handler = new Handler();
    private Runnable runnable = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        updateFields();

        if (!started) {
            started = true;
            final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            final PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Benchmark Client");
            wl.acquire();

            IntentFilter batteryLevelIntent = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

            // Add all timers that should be started

            /* C2DM */
//            addTimer("c2dm-timer", new C2DMTask(this));
//            registerReceiver(new BatteryLevel("C2DM"), batteryLevelIntent);

            /* XMPP */
            addTimer("xmpp-timer", new XMPPTask(this));
            registerReceiver(new BatteryLevel("XMPP"), batteryLevelIntent);

            /* Urban Airship */
//            addTimer("urbanairship-timer", new UrbanAirshipTask(this));
//            registerReceiver(new BatteryLevel("UAir"), batteryLevelIntent);

            /* Xtify */
//            addTimer("xtify-timer", new XtifyTask(this));

            createStateUpdater();

            Button startButton = (Button) findViewById(R.id.startButton);
            startButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startTimers();
                }
            });

            Button exitButton = (Button) findViewById(R.id.exitButton);
            exitButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (Map.Entry<Timer, TimerTask> entry : timers.entrySet()) {
                        SC2DMLogger.i("Stopping timer: ", entry.getValue().toString());
                        entry.getKey().cancel();
                    }

                    if (runnable != null)
                        handler.removeCallbacks(runnable);

                    wl.release();
                    finish();
                }
            });
        }
    }

    private void updateFields() {
        ((TextView) findViewById(R.id.period)).setText("" + (new Double(TIMER_PERIOD / 1000) / new Double(60)) + " minute(s)");
        ((TextView) findViewById(R.id.email)).setText("" + UserInfo.EMAIL);
    }

    private void addTimer(String name, TimerTask timerTask) {
        final Timer timer = new Timer(name, true);
        timers.put(timer, timerTask);
    }

    private void startTimers() {
        int numOfTimers = 0;
        for (Map.Entry<Timer, TimerTask> entry : timers.entrySet()) {
            Timer timer = entry.getKey();
            timer.scheduleAtFixedRate(entry.getValue(), (TIMER_DELAY + (numOfTimers * 300000)), TIMER_PERIOD);

            numOfTimers++;
        }
    }

    private void createStateUpdater() {
        runnable = new Runnable() {

            @Override
            public void run() {
                TaskStateApplication taskState = (TaskStateApplication) getApplication();

                TextView c2dmMessages = (TextView) findViewById(R.id.c2dm);
                c2dmMessages.setText(taskState.getC2dmMessages());

                TextView c2dmRegId = (TextView) findViewById(R.id.c2dmRegId);
                c2dmRegId.setText(taskState.getC2dmRegId());

                TextView xmppMessages = (TextView) findViewById(R.id.xmpp);
                xmppMessages.setText(taskState.getXmppMessages());

                TextView uaMessages = (TextView) findViewById(R.id.ua);
                uaMessages.setText(taskState.getUAMessages());

                TextView uaRegId = (TextView) findViewById(R.id.uaRegId);
                uaRegId.setText(taskState.getUaRegId());

                TextView xtifyMessages = (TextView) findViewById(R.id.xtify);
                xtifyMessages.setText(taskState.getXtifyMessages());

                handler.postDelayed(this, 10000);
            }
        };

        handler.postDelayed(runnable, 15000);
    }
}
