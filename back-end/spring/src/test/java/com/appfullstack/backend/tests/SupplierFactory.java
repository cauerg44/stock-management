package com.appfullstack.backend.tests;

import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.enums.Sector;

public class SupplierFactory {

	public static Supplier createSupplier() {
		return new Supplier(1L, "Larshooping", "2345-1231", 2000, Sector.HOME_FUNITURE);
	}
	
	public static Supplier createSupplier(String name) {
		Supplier supplier = createSupplier();
		supplier.setName(name);
		return supplier;
	}
}
