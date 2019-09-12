package org.fanlychie.scheduled.sample.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduleDemo {

    private static DateFormat format = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");

    // 延迟1秒后开始执行, 从本次执行完成到下一次开始执行的时间间隔为3秒
    // 第一次: 00(秒)
    // 第二次: 04(秒)
    // 第三次: 08(秒)
    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    public void demo1() throws InterruptedException {
        System.out.println(String.format("[DEMO.1] %s", format.format(new Date())));
        // 模拟耗时
        TimeUnit.SECONDS.sleep(1);
    }

    // 延迟1秒后开始执行, 以后每隔3秒执行一次
    // 第一次: 00(秒)
    // 第二次: 03(秒)
    // 第三次: 06(秒)
    @Scheduled(initialDelay = 1000, fixedRate = 3000)
    public void demo2() throws InterruptedException {
        System.out.println(String.format("[DEMO.2] %s", format.format(new Date())));
        // 模拟耗时
        TimeUnit.SECONDS.sleep(1);
    }

    // 每隔3秒执行一次
    // 第一次: 00(秒)
    // 第二次: 03(秒)
    // 第三次: 06(秒)
    @Scheduled(cron = "*/3 * * * * ?")
    public void demo3() throws InterruptedException {
        System.out.println(String.format("[DEMO.3] %s", format.format(new Date())));
        // 模拟耗时
        TimeUnit.SECONDS.sleep(1);
    }

    // 同 fixedDelay, fixedDelayString 支持占位符使用
    @Scheduled(initialDelay = 1000, fixedDelayString = "${spring.scheduled.fixed-delay-demo}")
    public void demo4() throws InterruptedException {
        System.out.println(String.format("[DEMO.4] %s", format.format(new Date())));
        // 模拟耗时
        TimeUnit.SECONDS.sleep(1);
    }

    // 同 fixedRate, fixedRateString 支持占位符使用
    @Scheduled(initialDelay = 1000, fixedRateString = "${spring.scheduled.fixed-rate-demo}")
    public void demo5() throws InterruptedException {
        System.out.println(String.format("[DEMO.5] %s", format.format(new Date())));
        // 模拟耗时
        TimeUnit.SECONDS.sleep(1);
    }

}