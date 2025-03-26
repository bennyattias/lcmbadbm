package edu.touro.mco152.bm;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.List;

public interface AbstractDiskWorker {

    void startProgram();

    void newPropertyChangeListener(PropertyChangeListener listener);

    void setUpProgress(int progress);

    void publishUpResults(DiskMark results);

    boolean isCancelledUp();

    void addPropertyChangeListener(PropertyChangeListener listener);

    boolean cancelUp(boolean mayInterruptIfRunning);


}
