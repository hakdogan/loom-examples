package org.jugistanbul.util;

import java.time.Duration;
import java.util.List;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 30.07.2023
 ***/
public class ThreadUtil
{
    public static void sleepOfSeconds(long duration){
        try {
            Thread.sleep(Duration.ofSeconds(duration));
        } catch (InterruptedException ie){
            Thread.currentThread().interrupt();
        }
    }

    public static void join(final Thread thread){

        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void joinAll(final List<Thread> threads){
        threads.forEach(ThreadUtil::join);
    }
}
