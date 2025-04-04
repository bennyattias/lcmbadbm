package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    /**
     * Tests the B of Bicep, and specifically the R (range) of CORRECT
     * by seeing whether the method works for the largest possible integer
     */
    @Test
    void resetSequence() {
        App app = new App();
        app.nextMarkNumber = Integer.MAX_VALUE;

        app.resetSequence();

        assertEquals(1, app.nextMarkNumber);



    }

    /**
     * Tests the P of Bicep by checking that the method runs in less than 1 millisecond
     */
    @Test
    void resetSequence2() {
        App app = new App();
        app.nextMarkNumber = 3;
        long start = System.currentTimeMillis();
        app.resetSequence();
        long end = System.currentTimeMillis();

        assertTrue(end - start < 1);
    }

}