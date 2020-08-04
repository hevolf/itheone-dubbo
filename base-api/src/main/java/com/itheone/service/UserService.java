package com.itheone.service;


import com.itheone.entity.UserEntiry;

public interface UserService {
    UserEntiry getDetail(String id);
    UserEntiry regist(UserEntiry user);
    UserEntiry recharge(String id, long money);
}
