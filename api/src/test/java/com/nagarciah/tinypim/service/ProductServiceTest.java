package com.nagarciah.tinypim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nagarciah.tinypim.entity.Category;
import com.nagarciah.tinypim.entity.Product;
import com.nagarciah.tinypim.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock 
	private ProductRepository productRepositoryMock;
	
	@Mock 
	private CategoryService categoryServiceMock;
	
	@InjectMocks
	private ProductService productService;

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@DisplayName("Test a successful creation of a product")
	@Test
	void shouldAddNewProduct() {
		// Arrange
		when(categoryServiceMock.get(200L))
			.thenReturn(Optional.of(new Category()));
	
		when(categoryServiceMock.get(300L))
			.thenReturn(Optional.of(new Category()));
		
		when(productRepositoryMock.save(any(Product.class)))
			.thenAnswer(invocation -> invocation.getArgument(0));
	
		// Act
		var newProduct = productService.add(new Product(), Arrays.asList(200L, 300L));
		
		// Assert
		verify(productRepositoryMock).save(any(Product.class));
		
		assertThat(newProduct.getCategories()).hasSize(2);
	}
	
	@DisplayName("Rejects creation of a product if the category doesn't exist")
	@Test
	void shouldFailWhenInvalidParentCategory() {
		// Arrange
		when(categoryServiceMock.get(anyLong()))
			.thenReturn(Optional.empty());
	
		// Act + Assert
		assertThrows(InvalidParentException.class, () -> {
		      productService.add(new Product(), Arrays.asList(1000L));
	    });
	}
}
