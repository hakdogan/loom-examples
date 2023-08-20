package org.jugistanbul.structuredconcurrency;

import org.jugistanbul.util.ThreadUtil;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 20.08.2023
 ***/
public class RequestProcessing
{
    public static void main(String[] args) {

        var serverThread = Thread.ofVirtual().unstarted(Server::run);
        serverThread.start();

        ThreadUtil.join(serverThread);
    }
}
