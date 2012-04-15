package ac.uk.brunel.dto;

import android.util.Log;
import android.view.View;

public class ApplicationDto implements View.OnClickListener {
	private String name;
	private int imageResource;
	
	public ApplicationDto(String name, int imageResource) {
		setName(name);
		setImageResource(imageResource);


	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImageResource() {
		return imageResource;
	}

	public void setImageResource(int imageResource) {
		this.imageResource = imageResource;
	}


    public void onClick(View view) {
        Log.i("tmg","test " + this.getName());
    }
}
