package com.github.venkat.hdfs.singlerpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

public class singleListStatus {

    public static void main(String[] args) {
        Configuration conf = new Configuration(false);
        conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/core-site.xml"));
        conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/hdfs-site.xml"));
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromSubject(null);
            FileSystem fs = FileSystem.get(conf);
            FileStatus[] fsStatus = fs.listStatus(new Path("/"));
            System.out.println(fsStatus.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


