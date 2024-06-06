package com.appfullstack.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appfullstack.backend.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
