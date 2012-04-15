package ac.uk.brunel.sensor;

import ac.uk.brunel.sensor.dto.SensorConstants;
import ac.uk.brunel.sensor.dto.SensorItem;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SensorMonitor extends Activity implements SensorEventListener {
    private static final String TAG = SensorMonitor.class.getName();

    private final DecimalFormat decimalFormat = new DecimalFormat("######0.00000");
    private SensorManager sensorManager = null;
    private SensorItem sensorItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor);

        sensorItem = SensorItem.valueOf(getIntent().getStringExtra(SensorConstants.INTENT_KEY_SENSOR));
        Log.i(TAG, "Sensor loaded: " + sensorItem.name());

        TextView sensorText = (TextView) findViewById(R.id.sensor_text);
        sensorText.setText(sensorItem.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorItem.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);

        TextView descriptionText = (TextView) findViewById(R.id.descriptionText);
        descriptionText.setText(sensorItem.getDescription());

        TextView statusText = (TextView) findViewById(R.id.statusText);
        statusText.setText(sensorItem.getLabelText(0));
    }

    @Override
    protected void onPause() {
        super.onPause();
        
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sensorEvent.values.length; i++) {
            float sensorValue = sensorEvent.values[i];

            if (sensorItem.getNumberOfLabels() > i) {
                sb.append(sensorItem.getLabelText(i)).append("\n");
                sb.append(decimalFormat.format(sensorValue)).append("\n\n");
            }

            if (sensorItem.getSensor().getType() == Sensor.TYPE_LIGHT) {
                changeBackground(sensorValue);
            }
        }

        TextView statusText = (TextView) findViewById(R.id.statusText);
        statusText.setText(sb.toString());
    }

    private void changeBackground(final float sensorValue) {
        View background = findViewById(R.id.sensorLayout);

        if (Float.compare(sensorValue, 0F) > 0) {
            setInitialTextColor();

            int color;

            if (Float.compare(sensorValue, 100F) <= 0) {
                color = Color.rgb(255, 250, 250);
            } else if (Float.compare(sensorValue, 200F) <= 0) {
                color = Color.rgb(238, 233, 233);
            } else if (Float.compare(sensorValue, 300F) <= 0) {
                color = Color.rgb(211, 206, 206);
            } else if (Float.compare(sensorValue, 400F) <= 0) {
                color = Color.rgb(184, 179, 179);
            } else if (Float.compare(sensorValue, 500F) <= 0) {
                color = Color.rgb(175, 170, 170);
            } else if (Float.compare(sensorValue, 600F) <= 0) {
                color = Color.rgb(155, 150, 150);
            } else if (Float.compare(sensorValue, 700F) <= 0) {
                color = Color.rgb(153, 148, 148);
            } else if (Float.compare(sensorValue, 800F) <= 0) {
                color = Color.rgb(136, 131, 131);
            } else if (Float.compare(sensorValue, 900F) <= 0) {
                color = Color.rgb(119, 114, 114);
            } else if (Float.compare(sensorValue, 1000F) <= 0) {
                color = Color.rgb(102, 97, 97);
            } else if (Float.compare(sensorValue, 1100F) <= 0) {
                color = Color.rgb(85, 80, 80);
            } else if (Float.compare(sensorValue, 1200F) <= 0) {
                color = Color.rgb(68, 63, 63);
            } else if (Float.compare(sensorValue, 1300F) <= 0) {
                color = Color.rgb(52, 47, 47);
                setDarkTextColor();
            } else if (Float.compare(sensorValue, 1400F) <= 0) {
                color = Color.rgb(35, 30, 30);
                setDarkTextColor();
            } else if (Float.compare(sensorValue, 1500F) <= 0) {
                color = Color.rgb(30, 25, 25);
                setDarkTextColor();
            } else if (Float.compare(sensorValue, 2600F) <= 0) {
                color = Color.rgb(20, 15, 15);
                setDarkTextColor();
            } else {
                color = Color.rgb(0, 0, 0);
                setDarkTextColor();
            }

            background.setBackgroundColor(color);
        }
    }

    private void setInitialTextColor() {
        TextView sensorText = (TextView) findViewById(R.id.sensor_text);
        sensorText.setTextColor(Color.BLACK);
        TextView statusText = (TextView) findViewById(R.id.statusText);
        statusText.setTextColor(Color.BLACK);
        TextView descriptionText = (TextView) findViewById(R.id.descriptionText);
        descriptionText.setTextColor(Color.BLACK);
    }

    private void setDarkTextColor() {
        TextView sensorText = (TextView) findViewById(R.id.sensor_text);
        sensorText.setTextColor(Color.WHITE);
        TextView statusText = (TextView) findViewById(R.id.statusText);
        statusText.setTextColor(Color.WHITE);
        TextView descriptionText = (TextView) findViewById(R.id.descriptionText);
        descriptionText.setTextColor(Color.WHITE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
