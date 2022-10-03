package com.nagarciah.tinypim.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.nagarciah.tinypim.entity.Category;
import com.nagarciah.tinypim.service.TinypimException;
import com.nagarciah.tinypim.service.CategoryService;

/**
 * Responses use a combination of Optional (to avoid null pointer exceptions)
 * and ResponseEntity to leverage Spring's HTTP layer to automatically reply
 * with HTTP 404 when there is no data, following the REST specification
 * 
 * The controller also depends on DI and other goodies provided by Spring
 * Framework
 * 
 * @author garcian
 */
@RestController
@RequestMapping("/category")
public class CategoryApi {

	private static final String HUMAN_READABLE_DETAILS_HEADER = "detail";
	private static final Logger log = LoggerFactory.getLogger(CategoryApi.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping(path = "/{categoryId}")
	public ResponseEntity<Category> get(@PathVariable Long categoryId) {
		return ResponseEntity.of(categoryService.get(categoryId));
	}

	@GetMapping
	public List<Category> getAll() {
		return categoryService.get();
	}

	/**
	 * Creates a category if it doesn't exist already
	 * @param category
	 * @param parentId
	 * @return
	 */
	@PostMapping("/parent/{parentId}")
	public ResponseEntity<Category> create(@Valid @RequestBody Category category,
			@PathVariable(required = true) Long parentId) {
		try {
			Category newResource = categoryService.add(parentId, category);

			// This is a naive code to calculate the location because it depends on the
			// deployment and other intermediary network entities, for example, multiple
			// instances of the service behind a domain, a load balancer and an API gateway.
			// KISS for the demo
			URI location = new URI("http://localhost:8080/category/%s".formatted(newResource.getId()));
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setLocation(location);
			return new ResponseEntity<Category>(category, responseHeaders, HttpStatus.CREATED);
		} catch (URISyntaxException e) {
			log.warn("Error generating URI for a REST resource", e);
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (TinypimException e) {
			return ResponseEntity.unprocessableEntity()
					.header(HUMAN_READABLE_DETAILS_HEADER, "%s: %s".formatted(e.getClass().getSimpleName(), e.getMessage()))
					.build();
		}
	}

	/**
	 * Offers a safe way to update a category only if it already exists, otherwise
	 * an HTTP 404 response is returned
	 * 
	 * @param categoryId
	 * @param category
	 * @return
	 */
	@PutMapping(path = "/{categoryId}")
	public ResponseEntity<Category> update(@PathVariable Long categoryId, @Valid @RequestBody Category category) {
		if (categoryService.categoryExists(categoryId)) {
			category.setId(categoryId);
			return ResponseEntity.of(Optional.of(categoryService.saveOrUpdate(category)));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = "/{categoryId}")
	public void delete(@PathVariable Long categoryId) {
		categoryService.delete(categoryId);
	}
}
