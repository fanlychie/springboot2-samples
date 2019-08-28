package org.fanlychie.batch.processor;

import org.fanlychie.batch.entity.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 数据处理器
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@Component
public class CustomerProcessor implements ItemProcessor<String, Customer> {

    // 数据处理
    @Override
    public Customer process(String item) throws Exception {
        Customer customer = new Customer();
        String[] items = item.split(",");
        customer.setName(items[0]);
        customer.setMobile(items[1]);
        customer.setAge(Integer.parseInt(items[2]));
        return customer;
    }

}