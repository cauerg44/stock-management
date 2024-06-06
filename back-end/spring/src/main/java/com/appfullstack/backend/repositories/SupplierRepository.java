package com.appfullstack.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appfullstack.backend.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
