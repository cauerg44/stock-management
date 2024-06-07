package com.appfullstack.backend.tests;

import com.appfullstack.backend.entities.Category;

public class CategoryFactory {

	public static Category createCategory() {
		Category category = new Category(1L, "Games");
		return category;
	}
	
	public static Category createCategory(String name) {
		Category Category = createCategory();
		Category.setName(name);
		return Category;
	}
}
