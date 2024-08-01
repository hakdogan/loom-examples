package org.jugistanbul;

import org.jugistanbul.handler.RequestHandler;
import org.jugistanbul.util.CustomException;

import java.net.ServerSocket;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 1.08.2024
 ***/
public class Server {

    void main() {

        try (ServerSocket socket = new ServerSocket(8080, 100)) {
            while (!Thread.currentThread().isInterrupted()) {
                try (var clientSocket = socket.accept()) {
                    var requestHandler = new RequestHandler(clientSocket);
                    requestHandler.handle();
                }
            }
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }
}
