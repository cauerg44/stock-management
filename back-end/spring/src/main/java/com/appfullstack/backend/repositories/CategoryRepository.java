package com.appfullstack.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appfullstack.backend.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
