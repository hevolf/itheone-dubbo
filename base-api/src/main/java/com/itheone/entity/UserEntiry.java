package com.itheone.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Peter on 11/12 012.
 */
@Data
public class UserEntiry  implements Serializable {
    private String id;
    private String name;
    private String address;
    private long balance;

}
