package org.fanlychie.batch.writer;

import org.fanlychie.batch.dao.CustomerRepository;
import org.fanlychie.batch.entity.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 写数据
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@Component
public class CustomerWriter implements ItemWriter<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        customerRepository.saveAll(items);
    }

}