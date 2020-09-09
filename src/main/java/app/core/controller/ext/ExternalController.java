package app.core.controller.ext;

import app.core.entity.ProductCategory;
import app.core.entity.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @author Karol Bąk
 */
@RestController
@RequestMapping("/ext")
@RequiredArgsConstructor
public class ExternalController {


    private final ProductCategoryRepository productCategoryRepository;

    @GetMapping("/categories")
    public List<ProductCategory> getProductCategories() {
        return (List<ProductCategory>) productCategoryRepository.findAll();
    }
}
