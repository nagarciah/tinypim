package com.nagarciah.tinypim.api;

import java.util.List;

import com.nagarciah.tinypim.entity.Product;

public class ProductRequest extends Product {

	private List<Long> parentCategoriesId;

	public List<Long> getParentCategoriesId() {
		return parentCategoriesId;
	}

	public void setParentCategoriesId(List<Long> parentCategoriesId) {
		this.parentCategoriesId = parentCategoriesId;
	}

	public Product toProduct() {
		return new Product(getId(), getName(), getOnlineStatus(), getShortDescription(), getLongDescription());
	}
}
