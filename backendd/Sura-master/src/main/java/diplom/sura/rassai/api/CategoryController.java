package diplom.sura.rassai.api;

import diplom.sura.rassai.models.Category;
import diplom.sura.rassai.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/getAllCategory")
    public List<Category> categoryList() {
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/getOneCategory/{id}")
    public Category getOneCategory(@PathVariable(name = "id") Long id) {
        return categoryService.getOneCategory(id);
    }

    @PostMapping(value = "/addCategory")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @DeleteMapping(value = "/deleteCategory/{id}")
    public void deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
    }

}
