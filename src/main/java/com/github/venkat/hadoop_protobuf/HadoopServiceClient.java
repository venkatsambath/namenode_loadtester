package com.github.venkat.hadoop_protobuf;

import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.net.NetUtils;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HadoopServiceClient {

    private static InetSocketAddress addr=new InetSocketAddress("192.168.0.2", 10000);
    private static Configuration conf= new Configuration();

    public static void main(String[] args) throws IOException, ServiceException {
        RPC.setProtocolEngine(conf, HadoopProtobufRpc.HadoopService.class,
                ProtobufRpcEngine.class);
        HadoopProtobufRpc.HadoopService client = RPC.getProxy(HadoopProtobufRpc.HadoopService.class, 0, addr, conf);
        HadoopRpcServiceProtos.EmptyResponseProto response = client.ping(null, HadoopRpcServiceProtos.EmptyRequestProto.newBuilder().setMessage("hiiii").build());

        System.out.println(response.getMessage());
    }
}
