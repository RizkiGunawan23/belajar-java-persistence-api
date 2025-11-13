package rizkigunawan.jpa;

import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import rizkigunawan.jpa.entity.Brand;
import rizkigunawan.jpa.entity.Member;
import rizkigunawan.jpa.entity.Product;
import rizkigunawan.jpa.entity.SimpleBrand;
import rizkigunawan.jpa.entity.SimpleProduct;
import rizkigunawan.jpa.entity.User;
import rizkigunawan.jpa.util.JpaUtil;

public class JpaQueryLanguageTest {

    @Test
    void select() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        TypedQuery<Brand> query = entityManager.createQuery("SELECT b from Brand b", Brand.class);
        query.getResultList().forEach(
                (brand) -> System.out.println(brand.getId()));

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void whereClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        TypedQuery<Member> query = entityManager.createQuery(
                            """
                                SELECT 
                                    m 
                                from 
                                    Member m 
                                WHERE 
                                    m.fullName.firstName = :firstName AND
                                    m.fullName.lastName = :lastName
                            """, Member.class)
                            .setParameter("firstName", "Rizki")
                            .setParameter("lastName", "Gunawan");

        query.getResultList().forEach(
                (member) -> System.out.println(member.getProfessionalName()));

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinWhereClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        TypedQuery<Product> query = entityManager.createQuery(
                            """
                                SELECT 
                                    p 
                                from 
                                    Product p
                                JOIN
                                    p.brand b
                                WHERE
                                    p.price > 2000000
                            """, Product.class);

        query.getResultList().forEach(
                (product) -> System.out.println(product.getName()));

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinFetchClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        // fetch artinya data likes akan langsung diambil bersamaan dengan data user.
        // Sehingga tidak terjadi N+1 problem ketika mengakses data likes dari user.
        TypedQuery<User> query = entityManager.createQuery(
                            """
                                SELECT 
                                    u 
                                from 
                                    User u
                                JOIN FETCH
                                    u.likes l
                            """, User.class);

        query.getResultList().forEach(
                (user) -> {
                    System.out.println("User: " + user.getName());
                    user.getLikes().forEach((like) -> {
                        System.out.println("\tLiked product: " + like.getName());
                    });
                });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void orderByClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        TypedQuery<Brand> query = entityManager.createQuery(
                            """
                                SELECT 
                                    b 
                                from 
                                    Brand b 
                                ORDER BY b.name ASC
                            """, Brand.class);

        query.getResultList().forEach(
                (product) -> System.out.println(product.getName()));

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void insertRandomBrand() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        for (int i = 1; i <= 100; i++) {
            Brand brand = Brand.builder()
                .name("Brand " + i)
                .description("Brand description " + i)
                .build();
            entityManager.persist(brand);
        }

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void limitOffset() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        int page = 7;
        int limit = 15;
        int offset = (page - 1) * limit;

        transactionManager.begin();

        TypedQuery<Brand> query = entityManager.createQuery(
                            """
                                SELECT 
                                    b 
                                from 
                                    Brand b
                                WHERE
                                    b.id != '03acac61-071f-4966-8d47-0b6a13076786' AND
                                    b.id != '6cf0d9ca-08fb-4e55-8aff-ecd5e2d16a0b'
                            """, Brand.class)
                            .setFirstResult(offset)
                            .setMaxResults(limit);

        query.getResultList().forEach(
                (brand) -> System.out.println(brand.getName()));

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void namedQueries() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        TypedQuery<Brand> query1 = entityManager
            .createNamedQuery("Brand.findAll", Brand.class);

        query1.getResultList().forEach(
                (brand) -> System.out.println(brand.getName()));

        TypedQuery<Brand> query2 = entityManager
            .createNamedQuery("Brand.findByName", Brand.class)
            .setParameter("name", "Brand 67");

        query2.getResultList().forEach(
                (brand) -> System.out.println(brand.getName()));

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void constructorExpression() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        TypedQuery<SimpleBrand> query = entityManager
            .createQuery(
                """
                    SELECT
                        new rizkigunawan.jpa.entity.SimpleBrand(b.id, b.name)
                    FROM 
                        Brand b
                    WHERE b.name = :name
                """, SimpleBrand.class)
            .setParameter("name", "Brand 67");

        query.getResultList().forEach(
                (simpleBrand) -> {
                    System.out.printf("%s - %s", simpleBrand.getId(), simpleBrand.getName());
                });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void aggregateFunction() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        TypedQuery<SimpleProduct> query = entityManager
            .createQuery(
                """
                    SELECT
                        new rizkigunawan.jpa.entity.SimpleProduct(
                            b.id,
                            count(*),
                            avg(p.price), 
                            min(p.price), 
                            max(p.price), 
                            sum(p.price)
                        )   
                    FROM 
                        Product p
                    JOIN
                        p.brand b
                    GROUP BY b.id
                    HAVING avg(p.price) > 3500000
                """, SimpleProduct.class);

        query.getResultList().forEach(
                (simpleProduct) -> {
                    System.out.printf(
                        "%s - %d - %f - %d - %d - %d%n", 
                        simpleProduct.getId(), 
                        simpleProduct.getCount(), 
                        simpleProduct.getAverage(), 
                        simpleProduct.getMin(),
                        simpleProduct.getMax(),
                        simpleProduct.getSum()
                    );
                });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void nativeQuery() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Query query = entityManager.createNativeQuery(
                """
                    SELECT * FROM brands
                    WHERE brands.created_at is not null
                """,
                Brand.class
        );

        @SuppressWarnings("unchecked")
        List<Brand> brands = (List<Brand>) query.getResultList();

        brands.forEach((brand) -> {
            System.out.printf("%s - %s - %s%n",
                brand.getId(), brand.getName(), brand.getDescription()
            );
        });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void namedNativeQuery() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Query query = entityManager.createNamedQuery("Brand.native.findAll",Brand.class);

        @SuppressWarnings("unchecked")
        List<Brand> brands = (List<Brand>) query.getResultList();

        brands.forEach((brand) -> {
            System.out.printf("%s - %s - %s%n",
                brand.getId(), brand.getName(), brand.getDescription()
            );
        });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void nonQuery() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        Query query = entityManager.createQuery("UPDATE Brand b SET b.name = :name WHERE b.id = :id");
        query.setParameter("name", "Brand A Updated");
        query.setParameter("id", "03acac61-071f-4966-8d47-0b6a13076786");
        int impatedRecords = query.executeUpdate();
        System.out.printf("Impacted records: %d%n", impatedRecords);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
