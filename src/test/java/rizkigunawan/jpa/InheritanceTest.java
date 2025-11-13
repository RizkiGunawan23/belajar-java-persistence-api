package rizkigunawan.jpa;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import rizkigunawan.jpa.entity.Brand;
import rizkigunawan.jpa.entity.Category;
import rizkigunawan.jpa.entity.Employee;
import rizkigunawan.jpa.entity.Manager;
import rizkigunawan.jpa.entity.PaymentCreditCard;
import rizkigunawan.jpa.entity.PaymentGopay;
import rizkigunawan.jpa.entity.Transaction;
import rizkigunawan.jpa.entity.TransactionCredit;
import rizkigunawan.jpa.entity.TransactionDebit;
import rizkigunawan.jpa.entity.VicePresident;
import rizkigunawan.jpa.util.JpaUtil;

public class InheritanceTest {
    
    @Test
    void singleTablePersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Employee employee = Employee.builder()
            .name("Rina Wati")
            .build();
        entityManager.persist(employee);

        Manager manager = Manager.builder()
            .name("Joko Morro")
            .totalEmployee(10)
            .build();
        entityManager.persist(manager);
        
        VicePresident vicePresident = VicePresident.builder()
            .name("Joko Morro")
            .totalManager(5)
            .build();
        entityManager.persist(vicePresident);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void singleTableFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Employee employee = entityManager.find(Employee.class, "6a0b2a8b-a77c-45c8-9799-d68598fac5bd");
        Assertions.assertNotNull(employee);
        
        Manager manager = entityManager.find(Manager.class, "4151880c-4f62-465a-bebf-e20fa80fe104");
        Assertions.assertNotNull(manager);
        
        Employee employee2 = entityManager.find(Employee.class, "128ef91e-fc54-42b8-bece-597394b32324");
        Assertions.assertNotNull((VicePresident) employee2);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinedTablePersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        PaymentGopay paymentGopay = PaymentGopay.builder()
            .amount(150_000L)
            .gopayId(UUID.randomUUID().toString())
            .build();
        entityManager.persist(paymentGopay);

        PaymentCreditCard paymentCreditCard = PaymentCreditCard.builder()
            .amount(1_000_000L)
            .maskedCard("6244*********1234")
            .bank("BCA")
            .build();
        entityManager.persist(paymentCreditCard);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinedTableFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        PaymentGopay paymentGopay = entityManager.find(PaymentGopay.class, "694e65cd-4078-4c6c-a473-593c5eee8cc9");
        Assertions.assertNotNull(paymentGopay);

        PaymentCreditCard paymentCreditCard = entityManager.find(PaymentCreditCard.class, "a88f64ae-63be-4a60-a537-13b0ef2bf71d");
        Assertions.assertNotNull(paymentCreditCard);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
    
    @Test
    void tablePerClassPersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Transaction transaction = Transaction.builder()
            .balance(5_000_000L)
            .build();
        entityManager.persist(transaction);

        TransactionCredit transactionCredit = TransactionCredit.builder()
            .balance(2_000_000L)
            .creditAmount(1_000_000L)
            .build();
        entityManager.persist(transactionCredit);

        TransactionDebit transactionDebit = TransactionDebit.builder()
            .balance(3_000_000L)
            .debitAmount(1_000_000L)
            .build();
        entityManager.persist(transactionDebit);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void mappedSuperclass() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Brand brand = Brand.builder()
            .name("Brand A")
            .description("Brand A description")
            .build();
        entityManager.persist(brand);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void mappedSuperclassWithAuditableEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        // Manager manager = Manager.builder()
        //     .name("Manager A")
        //     .totalEmployee(25)
        //     .build();
        // entityManager.persist(manager);
        Category category = Category.builder()
            .name("Category A")
            .description("Category A description")
            .build();
        entityManager.persist(category);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
