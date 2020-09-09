package app.core.service;

import app.bike_app.AbstractIntegrationTest;
import app.core.entity.Product;
import app.core.entity.ProductCategory;
import app.core.entity.repository.ProductCategoryRepository;
import app.core.entity.repository.ProductRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Karol Bąk
 */
class ProductCategoryServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testCategories() throws NotFoundException {

        //given
        ProductCategory bike = createProductCategory("Rowery", null);
        ProductCategory forMan = createProductCategory("Męskie", bike);

        ProductCategory forWoman = createProductCategory("Damskie", bike);

        createProduct("Rower męski", BigDecimal.valueOf(799), forMan);
        createProduct("Rower damski", BigDecimal.valueOf(799), forWoman);

        createProduct("Rower męski", BigDecimal.valueOf(2000), forMan);
        createProduct("Rower męski", BigDecimal.valueOf(799), forMan);

        //then
        List<Product> manBikes = productRepository.findAllByProductCategory_CategoryName("Męskie").orElseThrow(() -> new NotFoundException("not found"));
        forMan.setProducts(manBikes);
        ProductCategory productCategory = productCategoryRepository.getByCategoryName("Męskie");
        Assertions.assertEquals(3, manBikes.size());
        Assertions.assertEquals(3, productCategory.getProducts().size());
        Assertions.assertEquals(3, productCategory.getProducts().size());
    }

    private ProductCategory createProductCategory(String name, ProductCategory embedded) {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName(name);
        productCategory.setEmbedded(embedded);
        return productCategoryRepository.save(productCategory);
    }

    private Product createProduct(String name, BigDecimal price, ProductCategory category) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setProductCategory(category);
        return productRepository.save(product);
    }

}
