package org.jugistanbul.concurrency.unstructure;

import org.jugistanbul.concurrency.ExchangeReader;
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

    void main() throws ExecutionException, InterruptedException {

        Future<BigDecimal> usdExchangeRate = executor.submit(ExchangeReader::fetchUsdExchangeRate);
        Future<BigDecimal> euroExchangeRate = executor.submit(ExchangeReader::fetchEuroExchangeRate);

        var usd  = usdExchangeRate.get();
        var euro = euroExchangeRate.get();

        System.out.printf("Euro USD parity is %.2f", euro.divide(usd, RoundingMode.HALF_EVEN));
    }
}
