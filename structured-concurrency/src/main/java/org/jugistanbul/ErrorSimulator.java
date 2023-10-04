package org.jugistanbul;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 4.10.2023
 ***/
public class ErrorSimulator
{
    private ErrorSimulator(){}

    static void throwRandomly(final int threshold){
        var randomValue = ThreadLocalRandom.current().nextInt(100, 100_000);
        if(randomValue >= threshold){
            throw new RuntimeException("There's something wrong");
        }
    }

    public static void throwRandomly(final long delay, final int threshold){

        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throwRandomly(threshold);
    }
}
