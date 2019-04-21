package com.github.venkat.protobuf.examples;

import com.github.venkat.hadoop_protobuf.HadoopProtobufRpc;
import com.github.venkat.hadoop_protobuf.HadoopRpcServiceProtos;
import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyClientPB {

    private static Configuration conf;
    private static InetSocketAddress addr;

    public static void main(String[] args) throws IOException, ServiceException {
        RPC.setProtocolEngine(conf, HadoopProtobufRpc.HadoopService.class,
                ProtobufRpcEngine.class);
        HadoopProtobufRpc.HadoopService client = RPC.getProxy(HadoopProtobufRpc.HadoopService.class, 0, addr, conf);
        HadoopRpcServiceProtos.EmptyResponseProto response = client.ping(null, HadoopRpcServiceProtos.EmptyRequestProto.newBuilder().setMessage("hiiii").build());

        System.out.println(response.getMessage());
    }
}
