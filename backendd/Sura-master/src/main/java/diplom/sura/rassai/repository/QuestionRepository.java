package diplom.sura.rassai.repository;

import diplom.sura.rassai.models.Question;
import diplom.sura.rassai.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answers")
    List<Question> findAllWithAnswers();

    @Query("SELECT q FROM Question q WHERE q.user = :user")
    List<Question> findByUser(@Param("user") User user);
}
