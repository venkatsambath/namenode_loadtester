package com.github.venkat.protobuf.examples;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.github.venkat.protobuf.examples.MyBusinessProtocolProtos.hellores;
import com.github.venkat.protobuf.examples.MyBusinessProtocolProtos.helloreq;
import org.apache.hadoop.ipc.Server;


public class PBServerImpl implements MyBusinessProtocolService{
    @Override
    public hellores myMethod(RpcController controller, helloreq request) throws ServiceException {
        byte[] clientId = Server.getClientId();
        return hellores.newBuilder().setMsg("Hii").build();
    }
}
