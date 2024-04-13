package org.jugistanbul.concurrency.structured;

import org.jugistanbul.exeption.CustomException;

import java.net.ServerSocket;
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
                try (var clientSocket = socket.accept()) {
                    var requestHandler = new RequestHandler(clientSocket);
                    requestHandler.handle();
                }

                if(REQUEST_COUNT.incrementAndGet() == MAX_REQUEST){
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }
}
