package org.jugistanbul.structuredconcurrency;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 20.08.2023
 ***/
public class RequestHandler
{

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handle() {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            Future<String> username  = scope.fork(this::fetchUserName);
            Future<Integer> userIdentity = scope.fork(this::fetchUserIdentity);

            scope.join().throwIfFailed();
            createResponse(username.get(), userIdentity.get());

        } catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        } catch (ExecutionException executionException){
            throw new RuntimeException(executionException.getMessage());
        }
    }

    private String fetchUserName(){
        String[] nicknames = {"hakdogan", "jugistanbul", "JakartaOneTUR"};

        Random random = new Random();
        int randomIndex = random.nextInt(nicknames.length);

        return nicknames[randomIndex];
    }

    private Integer fetchUserIdentity(){
        return ThreadLocalRandom.current().nextInt(100, 100_000);
    }

    private void createResponse(final String username, final int userIdentity){

        try (var in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
             var out = new PrintStream(clientSocket.getOutputStream(), true)){

            var response =
                    String.format("HTTP/1.1 200 OK\r\n\r\nUsername: %s Identity: %d\r\n", username, userIdentity);

            out.write(response.getBytes());
            String line = in.readLine();

            while( line != null && !line.isEmpty()){
                out.write(line.getBytes());
                out.println();
                line = in.readLine();
            }

            out.println();

        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
