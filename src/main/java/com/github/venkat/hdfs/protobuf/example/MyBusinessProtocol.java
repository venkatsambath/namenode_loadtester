package com.github.venkat.hdfs.protobuf.example;

import com.google.protobuf.ServiceException;

public interface MyBusinessProtocol {
    public static final long versionID=10L;
    public String myMethod(String msg) throws ServiceException;
}
