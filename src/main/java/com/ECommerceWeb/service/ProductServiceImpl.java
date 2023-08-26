package com.ECommerceWeb.service;

import com.ECommerceWeb.entity.Category;
import com.ECommerceWeb.entity.Product;
import com.ECommerceWeb.exception.ProductException;
import com.ECommerceWeb.repository.CategoryRepository;
import com.ECommerceWeb.repository.ProductRepository;
import com.ECommerceWeb.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl  implements  ProductService{

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService,CategoryRepository categoryRepository){
        this.productRepository= productRepository;
        this.userService= userService;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest productRequest) {
       Category topLevel=categoryRepository.findByName(productRequest.getTopLevelCategory());
             if(topLevel == null){
                 Category topLevelCategory = new Category();
                 topLevelCategory.setName(productRequest.getTopLevelCategory());
                 topLevelCategory.setLevel(1);

                 topLevel = categoryRepository.save(topLevelCategory);
             }
        Category secondLevel = categoryRepository.findByNameAndParent(productRequest.getSecondLevelCategory(), topLevel.getName());
                      if(secondLevel == null){
                          Category secondLevelCategory=new Category();
                          secondLevelCategory.setName(productRequest.getSecondLevelCategory());
                          secondLevelCategory.setLevel(2);
                          secondLevelCategory.setParentCategory(topLevel);

                          secondLevel=categoryRepository.save(secondLevelCategory);
                      }
        Category thirdLevel = categoryRepository.findByNameAndParent(productRequest.getThirdLevelCategory(), secondLevel.getName());
        if(thirdLevel == null){
            Category thirdLevelCategory=new Category();
            thirdLevelCategory.setName(productRequest.getSecondLevelCategory());
            thirdLevelCategory.setLevel(3);
            thirdLevelCategory.setParentCategory(secondLevel);

            thirdLevel=categoryRepository.save(thirdLevelCategory);
        }
        Product product = new Product();
        product.setTitle(productRequest.getTitle());
        product.setColor(productRequest.getColor());
        product.setDescription(productRequest.getDescription());
        product.setDiscountPrice(productRequest.getDiscountPrice());
        product.setDiscountPresent(productRequest.getDiscountPresent());
        product.setImageUrl(productRequest.getImageUrl());
        product.setBrand(productRequest.getBrand());
        product.setPrice(productRequest.getPrice());
        product.setSizes(productRequest.getSize());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product saveProduct = productRepository.save(product);
        return saveProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
          Product product =  findProductById(productId);
          product.getSizes().clear();
          productRepository.delete(product);

        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product =  findProductById(productId);

        if(req.getQuantity()!=0){
            product.setQuantity(req.getQuantity());

        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt= productRepository.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with id");


    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        //get all products
        List<Product>products = productRepository.filterProduct(category,minPrice,maxPrice,minDiscount,sort);

        if(!colors.isEmpty()){
            products=products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());

        }
              if(stock!=null){
                  if(stock.equals(("in-stock"))){
                     products= products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
                  } else if (stock.equals("out of stock")) {
                      products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());

                  }
              }
              int startIndex = (int)pageable.getOffset();
                int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());

                List<Product> pageContent=products.subList(startIndex,endIndex);

                Page<Product> filterProducts = new PageImpl<>(pageContent,pageable,products.size());


                return filterProducts;
    }
}
