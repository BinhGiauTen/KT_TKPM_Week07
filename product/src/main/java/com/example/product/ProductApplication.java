package com.example.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProductApplication {
	
	 @GetMapping("/product")
	    public Product getProduct() throws InterruptedException {
	        
//	        Thread.sleep(10000);

	        
	        Product product = new Product();
	        product.setId("1");
	        product.setName("Sach");
	        return product;
	    }

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}
