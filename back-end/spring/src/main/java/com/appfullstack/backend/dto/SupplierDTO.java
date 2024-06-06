package com.appfullstack.backend.dto;

import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.enums.Sector;

public class SupplierDTO {

	private Long id;
	private String name;
	private String contactInfo;
	private Integer foundationYear;
	
	private Sector sector;
	
	public SupplierDTO() {
	}

	public SupplierDTO(Long id, String name, String contactInfo, Integer foundationYear, Sector sector) {
		this.id = id;
		this.name = name;
		this.contactInfo = contactInfo;
		this.foundationYear = foundationYear;
		this.sector = sector;
	}
	
	public SupplierDTO(Supplier entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.contactInfo = entity.getContactInfo();
		this.foundationYear = entity.getFoundationYear();
		this.sector = entity.getSector();
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

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Integer getFoundationYear() {
		return foundationYear;
	}

	public void setFoundationYear(Integer foundationYear) {
		this.foundationYear = foundationYear;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}
}
