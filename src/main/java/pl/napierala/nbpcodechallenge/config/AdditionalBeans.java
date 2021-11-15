package pl.napierala.nbpcodechallenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdditionalBeans {

    @Value("${rest.server.log.request.max.size}")
    private Integer maxRequestSize;

    @Bean
    @ConditionalOnProperty(name = "rest.server.log.request.enabled", havingValue = "true")
    public MyLoggingFilter requestLoggingFilter() {
        MyLoggingFilter loggingFilter = new MyLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(maxRequestSize);
        return loggingFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}