package com.googlecode.sc2dm;

import android.content.Context;
import com.googlecode.sc2dm.factory.SC2DMFactory;
import com.googlecode.sc2dm.factory.SC2DMFactoryImpl;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 11:40 AM - 10/6/11
 */
public class SC2DMDirectMessages implements PushMessages {
    private final Context context;
    private final SC2DMFactory factory = new SC2DMFactoryImpl();

    public SC2DMDirectMessages(Context context, String email, SC2DMCallback callbackClass) {
        // Make sure nothing is added to the SC2DM object
        SC2DM.INSTANCE.reset();

        this.context = context;

        SC2DM.INSTANCE.setEmail(email);
        SC2DM.INSTANCE.setSC2DMCallback(callbackClass);
    }

    @Override
    public void enable() {
        SC2DM.INSTANCE.deviceRegistration().register(context, SC2DM.INSTANCE.email());
    }
}
