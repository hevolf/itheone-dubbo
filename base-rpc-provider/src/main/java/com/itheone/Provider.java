package com.itheone;

import com.alibaba.fastjson.JSON;
import com.itheone.entity.OrderEntiry;
import com.itheone.service.InfoService;
import com.itheone.service.InfoServiceImpl;
import com.itheone.service.OrderService;
import com.itheone.service.OrderServiceImpl;
import com.itheone.utils.InvokeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Provider {

    // spring 注入bean
    @Configuration
    static class ProviderConfiguration {
        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl();
        }
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        ctx.start();

        System.out.println("---------spring启动成功--------");

        System.out.println("------------方式一：直接获取spring中bean----------------");

        // 方式一：直接获取spring中bean
        OrderService orderService = (OrderService) ctx.getBean("orderService");
        OrderEntiry entiry = orderService.getDetail("1");
        System.out.println("测试orderService.getDetail调用功能，调用结果：" + JSON.toJSONString(entiry));

        // 方式二： 传入参数，使用对象进行反射调用
        System.out.println("------------方式二： 传入参数，使用对象进行反射调用----------------");
        Map<String,String> info = new HashMap();
        info.put("target","orderService");
        info.put("methodName","getDetail");
        info.put("arg","1");
        Object result = InvokeUtils.call(info,ctx);
        System.out.println("测试InvokeUtils.call调用功能，调用结果：" + JSON.toJSONString(result));

        // 方式三：通过协议暴露，接收远程参数进行调用
        System.out.println("------------方式三：通过协议暴露，接收远程参数进行调用----------------");
//        initProtocol(ctx);
        initProtocol2(ctx);
        System.in.read();
    }

    //服务暴露
    public static void initProtocol(ApplicationContext ctx) throws RemoteException, AlreadyBoundException, MalformedURLException {
        InfoService infoService = new InfoServiceImpl();
        //注冊通讯端口
        LocateRegistry.createRegistry(InfoService.port);
        //注冊通讯路径
        Naming.bind(InfoService.RMI_URL, infoService);
        System.out.println("初始化rmi绑定");
    }

    public static void initProtocol2(ApplicationContext ctx) throws RemoteException, AlreadyBoundException, MalformedURLException {
        InfoService infoService = new InfoServiceImpl(){
            @Override
            public Object passInfo(Map<String, String> info) {//对象，方法，参数

                super.passInfo(info);//info内包含的信息，是反射需要的信息
                Object result = InvokeUtils.call(info,ctx);
                System.out.println("测试InvokeUtils.call调用功能，调用结果：" + JSON.toJSONString(result));
                return result;
            }
        };

        //注冊通讯端口
        LocateRegistry.createRegistry(InfoService.port);
        //注冊通讯路径
        Naming.bind(InfoService.RMI_URL, infoService);
        System.out.println("初始化rmi绑定");
    }
}
