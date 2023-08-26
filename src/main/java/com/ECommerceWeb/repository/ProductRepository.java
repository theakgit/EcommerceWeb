package com.ECommerceWeb.repository;

import com.ECommerceWeb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

      @Query("SELECT p FROM Product p " +
              "WHERE (p.category.name = :category OR :category = '') " +
              "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountPrice BETWEEN :minPrice AND :maxPrice)) " +
              "AND (:minDiscount IS NULL OR p.discountPresent >= :minDiscount) " +
              "ORDER BY " +
              "CASE WHEN :sort = 'price_low' THEN p.discountPrice END ASC, " +
              "CASE WHEN :sort = 'price_high' THEN p.discountPrice END DESC")


      public List<Product>filterProduct(@Param("category") String category,
                                        @Param("minPrice") Integer minPrice,
                                        @Param("maxPrice") Integer maxPrice,
                                        @Param("minDiscount") Integer minDiscount,
                                        @Param("sort") String sort);

}
