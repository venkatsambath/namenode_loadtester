package com.github.venkat.hdfs.tools;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

    public static void main(final String[] args) throws Exception {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(1000, 1000, 10000000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

        int j=0;

        for(int i=1; i<1000001;)
        {
            j=i+1000;
            System.out.println("Calling touchFiles("+i+","+j+")");

            executor.execute(new TouchFiles(i,j));
            i=i+1000;
        }
        executor.shutdown();
    }

}

