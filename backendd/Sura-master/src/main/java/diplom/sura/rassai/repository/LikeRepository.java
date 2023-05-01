package diplom.sura.rassai.repository;

import diplom.sura.rassai.models.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.post = :post AND l.user = :user")
    Optional<Like> findByPostAndUser(@Param("post") Post post, @Param("user") User user);

    @Query("SELECT l FROM Like l WHERE l.answer = :answer AND l.user = :user")
    Optional<Like> findByAnswerAndUser(@Param("answer") Answer answer, @Param("user") User user);

}
