package ac.uk.brunel.sensor.dto;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:58 PM - 1/20/11
 */
public enum SensorItem {
    ACCELEROMETER(SensorConstants.TYPE_TEXT_ACCELEROMETER, "Measures the acceleration applied to the device.", "X", "Y", "Z"),
    MAGNETIC_FIELD(SensorConstants.TYPE_TEXT_MAGNETIC_FIELD, "Measures the ambient magnetic field in the X, Y and Z axis.", "X", "Y", "Z"),
    ORIENTATION(SensorConstants.TYPE_TEXT_ORIENTATION, "Values are angles in degrees.\nAzimuth = an angular measurement in a spherical coordinate system.", "Azimuth", "Pitch", "Roll"),
    PROXIMITY(SensorConstants.TYPE_TEXT_PROXIMITY, "Proximity sensor distance.", "Distance"),
    LIGHT(SensorConstants.TYPE_TEXT_LIGHT, "Ambient light level in SI lux units.", "Ambient light level");


    private final String name;
    private final String description;
    private final List<String> labels;

    private Sensor sensor = null;

    private SensorItem(final String name, final String description, final String ... labels) {
        this.name = name;
        this.description = description;
        this.labels = Arrays.asList(labels);
    }

    private void setSensorInstance(final Sensor sensor) {
        this.sensor = sensor;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public String getDescription() {
        return description;
    }

    public String getLabelText(int position) {
        String labelText = "";

        if(position < labels.size()) {
            labelText = labels.get(position);
        }

        return labelText;
    }

    public int getNumberOfLabels() {
        return labels.size();
    }

    public String toString() {
        final String listName;

        if(sensor == null) {
            listName = name + " - not available";
        } else {
            listName = name;
        }

        return listName;
    }
    
    public static void setSensor(final Sensor sensor) {
        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                SensorItem.ACCELEROMETER.setSensorInstance(sensor);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                SensorItem.MAGNETIC_FIELD.setSensorInstance(sensor);
                break;
            case Sensor.TYPE_ORIENTATION:
                SensorItem.ORIENTATION.setSensorInstance(sensor);
                break;
            case Sensor.TYPE_PROXIMITY:
                SensorItem.PROXIMITY.setSensorInstance(sensor);
                break;
            case Sensor.TYPE_LIGHT:
                SensorItem.LIGHT.setSensorInstance(sensor);
                break;
        }
    }
}
