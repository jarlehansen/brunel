package ac.uk.brunel.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import ac.uk.brunel.dto.ApplicationDto;
import ac.uk.brunel.view.adapter.ApplicationsAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

public class AppDashboardController extends AbstractActivity {

	private ListView lstApps;
	private GridView gridApps;
    private ArrayList<ApplicationDto> applications;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.appdashboard);
		super.onCreate(savedInstanceState);
        
	}

	@Override
	protected void initComponents() {
		lstApps = (ListView) findViewById(R.id.lstApps);
		gridApps = (GridView) findViewById(R.id.gridApps);
	}

	@Override
	protected void renderView() {
		applications = new ArrayList<ApplicationDto>(Arrays.asList(
				new ApplicationDto("Calendar", R.drawable.calendar_app_b),
				new ApplicationDto("Gmail", R.drawable.gmail_app),
				new ApplicationDto("Settings", R.drawable.settings_app),
				new ApplicationDto("Gallery", R.drawable.gmail_app),
				new ApplicationDto("Comming", R.drawable.not_avail),
				new ApplicationDto("Comming", R.drawable.not_avail),
				new ApplicationDto("Comming", R.drawable.not_avail)));

		lstApps.setAdapter(new ApplicationsAdapter(context, applications));
		
		gridApps.setAdapter(new ApplicationsAdapter(context, applications));
	}

	@Override
	protected void initListeners() {
        gridApps.setOnItemClickListener(new ApplicationGridItemClickListener());
	}

     private class ApplicationGridItemClickListener implements GridView.OnItemClickListener{
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.i("tmg", "Position: " +i +" Class name: "+ applications.get(i).getName());

            if(applications.get(i).getName().equalsIgnoreCase("calendar")) {
                Intent intent = new Intent();
                //intent.setAction(Intent.ACTION_VIEW, Contacts.People.CONTENT_URI);
                //calendar not part of public google api(!) add a substitute app?
            }
            else if(applications.get(i).getName().equalsIgnoreCase("gmail")) {
                //launch intent
                //calendar not part of public google api(!) add a substitute app?
            }
            else if(applications.get(i).getName().equalsIgnoreCase("gallery")) {
                //launch intent
                //calendar not part of public google api(!) add a substitute app?
            }
        }
    }

}