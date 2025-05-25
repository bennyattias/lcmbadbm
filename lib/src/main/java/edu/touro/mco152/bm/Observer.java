package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * The Observer interface, which is the part of the Observer pattern
 * that is notified of something by the subject
 */

public interface Observer {

    /**
     * The method the subject calls to notify the observer of something
     * @param run this is the DiskRun object containing all the run info
     */
    void update(DiskRun run);
}
