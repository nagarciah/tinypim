package com.nagarciah.tinypim.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nagarciah.tinypim.entity.Category;
import com.nagarciah.tinypim.entity.Product;

public class ProductRequest extends Product {

	private List<Long> parentCategoriesId;

	public Product toProduct() {
		return new Product(getId(), getName(), getOnlineStatus(), getShortDescription(), getLongDescription());
	}
	
	public List<Long> getParentCategoriesId() {
		return parentCategoriesId;
	}

	public void setParentCategoriesId(List<Long> parentCategoriesId) {
		this.parentCategoriesId = parentCategoriesId;
	}

	// Prevents showing also the Category objects as part of the REST documentation
	@JsonIgnore
	@Override
	public List<Category> getCategories() {
		return super.getCategories();
	}

	@JsonIgnore
	public void setCategories(List<Category> categories) {
		super.setCategories(categories);
	}
}
