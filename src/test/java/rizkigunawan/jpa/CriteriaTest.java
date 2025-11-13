package rizkigunawan.jpa;

import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import rizkigunawan.jpa.entity.Brand;
import rizkigunawan.jpa.entity.Product;
import rizkigunawan.jpa.entity.ProductBrandAggregate;
import rizkigunawan.jpa.entity.SimpleBrand;
import rizkigunawan.jpa.util.JpaUtil;

public class CriteriaTest {
    
    @Test
    void criteriaQuery() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Brand> brandCriteria = builder.createQuery(Brand.class);
        Root<Brand> brandRoot = brandCriteria.from(Brand.class); // select b from Brand b
        brandCriteria.select(brandRoot);

        TypedQuery<Brand> brandQuery = entityManager.createQuery(brandCriteria); 
        brandQuery.getResultList().forEach((brand) -> {
            System.out.printf("%s - %s%n", brand.getId(), brand.getName());
        });       

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaQueryNonEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // cq ini akan dijadikan sebagai hasil query.
        // di sini memakai class SimpleBrand sebagai hasilnya nanti.
        CriteriaQuery<SimpleBrand> cq = cb.createQuery(SimpleBrand.class);
        // root berarti akan mengambil data dari entity mana.
        // data akan diambil dari entity Brand.
        Root<Brand> root = cq.from(Brand.class);
        // select akan mengambil data dari root.
        // di sini memakai tambahan construct, karena hasilnya tidak langsung berupa root,
        // tapi class lain yang mengambil beberapa field dari root.
        cq.select(cb.construct(
            SimpleBrand.class, 
            root.get("id"), 
            root.get("name")
        ));

        TypedQuery<SimpleBrand> query = entityManager.createQuery(cq);
        List<SimpleBrand> simpleBrands = query.getResultList();
        simpleBrands.forEach((simpleBrand) -> {
            System.out.printf("ID: %s, name: %s%n", simpleBrand.getId(), simpleBrand.getName());
        });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaWhereClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Brand> cq = cb.createQuery(Brand.class);
        Root<Brand> root = cq.from(Brand.class);

        cq.select(root);

        // secara default menggunakan AND
        cq.where(
            cb.equal(root.get("name"), "Brand 1"),
            cb.isNotNull(root.get("version"))
        );

        // cq.where(
        //     cb.or(
        //         cb.equal(root.get("name"), "Brand 1"),
        //         cb.isNotNull(root.get("version"))
        //     )
        // );

        TypedQuery<Brand> query = entityManager.createQuery(cq);
        Brand brand = query.getSingleResult();
        System.out.printf("ID: %s, name: %s%n", brand.getId(), brand.getName());

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaJoinClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        Join<Product, Brand> brandRoot = productRoot.join("brand");

        cq.select(productRoot);
        cq.where(cb.equal(brandRoot.get("name"), "Brand 2"));

        TypedQuery<Product> query = entityManager.createQuery(cq);
        List<Product> products = query.getResultList();

        products.forEach((product) -> {
            System.out.printf(
                "Product ID: %s, Product Name: %s, Brand ID: %s, Brand Name: %s%n", 
                product.getId(), 
                product.getName(), 
                product.getBrand().getId(), 
                product.getBrand().getName()
            );
        });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaJoinWhereParameterClause() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);
        Join<Product, Brand> brandRoot = productRoot.join("brand");

        ParameterExpression<String> nameParameter = cb.parameter(String.class);
        
        cq.select(productRoot);
        cq.where(cb.equal(brandRoot.get("name"), nameParameter));

        TypedQuery<Product> query = entityManager.createQuery(cq);
        query.setParameter(nameParameter, "Brand 2");
        List<Product> products = query.getResultList();

        products.forEach((product) -> {
            System.out.printf(
                "Product ID: %s, Product Name: %s, Brand ID: %s, Brand Name: %s%n", 
                product.getId(), 
                product.getName(), 
                product.getBrand().getId(), 
                product.getBrand().getName()
            );
        });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaAggregateQuery() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<ProductBrandAggregate> cq = cb.createQuery(ProductBrandAggregate.class);
        Root<Product> productRoot = cq.from(Product.class);
        Join<Product, Brand> brandRoot = productRoot.join("brand");

        cq.select(
            cb.construct(
                ProductBrandAggregate.class,
                brandRoot.get("id"),
                cb.min(productRoot.get("price")),
                cb.max(productRoot.get("price")),
                cb.avg(productRoot.get("price"))
            )
        );

        cq.groupBy(brandRoot.get("id"));
        cq.having(
            cb.greaterThan(cb.min(productRoot.get("price")), 3_000_000L)
        );

        TypedQuery<ProductBrandAggregate> query = entityManager.createQuery(cq);
        List<ProductBrandAggregate> productBrandAggregates = query.getResultList();

        productBrandAggregates.forEach((productBrandAggregate) -> {
            System.out.printf(
                "Brand ID: %s, Min: %d, Max: %d, Average: %f%n", 
                productBrandAggregate.getId(), 
                productBrandAggregate.getMin(), 
                productBrandAggregate.getMax(), 
                productBrandAggregate.getAverage()
            );
        });

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaUpdate() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaUpdate<Brand> cu = cb.createCriteriaUpdate(Brand.class);
        Root<Brand> root = cu.from(Brand.class);

        ParameterExpression<String> idParameter = cb.parameter(String.class);
        ParameterExpression<String> nameParameter = cb.parameter(String.class);

        cu.set(root.<String>get("name"), nameParameter);
        cu.where(
            cb.equal(root.get("id"), idParameter)
        );

        Query query = entityManager.createQuery(cu);
        query.setParameter(nameParameter, "Nokia Criteria Update");
        query.setParameter(idParameter, "6cf0d9ca-08fb-4e55-8aff-ecd5e2d16a0b");

        int impactedRecords = query.executeUpdate();
        System.out.printf("Impacted records: %d%n", impactedRecords);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaDelete() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transactionManager = entityManager.getTransaction();

        transactionManager.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaDelete<Brand> cd = cb.createCriteriaDelete(Brand.class);
        Root<Brand> root = cd.from(Brand.class);

        ParameterExpression<String> idParameter = cb.parameter(String.class);
        
        cd.where(
            cb.equal(root.get("id"), idParameter)
        );
        
        Query query = entityManager.createQuery(cd);
        query.setParameter(idParameter, "03acac61-071f-4966-8d47-0b6a13076786");

        int impactedRecords = query.executeUpdate();
        System.out.printf("Impacted records: %d%n", impactedRecords);

        transactionManager.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
