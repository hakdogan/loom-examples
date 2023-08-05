package org.jugistanbul.virtualthread.jump;

import org.jugistanbul.util.ThreadUtil;
import java.util.stream.IntStream;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 5.08.2023
 ***/
public class ThreadJump
{
    public static void main(String[] args) {

        var threadList = IntStream.range(0, 10)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {

                    if(i == 0) {
                        System.out.println(Thread.currentThread());
                    }

                    ThreadUtil.sleepOfMillis(25);

                    if(i == 0) {
                        System.out.println(Thread.currentThread());
                    }

                })).toList();

        threadList.forEach(Thread::start);
        ThreadUtil.joinAll(threadList);
    }
}
