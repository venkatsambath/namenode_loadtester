package com.github.venkat.examples;

import com.github.venkat.hdfs.tools.MyTouchFiles;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

    public static void main(final String[] args) throws Exception {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(3000, 30000, 10000000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

        int j=2010000;

        for(int i=2010000; i<2500000;)
        {
            j=i+500;
            System.out.println("Calling touchFiles("+i+","+j+")");

            executor.execute((Runnable) new MyTouchFiles(i));
            i=i+500;
        }

        executor.shutdown();
    }

}

