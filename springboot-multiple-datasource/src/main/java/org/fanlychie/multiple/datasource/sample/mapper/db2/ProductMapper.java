package org.fanlychie.multiple.datasource.sample.mapper.db2;

import org.fanlychie.multiple.datasource.sample.entity.db2.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> findAll();

}