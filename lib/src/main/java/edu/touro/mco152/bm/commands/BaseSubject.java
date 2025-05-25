package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.Observer;
import edu.touro.mco152.bm.Subject;
import edu.touro.mco152.bm.persist.DiskRun;

import java.util.ArrayList;
import java.util.List;

/**
 * The BaseSubject class is a basic implementation of the Subject interface
 * containing its methods as well as a list to add observers to.
 */
public class BaseSubject implements Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    public BaseSubject() {

    }
    /**
     * Method that adds an observer to the list
     * @param o the observer that is added
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    /**
     * method that removes an observer from the list
     * @param o the observer that is removed
     */
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    /**
     * Tells the observers some piece of information
     * @param run the information the subject tells the observers
     */
    @Override
    public void notifyObservers(DiskRun run) {
        for (Observer o : observers){
            o.update(run);
        }
    }
}
