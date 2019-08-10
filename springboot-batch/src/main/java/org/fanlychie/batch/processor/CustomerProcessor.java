package org.fanlychie.batch.processor;

import org.fanlychie.batch.dto.CustomerDto;
import org.fanlychie.batch.entity.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by fanlychie on 2019/7/23.
 * @author fanlychie
 */
@Component
public class CustomerProcessor implements ItemProcessor<Customer, CustomerDto> {

    // 数据处理
    @Override
    public CustomerDto process(Customer item) throws Exception {
        CustomerDto dto = new CustomerDto();
        BeanUtils.copyProperties(item, dto);
        return dto;
    }

}