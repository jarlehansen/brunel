package com.googlecode.sc2dm.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.googlecode.sc2dm.SC2DMAnnotatedMessages;
import com.googlecode.sc2dm.SC2DMUtils;
import com.googlecode.sc2dm.activity.annotation.AnnotatedCallback;
import com.googlecode.sc2dm.log.SC2DMLogger;

public class AnnotatedMessagesActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SC2DMLogger.printActiveLogLevels();
        SC2DMLogger.i("Running annotated callback");
        Toast.makeText(this, "Running annotated callback", 2500).show();

        String message = SC2DMUtils.getNotificationMessage(getIntent());
        if (!"".equals(message)) {
            SC2DMLogger.i("Message from intent: ", message);
            Toast.makeText(this, message, 5000).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SC2DMUtils.getRegisteredGoogleAccounts(this));
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String email = (String) getListAdapter().getItem(position);
        startSC2DM(email);
    }

    private void startSC2DM(String email) {
        Toast.makeText(this, "SC2DM with: " + email, 5000).show();
        SC2DMLogger.i("Starting SC2DM with: ", email);
        SC2DMAnnotatedMessages pushMessages = new SC2DMAnnotatedMessages(this, email);
        pushMessages.registerAnnotatedClasses(AnnotatedCallback.class);

        pushMessages.enable();
    }
}
