package org.fanlychie.batch.reader;

import org.fanlychie.batch.dto.CustomerDto;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class CustomerReader implements ItemReader<CustomerDto> {

    @Override
    public CustomerDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }

}