package com.github.venkat.hdfs.tools;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.SnapshotDiffReport;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

public class MySnapshotdiff {
    public MySnapshotdiff() throws IOException {

        Configuration conf = new Configuration(false);
        conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/core-site.xml"));
        conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/hdfs-site.xml"));

        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromSubject(null);

        FileSystem fs = FileSystem.get(conf);
        DistributedFileSystem dfs = (DistributedFileSystem) fs;

        Path snapshotRoot = new Path("/user/data/");
        String fromSnapshot = "dist1";
        String toSnapshot = "dist2";

        SnapshotDiffReport diffReport = dfs.getSnapshotDiffReport(snapshotRoot, fromSnapshot, toSnapshot);
        System.out.println(diffReport.toString());


    }
    public static void main(String[] args) {

    }
}
