package edu.touro.mco152.bm;


import edu.touro.mco152.bm.persist.DiskRun;

/**
 * The subject interface, the part of the Observer pattern
 * which notifies the observers of something. Here, it will be of the
 * run info from a benchmark
 */
public interface Subject {
    /**
     * Method that adds an observer to the list
     * @param o the observer that is added
     */
    public void registerObserver(Observer o);

    /**
     * method that removes an observer from the list
     * @param o the observer that is removed
     */
    public void removeObserver(Observer o);

    /**
     * Tells the observers some piece of information
     * @param run the information the subject tells the observers
     */
    public void notifyObservers(DiskRun run);
}
