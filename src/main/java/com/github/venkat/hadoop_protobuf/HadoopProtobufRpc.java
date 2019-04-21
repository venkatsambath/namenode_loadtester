package com.github.venkat.hadoop_protobuf;

//all methods and classes in generated class
import com.github.venkat.hadoop_protobuf.HadoopRpcServiceProtos;

//all methods for messages
import com.github.venkat.hadoop_protobuf.HadoopRpcServiceProtos.EmptyRequestProto;
import com.github.venkat.hadoop_protobuf.HadoopRpcServiceProtos.EmptyRequestProto;

//all methods for rpc
import com.github.venkat.hadoop_protobuf.HadoopRpcServiceProtos.HadoopRpcServiceProto;


import com.google.protobuf.BlockingService;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.ProtocolInfo;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.TestProtoBufRpc;
import org.apache.hadoop.ipc.protobuf.TestRpcServiceProtos;
import org.apache.hadoop.net.NetUtils;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HadoopProtobufRpc {

    public final static String ADDRESS = "0.0.0.0";
    public final static int PORT = 10000;
    private static InetSocketAddress addr;
    private static Configuration conf;
    private static RPC.Server server;
    private final static int SLEEP_DURATION = 1000;

    // here were create interface of service
    @ProtocolInfo(protocolName = "testProto", protocolVersion = 1)
    public interface HadoopService extends HadoopRpcServiceProto.BlockingInterface
    {

    }

    // here we implement the service
    public static class ServerImpl implements HadoopService
    {

        @Override
        public HadoopRpcServiceProtos.EmptyResponseProto ping(RpcController controller, EmptyRequestProto request) throws ServiceException {
            return HadoopRpcServiceProtos.EmptyResponseProto.newBuilder().setMessage("hyi").build();
        }
    }

    public static void main(String[] args) throws IOException, ServiceException {

        HadoopProtobufRpc obj = new HadoopProtobufRpc();
        obj.server_start();

        RPC.setProtocolEngine(conf, HadoopService.class,
                ProtobufRpcEngine.class);
       HadoopService client = RPC.getProxy(HadoopService.class, 0, addr, conf);
       HadoopRpcServiceProtos.EmptyResponseProto response = client.ping(null, EmptyRequestProto.newBuilder().setMessage("hiiii").build());

        System.out.println(response.getMessage());


    }

    public void server_start() throws IOException
    {
        conf = new Configuration();
        conf.setInt(CommonConfigurationKeys.IPC_MAXIMUM_DATA_LENGTH, 1024);
        conf.setBoolean(CommonConfigurationKeys.IPC_SERVER_LOG_SLOW_RPC, true);

        // Set RPC engine to the service we implemented
        RPC.setProtocolEngine(conf, HadoopService.class, ProtobufRpcEngine.class);

        // Create server side implementation
        ServerImpl serverImpl = new ServerImpl();

        // Register service implementation
        BlockingService service = HadoopRpcServiceProtos.HadoopRpcServiceProto
                .newReflectiveBlockingService(serverImpl);

        // Get RPC server for server side implementation
        server = new RPC.Builder(conf).setProtocol(HadoopService.class).setInstance(service).setBindAddress(ADDRESS).setPort(PORT).build();

        addr = NetUtils.getConnectAddress(server);

        server.start();
    }
}
