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

import com.nagarciah.tinypim.entity.Product;
import com.nagarciah.tinypim.service.ProductService;
import com.nagarciah.tinypim.service.TinypimException;

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
@RequestMapping("/product")
public class ProductApi {

	private static final String HUMAN_READABLE_DETAILS_HEADER = "detail";
	private static final Logger log = LoggerFactory.getLogger(ProductApi.class);

	@Autowired
	private ProductService productService;

	@GetMapping(path = "/{productId}")
	public ResponseEntity<Product> get(@PathVariable Long productId) {
		return ResponseEntity.of(productService.get(productId));
	}

	@GetMapping
	public List<Product> getAll() {
		return productService.get();
	}

	/**
	 * Creates a product if it doesn't exist already
	 * @param productRequest
	 * @param parentCategoriesId
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest productRequest) {
		try {
			Product newResource = productService.add(productRequest.toProduct(), productRequest.getParentCategoriesId());

			// This is a naive code to calculate the location because it depends on the
			// deployment and other intermediary network entities, for example, multiple
			// instances of the service behind a domain, a load balancer and an API gateway.
			// KISS for the demo
			URI location = new URI("http://localhost:8080/product/%s".formatted(newResource.getId()));
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setLocation(location);
			return new ResponseEntity<Product>(productRequest, responseHeaders, HttpStatus.CREATED);
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
	 * Offers a safe way to update a product only if it already exists, otherwise
	 * an HTTP 404 response is returned
	 * 
	 * @param productId
	 * @param productRequest
	 * @return
	 */
	// TODO offer a better way to set the categories by using the ProductRequest
	@PutMapping(path = "/{productId}")
	public ResponseEntity<Product> update(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest) {
		if (productService.productExists(productId)) {
			productRequest.setId(productId);
			return ResponseEntity.of(Optional.of(
					productService.saveOrUpdate(productRequest.toProduct(), productRequest.getParentCategoriesId())
				));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = "/{productId}")
	public void delete(@PathVariable Long productId) {
		productService.delete(productId);
	}
}
