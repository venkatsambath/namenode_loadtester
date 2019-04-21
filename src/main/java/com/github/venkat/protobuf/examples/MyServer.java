package com.github.venkat.protobuf.examples;

import com.google.protobuf.BlockingService;
import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.ProtocolInfo;
import org.apache.hadoop.ipc.RPC;

import com.github.venkat.protobuf.examples.PBServerImpl;
import org.apache.hadoop.net.NetUtils;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.github.venkat.protobuf.examples.MyBusinessProtocolProtos.MyBusinessProtocol;


public class MyServer {

    private static Configuration conf;
    public final static String ADDRESS = "0.0.0.0";
    public final static int PORT = 0;
    private static InetSocketAddress addr;
    private static RPC.Server server;
    private final static int SLEEP_DURATION = 1000;

    @ProtocolInfo(protocolName = "testProto", protocolVersion = 1)
    public interface MyBusinessProtocolService extends MyBusinessProtocol.BlockingInterface {
    }

    public static void main(String[] args) throws IOException {

        conf = new Configuration();
        conf.setInt(CommonConfigurationKeys.IPC_MAXIMUM_DATA_LENGTH, 1024);
        conf.setBoolean(CommonConfigurationKeys.IPC_SERVER_LOG_SLOW_RPC, true);

        RPC.setProtocolEngine(conf, MyBusinessProtocolService.class, ProtobufRpcEngine.class);

        PBServerImpl serverImpl = new PBServerImpl();
        BlockingService service = MyBusinessProtocol.newReflectiveBlockingService(serverImpl);

        server = new RPC.Builder(conf).setProtocol(MyBusinessProtocolService.class).setInstance(service).setBindAddress(ADDRESS).setPort(PORT).build();
        addr = NetUtils.getConnectAddress(server);

        server.start();


    }
}
