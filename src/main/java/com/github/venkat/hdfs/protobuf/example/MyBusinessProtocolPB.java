package com.github.venkat.hdfs.protobuf.example;

import org.apache.hadoop.ipc.VersionedProtocol;
import com.github.venkat.hdfs.protobuf.example.MyBusinessProtocolProtos.MyBusinessProtocol;

public interface MyBusinessProtocolPB extends
        MyBusinessProtocol.BlockingInterface {
}

