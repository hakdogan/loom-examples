package org.jugistanbul.virtualthread.boundary;

import org.jugistanbul.util.ThreadUtil;

import java.math.BigInteger;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 6.08.2023
 ***/
public class CpuBounded
{
    public static void main(String[] args) {
        executeTasksOnGivenExecutor(Executors.newCachedThreadPool());
        //executeTasksOnGivenExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }

    private static void executeTasksOnGivenExecutor(final ExecutorService executor){

        IntStream
                .rangeClosed(1, 64)
                .forEach(index -> {

                    Instant start = Instant.now();
                    executor.submit(() -> {
                        IntStream
                                .range(0, 50_000_000)
                                .mapToObj(BigInteger::valueOf)
                                .reduce(BigInteger.ZERO, BigInteger::add);

                        System.out.println(createTwoDigitId(index) + ";" + ThreadUtil.benchmark(start));
                    });
                });

        ThreadUtil.shutdownAndAwaitTermination(executor, TimeUnit.HOURS);
    }

    private static String createTwoDigitId(final int index){
        var id = String.valueOf(index);
        return id.length() == 1 ? "0" + id : id;
    }
}
