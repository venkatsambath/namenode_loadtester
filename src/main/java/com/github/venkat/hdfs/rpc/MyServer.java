package com.github.venkat.hdfs.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

public class MyServer {

    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 20000;

    public static void main(String[] args) throws IOException {
       final RPC.Server server= RPC.getServer(new ServerLogic(),SERVER_ADDRESS,SERVER_PORT,new Configuration());
       server.start();

    }

}
