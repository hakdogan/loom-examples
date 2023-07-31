package org.jugistanbul.virtualthread.factory;

import org.jugistanbul.util.ThreadUtil;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 31.07.2023
 ***/
public class StartVirtualThread
{
    public static void main(String[] args) {
        var thread = Thread.startVirtualThread(() -> System.out.printf("Hello from Virtual Thread"));
        ThreadUtil.join(thread);
    }
}
