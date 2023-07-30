package org.jugistanbul.virtualthread.factory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 30.07.2023
 ***/
public class VirtualThreadPerTask
{
    public static void main(String[] args) {

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            IntStream.range(0, 100_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
    }
}
