package ApiEx.withWebFlux.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

@Slf4j
@Configuration
@EnableScheduling
@EnableConfigurationProperties(AdjustmentProperties.class)
public class AsyncConfig {

    @Bean
    @ConditionalOnMissingBean
    RejectedExecutionHandler rejectedExecutionHandler() {
        return (r, executor) -> log.info("task failed — {}", r);
    }

    @Bean (name="executor")
    public ThreadPoolExecutor executor(
            AdjustmentProperties adjustmentProperties,
            RejectedExecutionHandler rejectedExecutionHandler
    ) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                adjustmentProperties.getMinProcessorConcurrencyLevel(),
                adjustmentProperties.getMaxProcessorConcurrencyLevel(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(adjustmentProperties.getLetterBoxSize()),
                new BasicThreadFactory.Builder()
                        .namingPattern("thread-%d")
                        .daemon(true)
                        .priority(Thread.MAX_PRIORITY)
                        .build()
        );

        threadPoolExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);

        threadPoolExecutor.prestartAllCoreThreads();
        return threadPoolExecutor;
    }
}
