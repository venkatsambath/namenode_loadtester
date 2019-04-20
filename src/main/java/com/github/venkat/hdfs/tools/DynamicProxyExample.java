package com.github.venkat.hdfs.tools;


import java.lang.reflect.*;

public class DynamicProxyExample {
    public interface Hello {
        void sayHello();
    }

    public class RealHello implements Hello {
        public void sayHello() {
            System.out.println("Hello, World");
        }
    }

    public class FakeHello implements InvocationHandler {
        private Object agent;
        public FakeHello(Object obj) {
            this.agent = obj;
        }
        //** Hijacking real method invocation
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            System.out.println("Hello Hadoop first");
            //** Real method invocation happens here
            Object result = method.invoke(agent, args);
            return result;
        }
    }

    public void test() {
        RealHello realHello = new RealHello();
        FakeHello fakeHello = new FakeHello(realHello);
        //** "hello" will be a proxy,
        //** whose method invocation will be dispatched to "fakeHello".
        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(), new Class[] {Hello.class}, fakeHello);
        hello.sayHello();
    }

    public static void main(String[] args) {
        DynamicProxyExample proxyTester = new DynamicProxyExample();
        proxyTester.test();
    }
}