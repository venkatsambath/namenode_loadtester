package com.github.venkat.hdfs.tools;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyListStatus {

    public MyListStatus(int percentage) {
        float number = 10000 * ((float)percentage / 100);
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(3000, 30000, 10000000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < number; i++) {
            System.out.println("Calling listStatus " + i);
            executor.execute(new listStatus());
        }
        executor.shutdown();
    }

    public static void main(String[] args) throws IOException {

    }

    public class listStatus implements Runnable {

        @Override
        public void run() {
            Configuration conf = new Configuration(false);
            conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/core-site.xml"));
            conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/hdfs-site.xml"));
            UserGroupInformation.setConfiguration(conf);
            try {
                UserGroupInformation.loginUserFromSubject(null);
                FileSystem fs = FileSystem.get(conf);
                FileStatus[] fsStatus = fs.listStatus(new Path("/"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
