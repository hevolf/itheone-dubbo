package com.itheone.service;

public interface PayService {
    boolean pay(long money);
    boolean cancelPay(long money);
}
