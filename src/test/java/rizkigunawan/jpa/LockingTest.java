package rizkigunawan.jpa;

import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import rizkigunawan.jpa.entity.Brand;
import rizkigunawan.jpa.util.JpaUtil;

public class LockingTest {
    
    @Test
    void optimisticLocking() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Brand brand = Brand.builder()
            .name("Nokia")
            .description("Nokia description")
            .build();
        entityManager.persist(brand);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void optimisticLockingDemo() throws InterruptedException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Brand brand = entityManager.find(Brand.class, "6cf0d9ca-08fb-4e55-8aff-ecd5e2d16a0b");
        brand.setName("Nokia Updated 1");
        brand = entityManager.merge(brand);

        Thread.sleep(10 * 1_000L);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void optimisticLockingDemo2() throws InterruptedException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Brand brand = entityManager.find(Brand.class, "6cf0d9ca-08fb-4e55-8aff-ecd5e2d16a0b");
        brand.setName("Nokia Updated 2");
        brand = entityManager.merge(brand);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
