package com.googlecode.sc2dm.manifest.generator.datastore;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import java.text.SimpleDateFormat;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:49 PM - 9/27/11
 */
public class UsageCounterDaoImpl implements UsageCounterDao {
    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss,SSS - dd/MM/yyyy");

    static {
        ObjectifyService.register(UsageCounter.class);
    }

    public void storeStats() {
        Objectify objectify = ObjectifyService.begin();

        UsageCounter usageCounter;

        try {
            usageCounter = objectify.get(UsageCounter.class, "Counter");
            usageCounter.increase();
        } catch (NotFoundException nfe) {
            usageCounter = new UsageCounter(1);
        }

        objectify.put(usageCounter);
    }

}
