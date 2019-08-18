package org.fanlychie.batch.dao;

import org.fanlychie.batch.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 客户实体DAO
 *
 * @author fanlychie
 * @since 2019/8/18
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}