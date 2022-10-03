package com.nagarciah.tinypim.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Product {

	/**
	 * As per the sample data, we can safely rely on an autoincrement id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String name;

	public Product() {
	}

	public Product(Long id, @NotNull String name, @NotNull ProductOnlineStatus onlineStatus, String shortDescription,
			String longDescription) {
		super();
		this.id = id;
		this.name = name;
		this.onlineStatus = onlineStatus;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
	}

	@NotNull
	private ProductOnlineStatus onlineStatus;

	private String shortDescription;

	private String longDescription;

	@ManyToMany
	private List<Category> categories;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductOnlineStatus getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(ProductOnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
