package com.github.venkat.protobuf.examples;

import com.github.venkat.protobuf.examples.MyBusinessProtocolProtos.MyBusinessProtocol;
import org.apache.hadoop.ipc.ProtocolInfo;

@ProtocolInfo(protocolName = "testProto", protocolVersion = 1)
public interface MyBusinessProtocolService extends
        MyBusinessProtocol.BlockingInterface {
}
