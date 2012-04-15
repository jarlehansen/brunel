package ac.uk.brunel.view;

import ac.uk.brunel.controller.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabButton extends LinearLayout{
	
	private ImageView imgTabButton;
	private TextView lblTabButton;
	private LinearLayout tabItemLayout, layoutTab;
	private Context context;
	
	public TabButton(Context context, int resource, String text){
		super(context);
		this.context = context;
		
		initLayout(context);
		renderItem(resource, text);
		setBackground(false);
	}

	private void initLayout(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tabItemLayout = (LinearLayout) layoutInflater.inflate(R.layout.tab_button, this);
		
		layoutTab = (LinearLayout) tabItemLayout.findViewById(R.id.layoutTab);
		imgTabButton = (ImageView) tabItemLayout.findViewById(R.id.imgTabButton);
		lblTabButton = (TextView) tabItemLayout.findViewById(R.id.lblTabButton);
	}

	private void renderItem(int resource, String text) {
		imgTabButton.setBackgroundResource(resource);
		lblTabButton.setText(text);		
	}
	
	public void setBackground(boolean isFocused){
		if(isFocused){
			layoutTab.setBackgroundResource(R.drawable.tab_selected);
		}else {
			layoutTab.setBackgroundResource(R.color.transparent);
		}
	}
}
