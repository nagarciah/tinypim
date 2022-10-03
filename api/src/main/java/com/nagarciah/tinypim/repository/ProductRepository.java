package com.nagarciah.tinypim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarciah.tinypim.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
