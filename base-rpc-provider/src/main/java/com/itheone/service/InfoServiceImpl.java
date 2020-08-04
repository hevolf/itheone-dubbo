package com.itheone.service;


import com.alibaba.fastjson.JSON;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class InfoServiceImpl extends UnicastRemoteObject implements InfoService {
    public InfoServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Object sayHello(String name) {
        return name+",你好，调通了！";
    }

    @Override
    public Object passInfo(Map<String, String> info) {
        System.out.println("恭喜你，调通了，参数："+JSON.toJSONString(info));
        info.put("msg","你好，调通了！");
        return info;
    }

}
