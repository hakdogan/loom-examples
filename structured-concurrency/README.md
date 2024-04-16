# Structured Concurrency

Virtual Threads solve the cost and efficiency issues of threads, but managing the resulting large number of threads is still a challenge. Structured concurrency overcomes this problem by treating groups of related tasks running on different threads as a single unit of work.

This module elucidates the fundamental principles and components of Structured Concurrency through illustrative examples.

The examples in this module show the short-circuiting behavior that structured concurrency provides with cancellation propagation when any of the subtasks fails or succeeds.  This is useful to prevent unnecessary work.

## Shutdown on Failure

<details>
<summary>org.jugistanbul.concurrency.structured.exchange.ShutDownOnFailure.java</summary>

[This example](https://github.com/hakdogan/loom-examples/blob/main/structured-concurrency/src/main/java/org/jugistanbul/concurrency/structured/exchange/ShutDownOnFailure.java) shows the short-circuiting behavior that structured concurrency provides with cancellation propagation when any of the subtasks fails.

```java
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            Subtask<BigDecimal> usd = scope.fork(ExchangeReader::fetchUsdExchangeRate);
            Subtask<BigDecimal> euro = scope.fork(ExchangeReader::fetchEuroExchangeRate);

            scope.join().throwIfFailed();
            System.out.printf("Euro USD parity is %.2f", euro.get().divide(usd.get(), RoundingMode.HALF_EVEN));
        }
```        
</details>

## Shutdown on Success

<details>
<summary>org.jugistanbul.concurrency.structured.exchange.ShutDownOnSuccess.java</summary>

[This example](https://github.com/hakdogan/loom-examples/blob/main/structured-concurrency/src/main/java/org/jugistanbul/concurrency/structured/exchange/ShutDownOnSuccess.java) shows the short-circuiting behavior that structured concurrency provides with cancellation propagation when any of the subtasks succeed which is useful to prevent unnecessary work once a successful result is obtained.

```java
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<>()) {

            StructuredTaskScope.Subtask<BigDecimal> usd = scope.fork(ExchangeReader::fetchUsdExchangeRate);
            StructuredTaskScope.Subtask<BigDecimal> euro = scope.fork(ExchangeReader::fetchEuroExchangeRate);

            scope.join();

            System.out.println(STR."USD process state  : \{usd.state()}");
            System.out.println(STR."EURO process state :  \{euro.state()}");

            System.out.println(scope.result());
        }
```

```shell
The remote service call will be performed to fetch the USD exchange rate.
The remote service call will be performed to fetch the Euro exchange rate.
USD process state  : UNAVAILABLE
EURO process state :  SUCCESS
28.94
```
</details>