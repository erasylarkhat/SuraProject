package diplom.sura.rassai.service;

import diplom.sura.rassai.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Category getOneCategory(Long id);
    Category addCategory(Category category);
    void deleteCategory(Long id);

}
