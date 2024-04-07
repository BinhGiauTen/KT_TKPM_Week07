package com.example.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableRetry
public class UserApplication {
	
	@Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(5000); // Retry after 5 seconds
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5); // Maximum retry attempts
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    @GetMapping("/getProductData")
    public String getProductData(RestTemplate restTemplate) {
        try {
            RetryTemplate retryTemplate = retryTemplate();
            return retryTemplate.execute(context -> restTemplate.getForObject("http://localhost:8080/product", String.class));
        } catch (ResourceAccessException ex) {
            return "Error: Unable to access product service";
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
