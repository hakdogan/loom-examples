package org.jugistanbul.util;

import java.time.Duration;

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
}
