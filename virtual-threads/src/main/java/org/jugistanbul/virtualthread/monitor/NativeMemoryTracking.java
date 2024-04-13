package org.jugistanbul.virtualthread.monitor;

import org.jugistanbul.util.ThreadType;
import org.jugistanbul.util.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 30.07.2023
 ***/
public class NativeMemoryTracking
{
    public static void main(String[] args) {

        ProcessHandle processHandle = ProcessHandle.current();
        var pid = processHandle.pid();

        var threadCount = defineThreadCount(args[0]);
        var threadType  = defineThreadType(args[1]);
        var jcmd        = args.length >= 3 && defineUsedJcmd(args[2]);
        var printTime   = threadCount - 1;

        System.out.println(STR."Thread count set to \{threadCount}");

        try(var executor = defineExecutorService(threadType)){

            IntStream.range(0, threadCount).forEach(i -> {

                if(jcmd && i == printTime){
                    memoryTracking(pid, threadType);
                }

                executor.execute(() -> ThreadUtil.sleepOfSeconds(5));
            });
        }
    }


    private static void memoryTracking(final long pid, final ThreadType threadType) {

        var scale = ThreadType.PLATFORM.equals(threadType) ? "GB" : "MB";
        var jcmd = STR."jcmd \{pid} VM.native_memory summary scale=\{scale}";

        Runtime rt = Runtime.getRuntime();
        String[] commands = {"/bin/sh", "-c", jcmd};

        try {

            Process proc = rt.exec(commands);
            try(var inputStream = proc.getInputStream();
                var errStream = proc.getErrorStream()){
                inputStream.transferTo(System.out);
                errStream.transferTo(System.out);
            }

        } catch (Exception e){
            throw new  RuntimeException(e);
        }
    }

    private static ThreadType defineThreadType(final String arg){

        return arg.equals(ThreadType.VIRTUAL.toString()) ?
                ThreadType.VIRTUAL
                : ThreadType.PLATFORM;
    }

    private static ExecutorService defineExecutorService(final ThreadType threadType){
        return ThreadType.VIRTUAL.equals(threadType)
                ? Executors.newVirtualThreadPerTaskExecutor()
                : Executors.newCachedThreadPool();
    }

    private static int defineThreadCount(final String arg){

        try {
           return Integer.parseInt(arg);
        }catch (NumberFormatException nfe){
            throw new  RuntimeException("You must provide a valid and positive number to determine the number of threads to use!");

        }
    }

    private static boolean defineUsedJcmd(final String arg){
        return Boolean.parseBoolean(arg);
    }
}
