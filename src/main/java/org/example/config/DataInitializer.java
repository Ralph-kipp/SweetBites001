package org.example.config;

import org.example.Product;
import org.example.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initProducts (ProductRepository productRepository){
         return args -> {
             Product product1 = new Product();
             product1.setId(1L);
             product1.setName("Brownies");
             product1.setPrice(150.00);
             productRepository.save(product1);

             Product product2 = new Product();
             product2.setId(2L);
             product2.setName("Cookies");
             product2.setPrice(150.00);
             productRepository.save(product2);
         };

    }
}
