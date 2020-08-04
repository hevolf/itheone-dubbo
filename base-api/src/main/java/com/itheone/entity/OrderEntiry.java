package com.itheone.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 11/12 012.
 */
@Data
public class OrderEntiry  implements Serializable {
    private String id;
    private long money;
    private String userId;
    private int status = 0;
    private List<ProductEntiry> productlist = new ArrayList<>();

}
