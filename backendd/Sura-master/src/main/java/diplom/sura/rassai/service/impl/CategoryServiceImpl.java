package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.models.Category;
import diplom.sura.rassai.repository.CategroyRepository;
import diplom.sura.rassai.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategroyRepository categroyRepository;

    @Override
    public List<Category> getAllCategories() {
        return categroyRepository.findAll();
    }

    @Override
    public Category getOneCategory(Long id) {
        return categroyRepository.findById(id).orElseThrow();
    }

    @Override
    public Category addCategory(Category category) {
        return categroyRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categroyRepository.deleteById(id);
    }
}
