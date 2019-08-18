package org.fanlychie.batch.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 运行器
 *
 * @author fanlychie
 * @since 2019/8/19
 */
@Component
public class CustomerLauncher {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher launcher;

    public void startup(String filename) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("filename", filename)
                .toJobParameters();
        launcher.run(job, jobParameters);
    }

}