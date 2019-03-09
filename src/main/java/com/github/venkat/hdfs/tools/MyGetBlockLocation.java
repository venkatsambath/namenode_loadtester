package com.github.venkat.hdfs.tools;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyGetBlockLocation {
    public MyGetBlockLocation(int percentage) {

        float number = 10000 * ((float)percentage / 100);
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(3000, 30000, 10000000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < number;i++ ) {
            System.out.println("Calling getBlockLocation " + i);
            executor.execute(new getBlockLocation());
        }
        executor.shutdown();
    }


    public class getBlockLocation implements Runnable{

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
            try {

                BlockLocation locs[] = null;

                FileSystem hdfs = FileSystem.get(conf);

                locs = hdfs.getFileBlockLocations(hdfs.getFileStatus(new Path("/user/file")), 0, Long.MAX_VALUE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
