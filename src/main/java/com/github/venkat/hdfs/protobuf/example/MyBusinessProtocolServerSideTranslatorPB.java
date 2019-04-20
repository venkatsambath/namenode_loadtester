package com.github.venkat.hdfs.protobuf.example;


import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.github.venkat.hdfs.protobuf.example.MyBusinessProtocolProtos.helloreq;
import com.github.venkat.hdfs.protobuf.example.MyBusinessProtocolProtos.hellores;

public class MyBusinessProtocolServerSideTranslatorPB implements MyBusinessProtocolPB {

    final private MyBusinessProtocol server;

    public MyBusinessProtocolServerSideTranslatorPB(MyBusinessProtocol server) {
        this.server = server;
    }

    @Override
    public hellores myMethod(RpcController controller, helloreq req) throws ServiceException {
        String result = server.myMethod(req.getMsg());
        return hellores.newBuilder().setMsg(result).build();
    }

}
