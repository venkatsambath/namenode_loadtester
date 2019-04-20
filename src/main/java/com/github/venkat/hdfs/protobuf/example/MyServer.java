package com.github.venkat.hdfs.protobuf.example;

import com.google.protobuf.BlockingService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.RPC;

import org.apache.hadoop.ipc.WritableRpcEngine;

import java.io.IOException;

class MyServer implements MyBusinessProtocol {

    private static Configuration conf;

    public static void main(String[] args) {
        conf.setInt(CommonConfigurationKeys.IPC_MAXIMUM_DATA_LENGTH, 1024);
        conf.setBoolean(CommonConfigurationKeys.IPC_SERVER_LOG_SLOW_RPC, true);

        RPC.setProtocolEngine(conf, TestRpcService.class, ProtobufRpcEngine.class);


    }

    public String myMethod(String msg)
    {
        return "Hi" + msg + "venkat";
    }
}
