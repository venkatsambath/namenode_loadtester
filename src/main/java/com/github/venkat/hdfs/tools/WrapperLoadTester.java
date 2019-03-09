package com.github.venkat.hdfs.tools;

import java.util.Scanner;

public class WrapperLoadTester {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("Load Tester Code");
        System.out.println("Enter which percentage of load should be on ls, snapshotdiff, touchfiles, getblocklocation");

        System.out.println("1. ls = ");
        int ls = reader.nextInt();
        System.out.println("2. snapshotdiff = ");
        int snapshotdiff = reader.nextInt();
        System.out.println("3. touchfiles = ");
        int touchfiles = reader.nextInt();
        System.out.println("4. getblocklocation = ");
        int getblocklocation = reader.nextInt();

        new Thread(() -> { MyListStatus myListStatus = new MyListStatus(ls); }).start();
        new Thread(() -> { MySnapshotdiff mySnapshotdiff = new MySnapshotdiff(snapshotdiff);  }).start();
        new Thread(() -> { MyTouchFiles myTouchFiles = new MyTouchFiles(touchfiles);  }).start();
        new Thread(() -> { MyGetBlockLocation myGetBlockLocation = new MyGetBlockLocation(getblocklocation);   }).start();


    }
}
