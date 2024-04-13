package org.jugistanbul.virtualthread.benchmark;

import org.jugistanbul.util.ThreadType;
import org.jugistanbul.util.ThreadUtil;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 2.08.2023
 ***/
public class Measurements
{

    void main() {

        var platformThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

        executeTasksOnGivenExecutor(platformThreadExecutor, ThreadType.PLATFORM);
        executeTasksOnGivenExecutor(virtualThreadExecutor, ThreadType.VIRTUAL);
    }

    private static void executeTasksOnGivenExecutor(final ExecutorService executor, final ThreadType type){

        var startTime = Instant.now();
        IntStream.range(0, 1000)
                .mapToObj(_ -> (Runnable) () -> ThreadUtil.sleepOfMillis(50))
                .forEach(executor::submit);

        ThreadUtil.wait(executor);
        System.out.printf("Completion time of %s %s ms%n", type.getDesc(), ThreadUtil.benchmark(startTime));
    }
}
