package ac.uk.brunel.view.adapter;

import java.util.ArrayList;

import ac.uk.brunel.dto.ApplicationDto;
import ac.uk.brunel.view.adapterview.ApplicationsAdapterView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ApplicationsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ApplicationDto> applications;

	public ApplicationsAdapter(Context context, ArrayList<ApplicationDto> applications) {
		this.context = context;
		this.applications = applications;
	}

	@Override
	public int getCount() {
		return applications.size();
	}

	@Override
	public Object getItem(int index) {
		return applications.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		return new ApplicationsAdapterView(context, applications.get(index)) ;
	}

}
