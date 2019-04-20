package com.github.venkat.hdfs.rpc;

import org.apache.hadoop.ipc.ProtocolSignature;

import java.io.IOException;

public class ServerLogic implements ServerLogicInterface {

    public String hello(String name)
    {
        return "hello" + name;
    }

    @Override
    public long getProtocolVersion(String s, long l) throws IOException {
        return ServerLogic.versionID;
    }

    @Override
    public ProtocolSignature getProtocolSignature(String s, long l, int i) throws IOException {
        return null;
    }
}
