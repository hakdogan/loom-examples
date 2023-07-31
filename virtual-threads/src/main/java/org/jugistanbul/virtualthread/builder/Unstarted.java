package org.jugistanbul.virtualthread.builder;

import org.jugistanbul.util.ThreadUtil;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 31.07.2023
 ***/
public class Unstarted
{
    public static void main(String[] args) {
        var unstartedThread = Thread.ofVirtual().unstarted(() -> System.out.printf("Hello from postponed Virtual Thread"));
        unstartedThread.start();
        ThreadUtil.join(unstartedThread);
    }
}
