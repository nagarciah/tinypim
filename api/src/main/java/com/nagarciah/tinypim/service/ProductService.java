package com.nagarciah.tinypim.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarciah.tinypim.entity.Product;
import com.nagarciah.tinypim.repository.ProductRepository;

@Service
public class ProductService {

	ProductRepository productRepository;

	CategoryService categoryService;

	@Autowired
	public ProductService(ProductRepository productRepository, CategoryService categoryService) {
		super();
		this.productRepository = productRepository;
		this.categoryService = categoryService;
	}

	public boolean productExists(Long id) {
		return productRepository.existsById(id);
	}

	public Optional<Product> get(Long id) {
		return productRepository.findById(id);
	}

	public List<Product> get() {
		return productRepository.findAll();
	}

	public Product add(Product product, List<Long> parentCategories) {
		product.setCategories(Collections.emptyList());
		
		if (product.getId() != null && productExists(product.getId())) {
			throw new ResourceAlreadyExistsException();
		}

		if (parentCategories == null || parentCategories.isEmpty()) {
			throw new InvalidParentException();
		}

		product.setCategories(new ArrayList<>());

		parentCategories.stream().forEach(c -> {
			var category = categoryService.get(c)
					.orElseThrow(() -> new InvalidParentException("Category %s doesn't exist".formatted(c)));
			product.getCategories().add(category);
		});

		return productRepository.save(product);
	}

	public Product saveOrUpdate(Product product, List<Long> parentCategories) {
		if (parentCategories == null || parentCategories.isEmpty()) {
			throw new InvalidParentException();
		}

		product.setCategories(new ArrayList<>());

		parentCategories.stream().forEach(c -> {
			var category = categoryService.get(c)
					.orElseThrow(() -> new InvalidParentException("Category %s doesn't exist".formatted(c)));
			product.getCategories().add(category);
		});
		
		return productRepository.save(product);
	}

	public void delete(Long productId) {
		productRepository.deleteById(productId);
	}
}
