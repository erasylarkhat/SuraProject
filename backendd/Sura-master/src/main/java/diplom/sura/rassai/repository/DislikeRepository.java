package diplom.sura.rassai.repository;

import diplom.sura.rassai.models.Answer;
import diplom.sura.rassai.models.Dislike;
import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DislikeRepository extends JpaRepository<Dislike, Long> {
    @Query("SELECT l FROM Dislike l WHERE l.post = :post AND l.user = :user")
    Optional<Dislike> findByPostAndUser(@Param("post") Post post, @Param("user") User user);

    @Query("SELECT l FROM Dislike l WHERE l.answer = :answer AND l.user = :user")
    Optional<Dislike> findByAnswerAndUser(@Param("answer") Answer answer, @Param("user") User user);
}
