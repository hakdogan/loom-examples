package org.jugistanbul.util;

import java.time.Duration;

public class ThreadUtil
{
    public static void sleepOfSeconds(long duration){
        try {
            Thread.sleep(Duration.ofSeconds(duration));
        } catch (InterruptedException ie){
            Thread.currentThread().interrupt();
        }
    }
}
