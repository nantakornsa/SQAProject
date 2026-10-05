package org.apache.commons.lang.time;

import junit.framework.TestCase;

public class StopWatchTest extends TestCase {

    public StopWatchTest(String name) {
        super(name);
    }

    public void testLang315() {
        StopWatch watch = new StopWatch();
        watch.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            // ignore
        }
        watch.suspend();
        long suspendTime = watch.getTime();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            // ignore
        }
        watch.stop();
        long totalTime = watch.getTime();
        assertTrue(suspendTime == totalTime);
    }
}