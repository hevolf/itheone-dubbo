package com.itheone.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Peter on 11/12 012.
 */
@Data
public class ProductEntiry  implements Serializable {
    private String id;
    private long price;
    private String name;
    private int status;//上下架
}
