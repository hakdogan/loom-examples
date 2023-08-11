package org.jugistanbul.virtualthread.scheduler;

import org.jugistanbul.util.ThreadUtil;
import java.util.stream.IntStream;

/**
 *
 *  Observe scheduler behavior by assigning different values to these parameters.
 *  For example firstly
 *
 *  -Djdk.virtualThreadScheduler.parallelism=1
 *  -Djdk.virtualThreadScheduler.maxPoolSize=1
 *  -Djdk.virtualThreadScheduler.minRunnable=1
 *
 *  then
 *
 *  -Djdk.virtualThreadScheduler.parallelism=2
 *  -Djdk.virtualThreadScheduler.maxPoolSize=2
 *  -Djdk.virtualThreadScheduler.minRunnable=2
 *
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 11.08.2023
 ***/
public class CooperativeScheduling
{
    public static void main(String[] args) {

        var threadList = IntStream.range(0, 10)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
                    System.out.println(Thread.currentThread());
                    ThreadUtil.sleepOfMillis(500);
                })).toList();

        threadList.forEach(Thread::start);
        ThreadUtil.joinAll(threadList);
    }
}
