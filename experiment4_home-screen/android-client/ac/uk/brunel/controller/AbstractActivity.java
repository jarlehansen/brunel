package ac.uk.brunel.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.WindowManager;

public abstract class AbstractActivity extends Activity {
	

	protected Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setBackgroundDrawable(null);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		
		context = this;
		
		initComponents();
		renderView();
		initListeners();
	}
	
	protected abstract void initComponents();
	protected abstract void renderView();
	protected abstract void initListeners();
}
