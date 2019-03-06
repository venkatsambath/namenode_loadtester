package com.github.venkat.hdfs.tools;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class LoadTester implements Runnable{

    public boolean running = false;

    public LoadTester ()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) throws IOException {

        List<LoadTester> workers = new ArrayList<LoadTester>();

//        System.out.println("This is currently running on the main thread, " +
//                "the id is: " + Thread.currentThread().getId());

        Date start = new Date();

        for (int i=0; i<1; i++)
        {
            workers.add(new LoadTester());
        }

        for (LoadTester worker : workers)
        {
            while (worker.running)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Date end = new Date();

        long difference = end.getTime() - start.getTime();

        System.out.println ("This whole process took: " + difference/1000 + " seconds.");

    }
    public void run()
    {
        this.running = true;
//        System.out.println("This is currently running on a separate thread, " +
//                "the id is: " + Thread.currentThread().getId());

        Configuration conf = new Configuration(false);
        File configFile = new File("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/core-site.xml");
        conf.addResource(new Path("file://" + configFile.getAbsolutePath()));
        configFile = new File("/Users/venkat/Code/namenode_loadtester/src/main/java/com/github/venkat/hdfs/tools/hdfs-site.xml");
        conf.addResource(new Path("file://" + configFile.getAbsolutePath()));
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromSubject(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
   //     System.out.println(conf.toString());
        FileSystem hdfs = null;
        try {
            hdfs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Enter number of threads:");
         Scanner reader = new Scanner(System.in);
         String fileName = reader.nextLine();
//        Path filename1 = new Path("/user/file");
//
//        BlockLocation locs[] = null;
//        String prefix = "";
        int i;
        MyListStatus ls = new MyListStatus();

        for (i = 0; i < 5; i++) {

            try {
                ls.listStatus();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                locs = hdfs.getFileBlockLocations(hdfs.getFileStatus(filename1), 0, Long.MAX_VALUE);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//

//            for (BlockLocation loc : locs) {
//                System.out.println(prefix);
//                System.out.println("{");
//                try {
//                    System.out.println("  hosts =         " + Arrays.toString(loc.getHosts()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("  cachedHosts =   " + Arrays.toString(loc.getCachedHosts()));
//                try {
//                    System.out.println("  names    =      " + Arrays.toString(loc.getNames()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    System.out.println("  topologyPaths = " + Arrays.toString(loc.getTopologyPaths()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("  offset =        " + loc.getOffset());
//                System.out.println("  length =        " + loc.getLength());
//                System.out.println("  corrupt =       " + loc.isCorrupt());
//                System.out.println("}");
//                prefix = ",";
//                System.out.println("looped in"+i+"times");
            }

/*        try
        {
            // this will pause this spawned thread for 5 seconds
            //  (5000 is the number of milliseconds to pause)
            // Also, the Thread.sleep() method throws an InterruptedException
            //  so we must "handle" this possible exception, that's why I've
            //  wrapped the sleep() method with a try/catch block
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            // As user Bernd points out in the comments section below, you should
            //  never swallow an InterruptedException.
            Thread.currentThread().interrupt();
        }*/
        this.running = false;
    }
}
