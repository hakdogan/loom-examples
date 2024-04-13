package org.jugistanbul.virtualthread.pin;

import org.jugistanbul.util.ThreadUtil;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 5.08.2023
 ***/
public class PreventPinning
{
    private static final ReentrantLock lock = new ReentrantLock();

    void main() {

        var threadList = IntStream.range(0, 10)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {

                    if (i == 0) {
                        System.out.println(Thread.currentThread());
                    }

                    lock.lock();
                    try {
                        ThreadUtil.sleepOfMillis(25);
                    } finally {
                        lock.unlock();
                    }

                    if (i == 0) {
                        System.out.println(Thread.currentThread());
                    }

                })).toList();

        threadList.forEach(Thread::start);
        ThreadUtil.joinAll(threadList);
    }
}
