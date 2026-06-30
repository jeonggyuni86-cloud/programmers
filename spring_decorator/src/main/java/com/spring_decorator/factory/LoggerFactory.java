package com.spring_decorator.factory;

import com.spring_decorator.logger.FileLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerFactory {
    @Bean
    public FileLogger fileLogger() {
        return new FileLogger();
    }
}
