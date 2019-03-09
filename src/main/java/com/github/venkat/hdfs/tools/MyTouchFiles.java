package com.github.venkat.hdfs.tools;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MyTouchFiles {

    public MyTouchFiles(int percentage) {

        float number = 1000000 * ((float)percentage / 100);
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(3000, 30000, 10000000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

        int j = 0;

        for (int i = 0; i < number;) {
            j = i + 500;
            System.out.println("Calling touchFiles(" + i + "," + j + ")");
            executor.execute(new TouchFiles(i, j));
            i = i + 500;
        }
        executor.shutdown();
    }

    public class TouchFiles implements Runnable {
        private int start, end;

        public TouchFiles(int i, int j) {
            this.start = i;
            this.end = j;
        }

        @Override
        public void run() {
            Configuration conf = new Configuration(false);
            conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/core-site.xml"));
            conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/hdfs-site.xml"));
            UserGroupInformation.setConfiguration(conf);
            try {
                UserGroupInformation.loginUserFromSubject(null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileSystem fs = null;
            try {
                fs = FileSystem.get(conf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = start; i < end; i++) {
                System.out.println("Loop :" + i);
                try {
                    fs.create(new Path("/user/data/test/file" + i), true).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

