package rizkigunawan.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import rizkigunawan.jpa.entity.Member;
import rizkigunawan.jpa.util.JpaUtil;

public class EntityListenerTest {
    
    @Test
    void entityListener() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Member member = entityManager.find(Member.class, 4);

        Assertions.assertEquals("Rizki Silent Gunawan, A.Md.Kom.", member.getProfessionalName());

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
