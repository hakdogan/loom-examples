package org.jugistanbul.concurrency;

import org.jugistanbul.ErrorSimulator;

import java.math.BigDecimal;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 11.10.2023
 ***/
public class ExchangeReader
{

    private ExchangeReader(){}

    public static BigDecimal fetchEuroExchangeRate() {
        System.out.println("The remote service call will be performed to fetch the Euro exchange rate.");
        return BigDecimal.valueOf(28.94);
    }

    public static BigDecimal fetchUsdExchangeRate() {
        System.out.println("The remote service call will be performed to fetch the USD exchange rate.");
        ErrorSimulator.throwRandomly(100, 10_000);
        return BigDecimal.valueOf(27.55);
    }
}
