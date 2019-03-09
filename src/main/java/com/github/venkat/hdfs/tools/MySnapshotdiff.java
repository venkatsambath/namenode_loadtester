package com.github.venkat.hdfs.tools;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.SnapshotDiffReport;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MySnapshotdiff {

    public MySnapshotdiff(int percentage) {

        float number = 10000 * ((float)percentage / 100);

        final ThreadPoolExecutor executor = new ThreadPoolExecutor(3000, 30000, 10000000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < number;i++ ) {
            System.out.println("Calling snapshotdiff " + i);
            executor.execute(new snapshotdiff());
        }
        executor.shutdown();

    }

    public class snapshotdiff implements Runnable {
        @Override
        public void run() {
            Configuration conf = new Configuration(false);
            conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/core-site.xml"));
            conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/hdfs-site.xml"));

            UserGroupInformation.setConfiguration(conf);
            try {
                UserGroupInformation.loginUserFromSubject(null);
                FileSystem fs = FileSystem.get(conf);
                DistributedFileSystem dfs = (DistributedFileSystem) fs;
                Path snapshotRoot = new Path("/user/bdr/venkat/");
                String fromSnapshot = "s1";
                String toSnapshot = "s2";
                System.out.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now().toString());
                SnapshotDiffReport diffReport = dfs.getSnapshotDiffReport(snapshotRoot, fromSnapshot, toSnapshot);
                System.out.println(java.time.LocalDate.now() + " " + java.time.LocalTime.now().toString());
                System.out.println(diffReport.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
