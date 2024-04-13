package org.jugistanbul.virtualthread.factory;

import org.jugistanbul.util.ThreadUtil;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 31.07.2023
 ***/
public class StartVirtualThread
{
    void main() {
        var thread = Thread.startVirtualThread(() -> System.out.println("Hello from Virtual Thread"));
        ThreadUtil.join(thread);
    }
}
