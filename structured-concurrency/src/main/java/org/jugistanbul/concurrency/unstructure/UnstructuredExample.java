package org.jugistanbul.concurrency.unstructure;

import org.jugistanbul.ErrorSimulator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.*;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 4.10.2023
 ***/
public class UnstructuredExample
{
    static final ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Future<BigDecimal> usdExchangeRate = executor.submit(UnstructuredExample::fetchUsdExchangeRate);
        Future<BigDecimal> euroExchangeRate = executor.submit(UnstructuredExample::fetchEuroExchangeRate);

        var usd  = usdExchangeRate.get();
        var euro = euroExchangeRate.get();

        System.out.printf("Euro USD parity is %.2f", euro.divide(usd, RoundingMode.HALF_EVEN));
    }

    private static BigDecimal fetchEuroExchangeRate() {

        System.out.println("The remote service call will be performed to fetch the Euro exchange rate.");
        ErrorSimulator.throwRandomly(10, 10_000);
        return BigDecimal.valueOf(28.94);
    }

    private static BigDecimal fetchUsdExchangeRate() throws InterruptedException {
        System.out.println("The remote service call will be performed to fetch the USD exchange rate.");
        ErrorSimulator.throwRandomly(100, 90_000);
        return BigDecimal.valueOf(27.55);
    }
}
