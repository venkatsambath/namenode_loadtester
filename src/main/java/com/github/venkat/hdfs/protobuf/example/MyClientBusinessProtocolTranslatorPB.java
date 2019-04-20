package com.github.venkat.hdfs.protobuf.example;


import com.google.protobuf.ServiceException;

public class MyClientBusinessProtocolTranslatorPB implements MyBusinessProtocol{

    final private MyBusinessProtocolPB rpcProxy;

    public MyClientBusinessProtocolTranslatorPB(MyBusinessProtocolPB proxy) {
        rpcProxy = proxy;
    }

    @Override
    public String myMethod(String msg) throws ServiceException {
        MyBusinessProtocolProtos.helloreq req = MyBusinessProtocolProtos.helloreq.newBuilder().setMsg("This is Venkat").build();
        MyBusinessProtocolProtos.hellores res = rpcProxy.myMethod(null,req);
        return res.getMsg();
    }

}
