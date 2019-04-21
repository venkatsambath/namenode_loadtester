package org.apache.hadoop.ipc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.ipc.metrics.RpcMetrics;
import org.apache.hadoop.ipc.protobuf.RpcHeaderProtos.RpcResponseHeaderProto.RpcErrorCodeProto;
import org.apache.hadoop.ipc.protobuf.TestProtos;
import org.apache.hadoop.ipc.protobuf.TestProtos.EchoRequestProto;
import org.apache.hadoop.ipc.protobuf.TestProtos.EchoResponseProto;
import org.apache.hadoop.ipc.protobuf.TestProtos.EmptyRequestProto;
import org.apache.hadoop.ipc.protobuf.TestProtos.EmptyResponseProto;
import org.apache.hadoop.ipc.protobuf.TestRpcServiceProtos.TestProtobufRpcProto;
import org.apache.hadoop.ipc.protobuf.TestRpcServiceProtos.TestProtobufRpc2Proto;

import com.google.protobuf.BlockingService;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import org.apache.hadoop.net.NetUtils;

public class TestProtoBufRpc {

    public final static String ADDRESS = "0.0.0.0";
    public final static int PORT = 0;
    private static InetSocketAddress addr;
    private static Configuration conf;
    private static RPC.Server server;
    private final static int SLEEP_DURATION = 1000;

    @ProtocolInfo(protocolName = "testProto", protocolVersion = 1)
    public interface TestRpcService
            extends TestProtobufRpcProto.BlockingInterface {
    }

    @ProtocolInfo(protocolName = "testProto2", protocolVersion = 1)
    public interface TestRpcService2 extends
            TestProtobufRpc2Proto.BlockingInterface {
    }

    public static class PBServerImpl implements TestRpcService {

        @Override
        public EmptyResponseProto ping(RpcController unused,
                                       EmptyRequestProto request) throws ServiceException {
            // Ensure clientId is received
            byte[] clientId = Server.getClientId();
            return EmptyResponseProto.newBuilder().build();
        }

        @Override
        public EchoResponseProto echo(RpcController unused, EchoRequestProto request)
                throws ServiceException {
            return EchoResponseProto.newBuilder().setMessage(request.getMessage())
                    .build();
        }

        @Override
        public EmptyResponseProto error(RpcController unused,
                                        EmptyRequestProto request) throws ServiceException {
            throw new ServiceException("error", new RpcServerException("error"));
        }

        @Override
        public EmptyResponseProto error2(RpcController unused,
                                         EmptyRequestProto request) throws ServiceException {
            throw new ServiceException("error", new URISyntaxException("",
                    "testException"));
        }
    }
    public static class PBServer2Impl implements TestProtoBufRpc.TestRpcService2 {

        @Override
        public EmptyResponseProto ping2(RpcController unused,
                                        EmptyRequestProto request) throws ServiceException {
            return EmptyResponseProto.newBuilder().build();
        }

        @Override
        public EchoResponseProto echo2(RpcController unused, EchoRequestProto request)
                throws ServiceException {
            return EchoResponseProto.newBuilder().setMessage(request.getMessage())
                    .build();
        }

        @Override
        public TestProtos.SleepResponseProto sleep(RpcController controller,
                                                   TestProtos.SleepRequestProto request) throws ServiceException {
            try{
                Thread.sleep(request.getMilliSeconds());
            } catch (InterruptedException ex){
            }
            return  TestProtos.SleepResponseProto.newBuilder().build();
        }
    }

    public static void main(String[] args) throws IOException {

        conf = new Configuration();
        conf.setInt(CommonConfigurationKeys.IPC_MAXIMUM_DATA_LENGTH, 1024);
        conf.setBoolean(CommonConfigurationKeys.IPC_SERVER_LOG_SLOW_RPC, true);
        // Set RPC engine to protobuf RPC engine
        RPC.setProtocolEngine(conf, TestRpcService.class, ProtobufRpcEngine.class);

        // Create server side implementation
        PBServerImpl serverImpl = new PBServerImpl();
        BlockingService service = TestProtobufRpcProto
                .newReflectiveBlockingService(serverImpl);

        // Get RPC server for server side implementation
        server = new RPC.Builder(conf).setProtocol(TestRpcService.class)
                .setInstance(service).setBindAddress(ADDRESS).setPort(PORT).build();
        addr = NetUtils.getConnectAddress(server);

        // now the second protocol
        PBServer2Impl server2Impl = new PBServer2Impl();
        BlockingService service2 = TestProtobufRpc2Proto
                .newReflectiveBlockingService(server2Impl);

        server.addProtocol(RPC.RpcKind.RPC_PROTOCOL_BUFFER, TestRpcService2.class,
                service2);
        server.start();

    }

}

