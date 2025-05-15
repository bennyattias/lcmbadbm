package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.Executor;
import edu.touro.mco152.bm.commands.WriteTest;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest {
    Executor executor = new Executor();
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

    /**
     * Tests the Write benchmark by checking if the boolean "passed" has become true,
     * an indication that the method has been fully executed.
     */
    @Test
    void writeTest() {
        WriteTest writeTest = new WriteTest(new SwingUI(), DiskRun.BlockSequence.SEQUENTIAL, 25, 128, 2048);
        executor.setCommand(writeTest);
        executor.runTest();
        assertTrue(writeTest.passed);
    }

    /**
     * Tests the Read benchmark by checking if the boolean "passed" has become true,
     * an indication that the method has been fully executed.
     */
    @Test
    void readTest() {
        WriteTest readTest = new WriteTest(new SwingUI(), DiskRun.BlockSequence.SEQUENTIAL, 25, 128, 2048);
        executor.setCommand(readTest);
        executor.runTest();
        assertTrue(readTest.passed);

    }

}
