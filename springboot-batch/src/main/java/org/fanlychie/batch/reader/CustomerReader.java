package org.fanlychie.batch.reader;

import com.github.fanlychie.excelutils.read.ExcelReaderBuilder;
import com.github.fanlychie.excelutils.read.PagingHandler;
import com.github.fanlychie.excelutils.write.ExcelWriterBuilder;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.fanlychie.batch.dto.CustomerDto;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据读取器
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@Component
@StepScope
public class CustomerReader implements ItemReader<List<CustomerDto>> {

    @Value("#{jobParameters[filename]}")
    private String filename;

    @Override
    public List<CustomerDto> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return new ExcelReaderBuilder()
                .stream(filename)
                .payload(CustomerDto.class)
                .build()
                .read();
    }

}