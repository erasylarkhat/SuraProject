package diplom.sura.rassai.repository;

import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE (LOWER(p.title) LIKE LOWER(CONCAT('%', :searchValue, '%')) or LOWER(p.description) LIKE LOWER(CONCAT('%', :searchValue, '%'))) and p.category = :category")
    List<Post> findByTitleContainingIgnoreCaseAndCategory(@Param("searchValue") String searchValue, @Param("category") String category);

    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchValue, '%')) or LOWER(p.description) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Post> findByTitleContainingIgnoreCase(@Param("searchValue") String searchValue);

    @Query("SELECT p FROM Post p WHERE p.category = :category")
    List<Post> findByCategory(@Param("category") String category);

    @Query("SELECT p FROM Post p WHERE p.user = :user")
    List<Post> findByUser(@Param("user") User user);

    // @Query("SELECT p FROM Post p LEFT JOIN FETCH p.likes WHERE p.id = :id")
    // Optional<Post> findByIdWithLikes(@Param("id") Long id);

    // @Query("SELECT p FROM Post p LEFT JOIN FETCH likes")
    // List<Post> findAllWithLikes();
}
