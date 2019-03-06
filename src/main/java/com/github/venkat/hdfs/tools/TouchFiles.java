package com.github.venkat.hdfs.tools;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

public class TouchFiles implements Runnable {
    private int start,end;
    public TouchFiles(int i,int j) {
        this.start = i;
        this.end=j;
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
                fs.create(new Path("/user/data/test/file" + i),true).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}