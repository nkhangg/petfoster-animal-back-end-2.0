package com.poly.petfoster.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

        @Query("select p from Product p where p.id = :id")
        public Optional<Product> findById(@Param("id") String id);

        boolean existsById(String id);

        @Query("select p from Product p where p.isActive = true")
        public List<Product> findAll();

        // @Query(nativeQuery = true, value = "select* from product p inner join
        // product_repo pr on pr.product_id= p.product_id
        // // where p.product_id ='PD0001'")
        // public Optional<Product> findById(@Param("id") String id);

        // @Query("select p from product p where p.product_name = :name")
        // public Optional<Product> findByName(@Param("name") String name);

        @Query(nativeQuery = true, value = "select top 4 * from product p where p.is_active = 1 order by create_at desc")
        public List<Product> selectNewArrivals();

        // @Query(nativeQuery = true, value = "select * from product p join product_type
        // t on t.product_type_id = p.[type_id] where p.product_id in ( select top 100
        // order_detail.product_id from order_detail group by order_detail.product_id
        // order by sum(order_detail.quantity) desc)")
        @Query(nativeQuery = true, value = "select * from product p join product_type t on t.product_type_id = p.[type_id] "
                        + "where p.product_id in ( select top 100 product_id from order_detail group by product_id ) and p.is_active = 1")
        List<Product> findAllProducts();

        @Query("SELECT p, MIN(pr.outPrice) FROM Product p " +
                        "INNER JOIN p.productsRepo pr " +
                        "WHERE (:typeName IS NULL OR p.productType.name = :typeName) " +
                        "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (pr.outPrice BETWEEN :minPrice AND :maxPrice)) "
                        +
                        "AND (:stock IS NULL OR pr.inStock = :stock) " +
                        "AND (:brand IS NULL OR p.brand = :brand) " +
                        "AND (:productName IS NULL OR p.name LIKE %:productName%) " +
                        "AND p.isActive = true " +
                        "GROUP BY p.id, p.name, p.desc, p.isActive, p.brand, p.createAt, p.productType.id  " +
                        "ORDER BY " +
                        "CASE WHEN :sort = 'low' THEN MIN(pr.outPrice) END ASC, " +
                        "CASE WHEN :sort = 'high' THEN MIN(pr.outPrice) END DESC")

        List<Product> filterProducts(
                        @Param("typeName") String typeName,
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice,
                        @Param("stock") Boolean stock,
                        @Param("brand") String brand,
                        @Param("productName") String productName,
                        @Param("sort") String sort);

        @Query(nativeQuery = true, value = "select top 10 * from product " +
                        "where [type_id] = (select [type_id] from product where product_id = :id) and product_id != :id")
        public List<Product> getSameTypeProducts(@Param("id") String id);

        @Query(nativeQuery = true, value = "select distinct brand from product")
        public List<String> getProductBrands();

}
