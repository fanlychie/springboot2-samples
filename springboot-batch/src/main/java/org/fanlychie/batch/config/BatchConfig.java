package org.fanlychie.batch.config;

import org.fanlychie.batch.dto.CustomerDto;
import org.fanlychie.batch.entity.Customer;
import org.fanlychie.batch.listener.CustomerJobListener;
import org.fanlychie.batch.processor.CustomerProcessor;
import org.fanlychie.batch.reader.CustomerReader;
import org.fanlychie.batch.writer.CustomerWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Spring Batch 配置
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job customerJob(CustomerJobListener listener) {
        return jobBuilderFactory
                // job 作业实例的名称
                .get("customer-job")
                // 增量
                .incrementer(new RunIdIncrementer())
                // job 作业监听器
                .listener(listener)
                // 启动 step
                .start(customerStep(null, null, null))
                // 构建
                .build();
    }

    @Bean
    public Step customerStep(CustomerReader reader, CustomerWriter writer, CustomerProcessor processor) {
        return stepBuilderFactory
                // step 实例的名称
                .get("customer-step")
                // 块大小, 数据由reader读入到chunk, 满了之后发送给writer处理写出
                .<List<CustomerDto>, List<Customer>>chunk(100)
                // 读取数据
                .reader(reader)
                // 处理数据
                .processor(processor)
                // 写出数据
                .writer(writer)
                // 构建
                .build();
    }

}