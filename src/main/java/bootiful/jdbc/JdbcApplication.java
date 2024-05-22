package bootiful.jdbc;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;

interface CustomerRepository extends ListCrudRepository<Customer, Integer> {

    String CUSTOMER_CACHE_KEY = "customers";


    @Cacheable(CUSTOMER_CACHE_KEY)
    @Override
    Optional<Customer> findById(Integer id);

    @CacheEvict(value =  CUSTOMER_CACHE_KEY, key = "#result.id")
    @Override
    <S extends Customer> S save(S entity);
}

@EnableCaching
@SpringBootApplication
public class JdbcApplication {


    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(CustomerRepository repository) {
        return args -> {
            Set<Order> orderSet = Set.of(new Order(null, "A-order"), new Order(null, "B-order"), new Order(null, "C-order"));
            Customer customer = repository.save(new Customer(null, "A", new Profile(null, "user", "password"), orderSet));
            repository.findAll().forEach(System.out::println);
            System.out.println("getting hopefully cached version(1)");
            repository.findById(customer.getId()).get();
            System.out.println("getting hopefully cached version(2)");
            Customer result = repository.findById(customer.getId()).get();
            Assert.state(customer != result, "the two references must not be the same");
        };
    }

    @Bean
    public ConcurrentMapCacheManager concurrentMapCacheManager() {
        ConcurrentMapCacheManager cache = new ConcurrentMapCacheManager();
        cache.setAllowNullValues(true);
        cache.setStoreByValue(true);
        return cache;
    }
}
