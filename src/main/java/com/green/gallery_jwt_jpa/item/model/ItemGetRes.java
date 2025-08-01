package com.green.gallery_jwt_jpa.item.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemGetRes {
    private int id;
    private String name;
    private String imgPath;
    private int price;
    private int discountPer;
}
