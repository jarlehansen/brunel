package ac.uk.brunel.view.adapterview;

import ac.uk.brunel.controller.R;
import ac.uk.brunel.dto.ApplicationDto;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ApplicationsAdapterView extends RelativeLayout{
	
	private TextView lblListItemApplication;
	private ImageView imgListItemApplication;
	
	public ApplicationsAdapterView(Context context, ApplicationDto application) {
		super(context);
		
		initLayout(context);
		renderItem(application);
	}

	private void initLayout(Context context) {

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout applicationListItemLayout = (RelativeLayout) layoutInflater.inflate(R.layout.grid_item_application, this);
	
		lblListItemApplication = (TextView) applicationListItemLayout.findViewById(R.id.lblListItemApplication);
		imgListItemApplication = (ImageView) applicationListItemLayout.findViewById(R.id.imgListItemApplication);
	}

	private void renderItem(ApplicationDto application) {
		lblListItemApplication.setText(application.getName());
		imgListItemApplication.setBackgroundResource(application.getImageResource());
	}
	
	

}
