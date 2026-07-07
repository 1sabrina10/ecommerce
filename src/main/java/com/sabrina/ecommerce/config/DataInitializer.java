/*
package com.sabrina.ecommerce.config;

import com.sabrina.ecommerce.entity.*;
import com.sabrina.ecommerce.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        initUsers();
        initCategories();
        initProducts();
    }

    // ── Users ──────────────────────────────────────
    private void initUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@ecommerce.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            User client = new User();
            client.setUsername("sabrina");
            client.setEmail("sabrina@ecommerce.com");
            client.setPassword(passwordEncoder.encode("1234"));
            client.setRole(Role.CLIENT);
            userRepository.save(client);

            log.info("✅ Users créés");
        }
    }

    // ── Catégories ─────────────────────────────────
    private void initCategories() {
        if (categoryRepository.count() == 0) {
            Category electronique = new Category();
            electronique.setName("Électronique");
            electronique.setDescription("Téléphones, ordinateurs, tablettes");
            categoryRepository.save(electronique);

            Category vetements = new Category();
            vetements.setName("Vêtements");
            vetements.setDescription("Habits, chaussures, accessoires");
            categoryRepository.save(vetements);

            Category alimentation = new Category();
            alimentation.setName("Alimentation");
            alimentation.setDescription("Nourriture et boissons");
            categoryRepository.save(alimentation);

            log.info("✅ Catégories créées");
        }
    }

    // ── Produits ───────────────────────────────────
    private void initProducts() {
        if (productRepository.count() == 0) {
            Category electronique = categoryRepository.findAll().get(0);
            Category vetements = categoryRepository.findAll().get(1);
            Category alimentation = categoryRepository.findAll().get(2);

            // Électronique
            Product iphone = new Product();
            iphone.setName("iPhone 15");
            iphone.setDescription("Smartphone Apple 128Go");
            iphone.setPrice(new BigDecimal("999.99"));
            iphone.setStock(50);
            iphone.setCategory(electronique);
            productRepository.save(iphone);

            Product laptop = new Product();
            laptop.setName("MacBook Pro");
            laptop.setDescription("Ordinateur portable Apple M3");
            laptop.setPrice(new BigDecimal("1999.99"));
            laptop.setStock(20);
            laptop.setCategory(electronique);
            productRepository.save(laptop);

            // Vêtements
            Product tshirt = new Product();
            tshirt.setName("T-shirt Nike");
            tshirt.setDescription("T-shirt sport blanc");
            tshirt.setPrice(new BigDecimal("29.99"));
            tshirt.setStock(100);
            tshirt.setCategory(vetements);
            productRepository.save(tshirt);

            // Alimentation
            Product cafe = new Product();
            cafe.setName("Café Arabica");
            cafe.setDescription("Café premium 500g");
            cafe.setPrice(new BigDecimal("12.99"));
            cafe.setStock(200);
            cafe.setCategory(alimentation);
            productRepository.save(cafe);

            log.info("✅ Produits créés");
        }
    }
}
*/
