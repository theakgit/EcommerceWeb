package com.ECommerceWeb.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Product {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  @Column(name="description")
    private String description;

  @Column(name="title")
  private String title;

  @Column(name="price")
    private int price;

  @Column(name="discount_price")
    private int discountPrice;

  @Column(name="discount_present")
   private int discountPresent;
  @Column(name="quantity")
    private int quantity;

  @Column(name="brand")
  private String brand;

  @Column(name="color")
  private String color;

  @Embedded
  @ElementCollection
  @Column(name="sizes")
  private Set<Size> sizes= new HashSet<>();

  @Column(name="image_url")
  private String imageUrl;

  @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
  private List<Rating> ratings = new ArrayList<>();

  @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();

  @Column(name="num_ratings")
  private int numRatings;

  @ManyToOne()
  @JoinColumn(name = "category_id")
  private Category category;

  private LocalDateTime createdAt;

  public Product(){}

  public Product(Long id, String description, int price, int discountPrice, int discountPresent, int quantity, String brand, String color, Set<Size> sizes, String imageUrl, List<Rating> ratings, List<Review> reviews, int numRatings, Category category, LocalDateTime createdAt) {
    this.id = id;
    this.description = description;
    this.price = price;
    this.discountPrice = discountPrice;
    this.discountPresent = discountPresent;
    this.quantity = quantity;
    this.brand = brand;
    this.color = color;
    this.sizes = sizes;
    this.imageUrl = imageUrl;
    this.ratings = ratings;
    this.reviews = reviews;
    this.numRatings = numRatings;
    this.category = category;
    this.createdAt = createdAt;
  }
}
