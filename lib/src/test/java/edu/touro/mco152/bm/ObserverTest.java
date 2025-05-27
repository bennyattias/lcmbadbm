package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.Executor;
import edu.touro.mco152.bm.commands.ReadTest;
import edu.touro.mco152.bm.commands.WriteTest;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Properties;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.App.blockSizeKb;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ObserverTest tests if a subject in BadBM, called by the client "DiskWorker",
 * properly registers and notifies its observers.
 */
public class ObserverTest {
    private static TestObserver testObserver = new TestObserver();
    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */
    @BeforeAll
    static void setupDefaultAsPerProperties()
    {
        /// Do the minimum of what  App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();
        System.out.println(App.getConfigString());
        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference
        App.dataDir = new File(App.locationDir.getAbsolutePath()+ File.separator+App.DATADIRNAME);

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

    @Test
    void testObserverRegistration(){
        Executor executor = new Executor();
        WriteTest writeTest1 = new WriteTest(new SwingUI(), blockSequence, numOfMarks, numOfBlocks, blockSizeKb);
        executor.setCommand(writeTest1);
        executor.runTest();

        writeTest1.registerObserver(testObserver);
        writeTest1.notifyObservers(writeTest1.getRun());
    }

    @AfterAll
    public static void observerCalledTest() {
        // Ensure observer was notified.
        assertTrue(testObserver.isTestPassed());
    }



}
