package org.jugistanbul.virtualthread.builder;

import org.jugistanbul.util.ThreadUtil;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 31.07.2023
 ***/
public class Factory
{
    public static void main(String[] args) {

        var virtualThreadFactory = Thread.ofVirtual().factory();
        var platformThreadFactory = Thread.ofPlatform().factory();

        runWithExecutor(virtualThreadFactory);
        runWithExecutor(platformThreadFactory);

        var virtualThread = virtualThreadFactory.newThread(Factory::sayHello);
        var platformThread = platformThreadFactory.newThread(Factory::sayHello);

        virtualThread.start();
        platformThread.start();

        ThreadUtil.joinAll(List.of(virtualThread, platformThread));
    }

    private static void runWithExecutor(final ThreadFactory threadFactory){

        try (var executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
            IntStream.rangeClosed(0, 4).forEach(i ->
                    executor.submit(() -> {
                        Thread.sleep(Duration.ofSeconds(1));
                        System.out.println("Is virtual: " + Thread.currentThread().isVirtual());
                        return i;
                    }));
        }
    }

    private static void sayHello(){
        System.out.println("Hello world!");
    }
}
