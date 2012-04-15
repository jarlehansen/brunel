package ac.uk.brunel.sensor;

import ac.uk.brunel.sensor.dto.SensorConstants;
import ac.uk.brunel.sensor.dto.SensorItem;
import android.app.ListActivity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 3:32 PM - 1/20/11
 */
public class SensorListActivity extends ListActivity {
    private final String TAG = SensorListActivity.class.getName();

    private ArrayAdapter<SensorItem> arrayAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_list);

        initAllSensors();
    }

    public void initAllSensors() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        Log.i(TAG, "Sensors found: " + sensors.toString());
        
        for (Sensor sensor : sensors) {
            SensorItem.setSensor(sensor);
        }

        arrayAdapter = new ArrayAdapter<SensorItem>(this,
                R.layout.sensor_row,
                R.id.row_text,
                SensorItem.values());

        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, SensorMonitor.class);
        intent.putExtra(SensorConstants.INTENT_KEY_SENSOR, arrayAdapter.getItem(position).name());

        startActivity(intent);
    }
}