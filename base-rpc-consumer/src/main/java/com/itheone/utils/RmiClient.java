package com.itheone.utils;

import com.alibaba.fastjson.JSON;
import com.itheone.entity.OrderEntiry;
import com.itheone.service.InfoService;
import com.itheone.service.OrderService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class RmiClient {


    public static void main(String[] args) throws RemoteException, MalformedURLException {
        InfoService infoService = null;

        try {
            //取远程服务实现
            infoService = (InfoService) Naming.lookup(InfoService.RMI_URL);
            Object ret = infoService.sayHello("james");
            System.out.println("测试远程调用功能infoService.sayHello，调用结果：" + JSON.toJSONString(ret));

            //呼叫远程反射方法
            Map<String,String> info = new HashMap();
            info.put("target","orderService");
            info.put("methodName","getDetail");
            info.put("arg","1");
            Object result = infoService.passInfo(info);
            System.out.println("测试远程调用功能，调用结果：" + JSON.toJSONString(result));

            //静态代理方法
            OrderService service = getService(infoService);
            Object result2 = service.getDetail("1");//透明化调用，不增加开发人员的负担
            System.out.println("测试远程调用功能，调用结果：" + JSON.toJSONString(result2));
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * 静态代理,动态编译类来实现
     */
    public static OrderService getService(InfoService infoService){
        OrderService service = new OrderService(){

            @Override
            public OrderEntiry getDetail(String id) {
                Map<String,String> info = new HashMap();
                //写死了反射的目标，静态代理
                info.put("target","orderService");//对象
                info.put("methodName","getDetail");//方法
                info.put("arg",id);//参数

                OrderEntiry result = null;
                try {
                    result = (OrderEntiry)infoService.passInfo(info);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return result;
            }
        };
        return service;
    }


}
