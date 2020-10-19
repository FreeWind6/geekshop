package ru.geekbrains.shop.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.geekbrains.shop.persistence.entities.Product;
import ru.geekbrains.shop.persistence.entities.Review;
import ru.geekbrains.shop.persistence.entities.Shopuser;
import ru.geekbrains.shop.persistence.entities.enums.ProductCategory;
import ru.geekbrains.shop.persistence.entities.enums.Role;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.geekbrains.shop.persistence.repositories.ProductRepository;
import ru.geekbrains.shop.services.feign.clients.ShopFeignClient;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

//@DataJpaTest
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ShopFeignClient shopFeignClient;

    private static Product product;
    private static Shopuser shopuser;
    private static Review review;

/*    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    private List<Product> productMocks;

    private ObjectMapper objectMapper = new ObjectMapper();

    {
        try {
            productMocks = objectMapper.readValue(new ClassPathResource("mocks/products.json").getFile(), new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        productMocks.forEach(
                product -> testEntityManager.persist(product)
        );
    }*/

    @Before
    public void setUp() {
        product = Product.builder()
                .title("Кофе")
                .available(true)
                .category(ProductCategory.DRINK)
                .price(200.00)
                .description("")
                .build();

        shopuser = Shopuser.builder()
                .email("ya.ru")
                .firstName("Dima")
                .password("123456")
                .phone("+7977887")
                .lastName("Kub")
                .role(Role.ROLE_ADMIN)
                .build();

        review = Review.builder()
                .commentary("123")
                .product(product)
                .shopuser(shopuser)
                .approved(true)
                .build();

        reviewService.save(review);
    }

/*  Optional<List<Review>> getReviewsByProduct
    Optional<List<Review>> getReviewsByShopuser
    UUID moderate для сервиса ReviewService;*/

    @Test
    public void testReviewsByProduct() {
        assertEquals(1, reviewService.getReviewsByProduct(product).size());
    }

    @Test
    public void testReviewsByShopuser() {
        assertEquals(1, reviewService.getReviewsByShopuser(shopuser).size());
    }

    @Test
    public void testModerate() {
//        assertEquals(UUID.fromString("8a50849b-4b5c-40e9-a5c1-20185f2753fd"), reviewService.moderate(UUID.fromString("8a50849b-4b5c-40e9-a5c1-20185f2753fd"),null));
        assertNull(reviewService.moderate(UUID.fromString("8a50849b-4b5c-40e9-a5c1-20185f2753fd"), null));
    }

}