package org.fanlychie.multiple.datasource.sample.dao.test2;

import org.fanlychie.multiple.datasource.sample.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}