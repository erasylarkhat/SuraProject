package diplom.sura.rassai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    @Query("SELECT i FROM PostImage i WHERE i.post = :post")
    List<PostImage> findByPost(@Param("post") Post post);
}
