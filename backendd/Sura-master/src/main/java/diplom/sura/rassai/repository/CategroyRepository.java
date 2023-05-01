package diplom.sura.rassai.repository;

import diplom.sura.rassai.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategroyRepository extends JpaRepository<Category, Long> {
}
