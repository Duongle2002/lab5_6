package com.example.lab5.repository;

import com.example.lab5.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository hỗ trợ sẵn phương thức findAll(Pageable pageable)
}
