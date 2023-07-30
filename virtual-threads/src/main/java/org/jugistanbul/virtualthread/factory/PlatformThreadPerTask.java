package org.jugistanbul.virtualthread.factory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class PlatformThreadPerTask
{
    public static void main(String[] args) {

        try(var executor = Executors.newCachedThreadPool()){
            IntStream.range(0, 5000)
                    .forEach(i -> {
                        executor.submit(() -> {
                            Thread.sleep(Duration.ofSeconds(1));
                            return i;
                        });
                    });
        }
    }
}
