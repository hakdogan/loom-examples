package org.jugistanbul.concurrency.structured.exchange;

import org.jugistanbul.concurrency.ExchangeReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import static java.util.concurrent.StructuredTaskScope.Subtask;


/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 11.10.2023
 ***/
public class ShutDownOnFailure
{
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            Subtask<BigDecimal> usd = scope.fork(ExchangeReader::fetchUsdExchangeRate);
            Subtask<BigDecimal> euro = scope.fork(ExchangeReader::fetchEuroExchangeRate);

            scope.join().throwIfFailed();
            System.out.printf("Euro USD parity is %.2f", euro.get().divide(usd.get(), RoundingMode.HALF_EVEN));
        }
    }
}
