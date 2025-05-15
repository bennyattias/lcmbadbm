package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Properties;

class AbstractDiskWorkerTest implements AbstractDiskWorker{
    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */



    private static void setupDefaultAsPerProperties()
    {
        /// Do the minimum of what  App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();

        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference

        // may be null when tests not run in original proj dir, so use a default area
        if (App.locationDir == null) {
            App.locationDir = new File(System.getProperty("user.home"));
        }

        App.dataDir = new File(App.locationDir.getAbsolutePath()+File.separator+App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        }
        else
        {
            App.dataDir.mkdirs(); // create data dir if not already present
        }
    }

    public void readWriteMessage(){
        System.out.println("For valid READ measurements please clear the disk cache by\n" +
                "                            using the included RAMMap.exe or flushmem.exe utilities.\n" +
                "                            Removable drives can be disconnected and reconnected.\n" +
                "                            For system drives use the WRITE and READ operationss\n" +
                "                            independantly by doing a cold reboot after the WRITE");
    }

    @Override
    public void startProgram() {

    }

    @Override
    public void newPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void setUpProgress(int progress) {

    }

    @Override
    public void publishUpResults(DiskMark results) {

    }

    @Override
    public boolean isCancelledUp() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public boolean cancelUp(boolean mayInterruptIfRunning) {
        return false;
    }
}
