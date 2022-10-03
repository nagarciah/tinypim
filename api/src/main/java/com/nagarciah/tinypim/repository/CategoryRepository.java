package com.nagarciah.tinypim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarciah.tinypim.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
