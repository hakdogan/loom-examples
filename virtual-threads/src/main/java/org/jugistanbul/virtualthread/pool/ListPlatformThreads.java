package org.jugistanbul.virtualthread.pool;

import org.jugistanbul.util.ThreadUtil;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 3.08.2023
 ***/
public class ListPlatformThreads
{
    private static final Pattern WORKER_PATTERN = Pattern.compile("worker-+\\d");
    private static final Pattern POOL_PATTERN = Pattern.compile("@ForkJoinPool-+\\d");
    private static final Set<String> poolNames = ConcurrentHashMap.newKeySet();
    private static final Set<String> pThreadNames = ConcurrentHashMap.newKeySet();

    void main() {

        var threadList = IntStream
                .range(0, 1_000_000)
                .mapToObj(_ -> Thread.ofVirtual().unstarted(() -> {

                    var poolName = getPoolName();
                    poolNames.add(poolName);

                    var workerName = getWorkerName();
                    pThreadNames.add(workerName);

                })).toList();

        var start = Instant.now();
        threadList.forEach(Thread::start);
        ThreadUtil.joinAll(threadList);

        System.out.println(STR."Execution time:  \{ThreadUtil.benchmark(start)} ms");
        System.out.println(STR."Core             \{Runtime.getRuntime().availableProcessors()}");
        System.out.println(STR."Pools            \{poolNames.size()}");
        System.out.println(STR."Platform threads \{pThreadNames.size()}");
    }

    private static String getWorkerName(){
        var name = Thread.currentThread().toString();
        Matcher workerMatcher = WORKER_PATTERN.matcher(name);
        if(workerMatcher.find()){
            return workerMatcher.group();
        }

        return "worker name not found";
    }

    private static String getPoolName(){
        var name = Thread.currentThread().toString();
        Matcher poolMatcher = POOL_PATTERN.matcher(name);
        if(poolMatcher.find()){
            return poolMatcher.group();
        }

        return "pool name not found";
    }
}
