package com.itheone.service;

import com.itheone.entity.ProductEntiry;

public interface ProductService {
    ProductEntiry getDetail(String id);
    ProductEntiry modify(ProductEntiry product);
    boolean status(String id, boolean upDown);
}
