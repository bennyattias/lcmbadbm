package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * Class TestObserver is a Mock observer used in JUnit testing
 * to test whether the subject properly registers and notifies its observers
 */
public class TestObserver implements Observer {

    private static boolean testPassed = false;
    /**
     * The method the subject calls to notify the observer of something
     *
     * @param run this is the DiskRun object containing all the run info
     */
    @Override
    public void update(DiskRun run) {
        testPassed = true;
    }
    /**
     * @return value of testPassed boolean
     */
    public static boolean isTestPassed() {
        return testPassed;
    }
}
