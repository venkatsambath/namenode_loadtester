package com.github.venkat.hdfs.rpc;

import org.apache.hadoop.ipc.RPC;

import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyClient {

    public static void main(String[] args) throws IOException {

     final ServerLogicInterface serverLogicInterface = (ServerLogicInterface) RPC.waitForProxy(ServerLogicInterface.class, 234345L, new InetSocketAddress(MyServer.SERVER_ADDRESS, MyServer.SERVER_PORT), new Configuration());

        String result = serverLogicInterface.hello("venkat");

        System.out.printf("my result" +result);

        RPC.stopProxy(serverLogicInterface);
    }
}
