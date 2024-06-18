package com.appfullstack.backend.tests;

import java.time.LocalDate;

import com.appfullstack.backend.entities.Category;
import com.appfullstack.backend.entities.Product;
import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.enums.Rating;

public class ProductFactory {

	public static Product createProduct() {
		Category category = CategoryFactory.createCategory();
		Supplier supplier = SupplierFactory.createSupplier();
		Product product = new Product(1L, "Rubik's cube", 52.99, LocalDate.parse("2024-04-22"), Rating.EXCELENT, "Professional rubik's cube for competitions");
		product.setSupplier(supplier);
		product.getCategories().add(category);
		return product;
	}
	
	public static Product createProduct(String name) {
		Product product = createProduct();
		product.setName(name);
		return product;
	}
}
