package org.jugistanbul.virtualthread.monitor;

import org.jugistanbul.util.ThreadUtil;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 4.08.2023
 ***/
public class MonitoringPinningEvent
{
    private static final Object lock = new Object();

    void main() {

        var thread = Thread.ofVirtual().unstarted(() -> {
            //To see the pinning event, execute this class with runMonitoringPinningEvent.sh file
            System.out.println("This virtual thread will be pinned on its carrier thread");
            synchronized (lock){
                ThreadUtil.sleepOfMillis(1);
            }
        });

        thread.start();
        ThreadUtil.join(thread);
    }
}
