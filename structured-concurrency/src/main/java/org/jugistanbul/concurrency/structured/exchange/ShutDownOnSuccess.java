package org.jugistanbul.concurrency.structured.exchange;

import org.jugistanbul.concurrency.ExchangeReader;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 11.10.2023
 ***/
public class ShutDownOnSuccess
{
    void main() throws InterruptedException, ExecutionException {

        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<>()) {

            StructuredTaskScope.Subtask<BigDecimal> usd = scope.fork(ExchangeReader::fetchUsdExchangeRate);
            StructuredTaskScope.Subtask<BigDecimal> euro = scope.fork(ExchangeReader::fetchEuroExchangeRate);

            scope.join();

            System.out.println(STR."USD process state  : \{usd.state()}");
            System.out.println(STR."EURO process state :  \{euro.state()}");

            System.out.println(scope.result());
        }
    }
}
