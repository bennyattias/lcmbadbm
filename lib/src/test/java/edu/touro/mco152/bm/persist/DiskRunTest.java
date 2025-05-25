package edu.touro.mco152.bm.persist;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DiskRunTest {
    /**
     * Tests the "Right" case
     */
    @Test
    void setDiskInfo() {
        DiskRun diskRun = new DiskRun();
        diskRun.diskInfo = "diskInfo";

        diskRun.setDiskInfo("info567");

        assertEquals("info567", diskRun.getDiskInfo());
    }

    /**
     * Tests the B of Bicep, and specifically the C (conformance) of CORRECT
     * by making sure null values can be ascribed to diskInfo, as the method intends
     */
    @Test
    void setDiskInfo2() {
        DiskRun diskRun = new DiskRun();
        diskRun.diskInfo = "diskInfo";

        diskRun.setDiskInfo(null);

        assertEquals(null, diskRun.getDiskInfo());
    }

    /**
     * Tests the C of Bicep by setting the diskInfo value both manually and through setDiskInfo()
     * and achieving the same result.
     */
    @Test
    void setDiskInfo3() {
        DiskRun diskRun = new DiskRun();
        diskRun.diskInfo = "123456";

        DiskRun diskRun2 = new DiskRun();
        diskRun2.setDiskInfo("123456");

        assertEquals(diskRun.diskInfo, diskRun2.diskInfo);
    }

    @Test
    void getDuration() {
        DiskRun diskRun = new DiskRun();

        String actual = diskRun.getDuration();

        assertEquals("unknown", actual);

    }

    @Test
    void getDuration2() {
        DiskRun diskRun = new DiskRun();
        diskRun.setEndTime(new Date());

        String actual = diskRun.getDuration();
        System.out.println(actual);

        assertFalse("unknown".equals(actual));

    }
}