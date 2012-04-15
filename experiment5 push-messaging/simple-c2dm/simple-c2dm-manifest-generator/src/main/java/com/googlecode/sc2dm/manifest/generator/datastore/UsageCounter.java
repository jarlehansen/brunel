package com.googlecode.sc2dm.manifest.generator.datastore;

import javax.persistence.Id;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:50 PM - 9/27/11
 */
class UsageCounter {
    @Id
    private String id = "Counter";
    private long counter;

    public UsageCounter() {
    }

    public UsageCounter(long counter) {
        this.counter = counter;
    }

    public synchronized void increase() {
        counter++;
    }
}
