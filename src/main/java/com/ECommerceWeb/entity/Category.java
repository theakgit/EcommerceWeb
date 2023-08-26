package com.ECommerceWeb.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @NotNull
    @Size(max=50)
   private String name;


   @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="parent_category_id")
    private Category parentCategory;

   private int level;

   public Category(){}

    public Category(Long id, String name, Category parentCategory, int level) {
        this.id = id;
        this.name=name;
        this.parentCategory = parentCategory;
        this.level = level;
    }
}
