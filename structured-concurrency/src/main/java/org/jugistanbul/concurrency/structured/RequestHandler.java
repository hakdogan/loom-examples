package org.jugistanbul.concurrency.structured;

import org.jugistanbul.ErrorSimulator;
import org.jugistanbul.exeption.CustomException;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;
import static java.util.concurrent.StructuredTaskScope.Subtask;


/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 20.08.2023
 ***/
public class RequestHandler
{

    private final Socket clientSocket;
    private Random random = new Random();

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handle() {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            Subtask<String> username  = scope.fork(this::fetchUserName);
            Subtask<Integer> userIdentity = scope.fork(this::fetchUserIdentity);

            scope.join().throwIfFailed();
            createResponse(username.get(), userIdentity.get());

        } catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        } catch (ExecutionException executionException){
            throw new CustomException(executionException.getMessage());
        }
    }

    private String fetchUserName(){

        ErrorSimulator.throwRandomly(10, 70_000);
        System.out.println("The remote service call will be performed to fetch a username randomly");

        String[] nicknames = {"hakdogan", "jugistanbul", "JakartaOneTUR"};
        int randomIndex = random.nextInt(nicknames.length);

        return nicknames[randomIndex];
    }

    private Integer fetchUserIdentity(){
        ErrorSimulator.throwRandomly(100, 95_000);
        System.out.println("The remote service call will be performed to fetch the user identity");
        return ThreadLocalRandom.current().nextInt(100, 100_000);
    }

    private void createResponse(final String username, final int userIdentity){

        try (var in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
             var out = new PrintStream(clientSocket.getOutputStream(), true)){

            var response =
                    String.format("HTTP/1.1 200 OK\r%n\r%nUsername: %s Identity: %d\r%n", username, userIdentity);

            out.write(response.getBytes());
            String line = in.readLine();

            while( line != null && !line.isEmpty()){
                out.write(line.getBytes());
                out.println();
                line = in.readLine();
            }

            out.println();

        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
    }
}
