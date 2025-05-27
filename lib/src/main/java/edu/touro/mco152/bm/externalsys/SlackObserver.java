package edu.touro.mco152.bm.externalsys;

import edu.touro.mco152.bm.Observer;
import edu.touro.mco152.bm.persist.DiskRun;

public class SlackObserver implements Observer {
    SlackManager manager = new SlackManager("Slack");
    /**
     * The method the subject calls to notify the observer of something
     *
     * @param run this is the DiskRun object containing all the run info
     */
    @Override
    public void update(DiskRun run) {
        if (maxExceeds3PercentAvg(run)){
            manager.postMsg2OurChannel("Max run time exceeds 3% of avg run time");
        }
    }

    public boolean maxExceeds3PercentAvg(DiskRun run) {
        return run.getRunMax() > (0.03 * run.getRunAvg());
    }
}
