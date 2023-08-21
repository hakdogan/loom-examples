package org.jugistanbul.structuredconcurrency;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.net.ServerSocket;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 20.08.2023
 ***/
public class Server
{

    private static final AtomicInteger REQUEST_COUNT = new AtomicInteger();
    private static final int MAX_REQUEST = 5;

    private Server(){}

    public static void run() {

        try (ServerSocket socket = new ServerSocket(8080, 100)) {
            while (!Thread.currentThread().isInterrupted()) {

                try (var scope = new StructuredTaskScope.ShutdownOnFailure();
                     var clientSocket = socket.accept()) {

                    var requestHandler = new RequestHandler(clientSocket);
                    Future<String > content  = scope.fork(() -> requestHandler.handle());
                    Future<Integer> order = scope.fork(() -> fetchRequestCount());

                    scope.join().throwIfFailed();
                    System.out.println(String.format("%sRequest count: %d%N", content.get(), order.get()));
                    if(REQUEST_COUNT.get() == MAX_REQUEST){
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Integer fetchRequestCount(){
        return REQUEST_COUNT.incrementAndGet();
    }
}
