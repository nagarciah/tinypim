package com.nagarciah.tinypim.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarciah.tinypim.entity.Category;
import com.nagarciah.tinypim.repository.CategoryRepository;

@Service
public class CategoryService {

	CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	public boolean categoryExists(Long id) {
		return categoryRepository.existsById(id);
	}

	public Optional<Category> get(Long id) {
		return categoryRepository.findById(id);
	}

	public List<Category> get() {
		return categoryRepository.findAll();
	}

	public Category add(Long parentId, Category category) {
		if (category.getId() != null && categoryExists(category.getId())) {
			throw new ResourceAlreadyExistsException();
		}

		var parent = get(parentId);
		if (parent.isEmpty()) {
			throw new InvalidParentException();
		}

		category.setParent(parent.get());
		return categoryRepository.save(category);
	}

	public Category saveOrUpdate(Category category) {
		return categoryRepository.save(category);
	}

	public void delete(Long categoryId) {
		categoryRepository.deleteById(categoryId);
	}
}
