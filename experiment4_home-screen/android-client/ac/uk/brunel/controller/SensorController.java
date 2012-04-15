package ac.uk.brunel.controller;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;

/**
 * Created by :
 * User: tmg
 * Date: 11
 */
public class SensorController extends Activity {

    public SensorController() {

    }

    private void initSensors() {
        int a = SensorManager.SENSOR_LIGHT;

        SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor sen : sensorList) {
            Log.i("tmg","Sensor:  " +sen.getName());
        }

        Sensor lightSensor = (Sensor) mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);



        
    }
}
