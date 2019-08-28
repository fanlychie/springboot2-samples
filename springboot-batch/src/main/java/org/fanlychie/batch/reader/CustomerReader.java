package org.fanlychie.batch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 数据读取器
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@Component
@StepScope
public class CustomerReader implements ItemReader<String> {

    private int index;

    @Value("#{jobParameters[filename]}")
    private String filename;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(filename), "UTF-8"))) {
            String read;
            int current = 0;
            while ((read = reader.readLine()) != null) {
                if (current++ >= index) {
                    index++;
                    return read;
                }
            }
        }
        return null;
    }

}