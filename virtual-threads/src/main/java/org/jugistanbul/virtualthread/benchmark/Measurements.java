package org.jugistanbul.virtualthread.benchmark;

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

    public static void main(String[] args) {

        var platformThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

        executeTasksOnGivenExecutor(platformThreadExecutor, Instant.now());
        executeTasksOnGivenExecutor(virtualThreadExecutor, Instant.now());
    }

    private static void executeTasksOnGivenExecutor(final ExecutorService executor, final Instant startTime){

        IntStream.range(0, 1000)
                .mapToObj(i -> {
                    Runnable runnable = () -> ThreadUtil.sleepOfMillis(50);
                    return runnable;
                }).map(executor::submit).toList();

        ThreadUtil.wait(executor);
        System.out.println(String.format("Completion time %s ms", ThreadUtil.benchmark(startTime)));
    }
}
