package org.fanlychie.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Job 监听器
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@Slf4j
@Component
public class CustomerJobListener implements JobExecutionListener {

    // job 执行开始前执行
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(">>>>>> id: {}, name: {}, time: {}",
                jobExecution.getJobId(),
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStartTime());
    }

    // job 执行完成后执行
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("<<<<<< id: {}, name: {}, time: {}",
                jobExecution.getJobId(),
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getEndTime());
    }

}