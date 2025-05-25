package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.dataDir;

public class SwingUI extends SwingWorker<Boolean, DiskMark> implements AbstractDiskWorker{
    private boolean lastStatus = false;
    private DiskWorker diskWorker;

    public SwingUI() {
    this.diskWorker = new DiskWorker(SwingUI.this);
    }
    @Override
    public void startProgram() {
        execute();
    }

    @Override
    public void newPropertyChangeListener(PropertyChangeListener listener) {
        addPropertyChangeListener(listener);
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        return diskWorker.startBenchmark();
    }

    /**
     * Process a list of 'chunks' that have been processed, ie that our thread has previously
     * published to Swing. For my info, watch Professor Cohen's video -
     * Module_6_RefactorBadBM Swing_DiskWorker_Tutorial.mp4
     * @param markList a list of DiskMark objects reflecting some completed benchmarks
     */

    @Override
    protected void process(List<DiskMark> markList) {
        markList.stream().forEach((dm) -> {
            if (dm.type == DiskMark.MarkType.WRITE) {
                Gui.addWriteMark(dm);
            } else {
                Gui.addReadMark(dm);
            }
        });
    }

    @Override
    protected void done() {
        // Obtain final status, might from doInBackground ret value, or SwingWorker error
        try {
            lastStatus = super.get();   // record for future access
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).warning("Problem obtaining final status: " + e.getMessage());
        }

        if (App.autoRemoveData) {
            Util.deleteDirectory(dataDir);
        }
        App.state = App.State.IDLE_STATE;
        Gui.mainFrame.adjustSensitivity();
    }


    @Override
    public void setUpProgress(int progress) {
        setProgress(progress);
    }

    @Override
    public void publishUpResults(DiskMark results) {
        publish(results);
    }

    @Override
    public boolean isCancelledUp() {
        return isCancelled();
    }

    @Override
    public boolean cancelUp(boolean mayInterruptIfRunning) {
        return cancel(true);
    }
}
