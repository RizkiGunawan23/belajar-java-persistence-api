package rizkigunawan.jpa;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import rizkigunawan.jpa.entity.Image;
import rizkigunawan.jpa.util.JpaUtil;

public class LargeObjectTest {
    
    @Test
    void largeObject() throws IOException, URISyntaxException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Image.ImageBuilder imageBuilder = Image.builder()
            .name("Nama 1")
            .description("Deskripsi 1");

        byte[] bytes = Files.readAllBytes(Path.of(getClass().getResource("/images/Car.jpg").toURI()));

        imageBuilder.image(bytes);
        Image image = imageBuilder.build();

        entityManager.persist(image);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }    

}
