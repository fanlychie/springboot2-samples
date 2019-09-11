package org.fanlychie.multiple.datasource.sample.mapper;

import org.fanlychie.multiple.datasource.sample.annotation.DataSource;
import org.fanlychie.multiple.datasource.sample.entity.Product;
import org.fanlychie.multiple.datasource.sample.routing.RoutingDataSource.DataSourceEnum;

import java.util.List;

@DataSource(DataSourceEnum.DB2)
public interface ProductMapper {

    List<Product> findAll();

}