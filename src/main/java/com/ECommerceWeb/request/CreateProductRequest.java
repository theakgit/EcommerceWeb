package com.ECommerceWeb.request;

import com.ECommerceWeb.entity.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data

public class CreateProductRequest {

    private String title;


    private String description;

    private int price;

    private int discountPrice;

    private int discountPresent;

    private int quantity;

    private String brand;

    private String color;
    private Set<Size> size = new HashSet<>();

    private String imageUrl;

    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;

}
