package rizkigunawan.jpa;

import java.math.BigInteger;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import rizkigunawan.jpa.entity.Brand;
import rizkigunawan.jpa.entity.Credential;
import rizkigunawan.jpa.entity.Product;
import rizkigunawan.jpa.entity.User;
import rizkigunawan.jpa.entity.Wallet;
import rizkigunawan.jpa.util.JpaUtil;

public class EntityRelationshipTest {
    
    @Test
    void oneToOnePersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        String uuid = UUID.randomUUID().toString();

        Credential credential = Credential.builder()
            .id(uuid)
            .email("user1@mail.com")
            .password("password123")
            .build();
        entityManager.persist(credential);
        
        User user = User.builder()
            .id(uuid)
            .name("User 1")
            .build();
        entityManager.persist(user);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToOneFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        String uuid = "a1c5e5fb-bd90-411c-95b2-a746998032f2";

        User user = entityManager.find(User.class, uuid);
        Credential credential = user.getCredential();
        Wallet wallet = user.getWallet();

        Assertions.assertEquals(uuid, user.getId());
        Assertions.assertEquals("User 1", user.getName());
        Assertions.assertEquals(uuid, credential.getId());
        Assertions.assertEquals("user1@mail.com", credential.getEmail());
        Assertions.assertEquals("password123", credential.getPassword());
        Assertions.assertNotNull(wallet.getId());
        Assertions.assertEquals(BigInteger.valueOf(3_500_000), wallet.getBalance());

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToOneJoinColumn() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        String uuid = "a1c5e5fb-bd90-411c-95b2-a746998032f2";

        User user = entityManager.find(User.class, uuid);
        
        Wallet wallet = Wallet.builder()
            .balance(BigInteger.valueOf(3_500_000))
            .user(user)
            .build();

        entityManager.persist(wallet);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToManyPersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Brand brand = Brand.builder()
            .name("Samsung")
            .description("Samsung description")
            .build();
        
        entityManager.persist(brand);

        Product product1 = Product.builder()
            .name("Samsung Galaxy 1")
            .price(4_000_000L)
            .description("Samsung Galaxy 1 description")
            .brand(brand)
            .build();
        
        entityManager.persist(product1);
        
        Product product2 = Product.builder()
            .name("Samsung Galaxy 2")
            .price(4_500_000L)
            .description("Samsung Galaxy 2 description")
            .brand(brand)
            .build();
        
        entityManager.persist(product2);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToManyFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Brand brand = entityManager.find(Brand.class, "d7538494-b5b7-4b57-957e-31d969a5c48e");
        System.out.println("After brand found");
        Assertions.assertEquals(2, brand.getProducts().size());
        System.out.println("Before products accessed");

        brand.getProducts().forEach(product -> 
            System.out.println(product.getName())
        );

        System.out.println("After products accessed");

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
    
    @Test
    void manyToManyPersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        String userId = "a1c5e5fb-bd90-411c-95b2-a746998032f2";
        User user = entityManager.find(User.class, userId);

        Assertions.assertNotNull(user);

        String product1Id = "c6938d0d-4ed9-4cf3-8928-74c0f54ceb84";

        Product product1 = entityManager.find(Product.class, product1Id);

        Assertions.assertNotNull(product1);

        String product2Id = "96135545-1949-4faf-a155-d50ca56c3152";

        Product product2 = entityManager.find(Product.class, product2Id);

        Assertions.assertNotNull(product2);

        user.getLikes().add(product1);
        user.getLikes().add(product2);

        entityManager.merge(user);

        Assertions.assertNotNull(user.getLikes());

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
