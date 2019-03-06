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

public class MyListStatus {

    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration(false);
        conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/core-site.xml"));
        conf.addResource(new Path("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/hdfs-site.xml"));

        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromSubject(null);

        FileSystem fs = FileSystem.get(conf);
        FileStatus[] fsStatus = fs.listStatus(new Path("/"));
        for(int i = 0; i < fsStatus.length; i++){
            System.out.println(fsStatus[i].getPath().toString());
        }
    }
}
