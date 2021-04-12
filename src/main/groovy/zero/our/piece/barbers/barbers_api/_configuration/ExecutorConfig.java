package zero.our.piece.barbers.barbers_api._configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ExecutorConfig {

  @Bean(name = "BarberShopThreadPool")
  public Executor BarberShopThreadPool() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(5);
    executor.setQueueCapacity(200);
    executor.setThreadNamePrefix("Art-Experience");
    executor.initialize();
    return executor;
  }
}
