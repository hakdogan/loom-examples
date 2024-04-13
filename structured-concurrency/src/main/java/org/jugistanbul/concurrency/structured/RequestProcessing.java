package org.jugistanbul.concurrency.structured;

import org.jugistanbul.util.ThreadUtil;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 20.08.2023
 ***/
public class RequestProcessing
{
    void main() {

        var serverThread = Thread.ofVirtual().unstarted(Server::run);
        serverThread.start();

        ThreadUtil.join(serverThread);
    }
}
