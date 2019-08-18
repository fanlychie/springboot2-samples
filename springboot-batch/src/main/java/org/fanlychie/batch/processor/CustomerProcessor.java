package org.fanlychie.batch.processor;

import org.fanlychie.batch.dto.CustomerDto;
import org.fanlychie.batch.entity.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 数据处理器
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@Component
public class CustomerProcessor implements ItemProcessor<List<CustomerDto>, List<Customer>> {

    // 数据处理
    @Override
    public List<Customer> process(List<CustomerDto> items) throws Exception {
        List<Customer> customers = new ArrayList<>();
        for (CustomerDto item : items) {
            Customer customer = new Customer();
            BeanUtils.copyProperties(item, customer);
            customers.add(customer);
        }
        return customers;
    }

}