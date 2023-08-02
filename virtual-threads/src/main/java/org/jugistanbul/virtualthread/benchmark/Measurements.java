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

    private static ExecutorService platformThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public static void main(String[] args) {
        taskSubmit(platformThreadExecutor, Instant.now());
        taskSubmit(virtualThreadExecutor, Instant.now());
    }

    private static void taskSubmit(final ExecutorService executor, final Instant startTime){

        IntStream.range(0, 1000)
                .mapToObj(i -> {
                    Runnable runnable = () -> ThreadUtil.sleepOfMillis(50);
                    return runnable;
                }).map(executor::submit).toList();

        ThreadUtil.wait(executor);
        System.out.println(String.format("Completion time %s ms", ThreadUtil.benchmark(startTime)));
    }
}
