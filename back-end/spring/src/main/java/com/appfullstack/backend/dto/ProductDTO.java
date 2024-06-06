package com.appfullstack.backend.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.appfullstack.backend.entities.Category;
import com.appfullstack.backend.entities.Product;
import com.appfullstack.backend.enums.Rating;

public class ProductDTO {

	private Long id;
	private String name;
	private Double price;
	private LocalDate manufactureDate;
	private Rating rating;
	private String description;
	
	private SupplierDTO supplier;
	
    private Set<CategoryDTO> categories = new HashSet<>();
    
    public ProductDTO() {
    }

	public ProductDTO(Long id, String name, Double price, LocalDate manufactureDate, Rating rating, String description,	SupplierDTO supplier) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.manufactureDate = manufactureDate;
		this.rating = rating;
		this.description = description;
		this.supplier = supplier;
	}
    
    public ProductDTO(Product entity) {
    	this.id = entity.getId();
    	this.name = entity.getName();
    	this.price = entity.getPrice();
    	this.manufactureDate = entity.getManufactureDate();
    	this.rating = entity.getRating();
    	this.description = entity.getDescription();
    	this.supplier = new SupplierDTO(entity.getSupplier());
    	for (Category cat : entity.getCategories()) {
    		categories.add(new CategoryDTO(cat));
    	}
    }

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(LocalDate manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SupplierDTO getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierDTO supplier) {
		this.supplier = supplier;
	}

	public Set<CategoryDTO> getCategories() {
		return categories;
	}
}
