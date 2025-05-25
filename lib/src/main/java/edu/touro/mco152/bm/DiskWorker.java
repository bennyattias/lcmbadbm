package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.Executor;
import edu.touro.mco152.bm.commands.ReadTest;
import edu.touro.mco152.bm.commands.WriteTest;
import edu.touro.mco152.bm.persist.DatabaseObserver;
import edu.touro.mco152.bm.ui.Gui;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;

/**
 * Run the disk benchmarking as a Swing-compliant thread (only one of these threads can run at
 * once.) Cooperates with Swing to provide and make use of interim and final progress and
 * information, which is also recorded as needed to the persistence store, and log.
 * <p>
 * Depends on static values that describe the benchmark to be done having been set in App and Gui classes.
 * The DiskRun class is used to keep track of and persist info about each benchmark at a higher level (a run),
 * while the DiskMark class described each iteration's result, which is displayed by the UI as the benchmark run
 * progresses.
 * <p>
 * This class only knows how to do 'read' or 'write' disk benchmarks. It is instantiated by the
 * startBenchmark() method.
 * <p>
 * To be Swing compliant this class extends SwingWorker and declares that its final return (when
 * doInBackground() is finished) is of type Boolean, and declares that intermediate results are communicated to
 * Swing using an instance of the DiskMark class.
 * <p>
 * In the command pattern, this class id the Client that sets up the executor which takes in concrete commands
 * and calls their execute() method
 * </p>
 * In the Observer pattern, this class is the client that sets up the subject(s),
 * adds observers to the subject's list, and calls the subject's notifyObservers method.
 */

public class DiskWorker {

    // Record any success or failure status returned from SwingWorker (might be us or super)
    Boolean lastStatus = null;  // so far unknown
    AbstractDiskWorker diskWorker;

    public DiskWorker(AbstractDiskWorker diskWorker) {
        this.diskWorker = diskWorker;
    }

    protected Boolean startBenchmark() throws Exception {

        /*
          We 'got here' because: 1: End-user clicked 'Start' on the benchmark UI,
          which triggered the start-benchmark event associated with the App::startBenchmark()
          method.  2: startBenchmark() then instantiated a DiskWorker, and called
          its (super class's) execute() method, causing Swing to eventually
          call this doInBackground() method.
         */
        Logger.getLogger(App.class.getName()).log(Level.INFO, "*** New worker thread started ***");
        msg("Running readTest " + App.readTest + "   writeTest " + App.writeTest);
        msg("num files: " + App.numOfMarks + ", num blks: " + App.numOfBlocks
                + ", blk size (kb): " + App.blockSizeKb + ", blockSequence: " + App.blockSequence);

        /*
          init local vars that keep track of benchmarks, and a large read/write buffer
         */
        int wUnitsComplete = 0, rUnitsComplete = 0, unitsComplete;
        int wUnitsTotal = App.writeTest ? numOfBlocks * numOfMarks : 0;
        int rUnitsTotal = App.readTest ? numOfBlocks * numOfMarks : 0;
//        int unitsTotal = wUnitsTotal + rUnitsTotal;
//        float percentComplete;
//
//        int blockSize = blockSizeKb * KILOBYTE;
//        byte[] blockArr = new byte[blockSize];
//        for (int b = 0; b < blockArr.length; b++) {
//            if (b % 2 == 0) {
//                blockArr[b] = (byte) 0xFF;
//            }
//        }

        DiskMark wMark, rMark;  // declare vars that will point to objects used to pass progress to UI

        Gui.updateLegend();  // init chart legend info

        if (App.autoReset) {
            App.resetTestData();
            Gui.resetTestData();
        }

        int startFileNum = App.nextMarkNumber;

        /*
          The GUI allows a Write, Read, or both types of BMs to be started. They are done serially.
         */
        Executor executor = new Executor();

        if (App.writeTest) {
            WriteTest writeTest1 = new WriteTest(diskWorker, blockSequence, numOfMarks, numOfBlocks, blockSizeKb);
            executor.setCommand(writeTest1);
            executor.runTest();

            //Register observers to the Subject's list and notify them
            writeTest1.registerObserver(new DatabaseObserver());
            writeTest1.registerObserver(new Gui());
            //TODO add Slack Observer
            writeTest1.notifyObservers(writeTest1.getRun());
        }

        /*
          Most benchmarking systems will try to do some cleanup in between 2 benchmark operations to
          make it more 'fair'. For example a networking benchmark might close and re-open sockets,
          a memory benchmark might clear or invalidate the Op Systems TLB or other caches, etc.
         */

        // try renaming all files to clear catch
        if (App.readTest && App.writeTest && !diskWorker.isCancelledUp()) {
            JOptionPane.showMessageDialog(Gui.mainFrame,
                    """
                            For valid READ measurements please clear the disk cache by
                            using the included RAMMap.exe or flushmem.exe utilities.
                            Removable drives can be disconnected and reconnected.
                            For system drives use the WRITE and READ operations\s
                            independantly by doing a cold reboot after the WRITE""",
                    "Clear Disk Cache Now", JOptionPane.PLAIN_MESSAGE);
        }

        // Same as above, just for Read operations instead of Writes.
        if (App.readTest) {
            ReadTest readTest1 = new ReadTest(diskWorker, blockSequence, numOfMarks, numOfBlocks, blockSizeKb);
            executor.setCommand(readTest1);
            executor.runTest();

            //Register observers to the Subject's list and notify them
            readTest1.registerObserver(new DatabaseObserver());
            readTest1.registerObserver(new Gui());
            //TODO add Slack Observer
            readTest1.notifyObservers(readTest1.getRun());
        }
        App.nextMarkNumber += App.numOfMarks;
        return true;
    }


}
