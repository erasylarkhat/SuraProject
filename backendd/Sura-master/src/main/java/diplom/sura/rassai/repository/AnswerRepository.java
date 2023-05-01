package diplom.sura.rassai.repository;

import diplom.sura.rassai.models.Answer;
import diplom.sura.rassai.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUser(User user);
}
