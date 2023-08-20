package org.jugistanbul.structuredconcurrency;

import java.io.*;
import java.net.Socket;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 20.08.2023
 ***/
public class RequestHandler
{
    public static final byte[] RESPONSE = (
            "HTTP/1.1 200 OK\r\n" +
                    "Content-length: 2\r\n" +
                    "\r\n" +
                    "OK\r\n").getBytes();
    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String handle() {

        var stringBuffer = new StringBuffer();

        try (var in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
             var out = new PrintStream(clientSocket.getOutputStream(), true)){

            out.write(RESPONSE);
            String line = in.readLine();
            append(stringBuffer, line);

            while( line != null && !line.isEmpty()){
                out.write(line.getBytes());
                line = in.readLine();
                append(stringBuffer, line);
            }

        } catch (Exception ex){
            throw new RuntimeException(ex);
        }

        return stringBuffer.toString();
    }

    private void append(final StringBuffer stringBuffer, final String text){
        stringBuffer.append(text).append("\r\n");
    }
}
