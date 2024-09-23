package com.cp.kku.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cp.kku.demo.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
