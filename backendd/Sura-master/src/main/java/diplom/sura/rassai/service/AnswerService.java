package diplom.sura.rassai.service;

import diplom.sura.rassai.models.Answer;
import diplom.sura.rassai.models.Question;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AnswerService {

    List<Answer> getAllAnswer();
    Answer getOneAnswer(Long id);
    Answer addAnswer(Answer answer, Question question, HttpServletRequest request);
    void deleteAnswer(Long id);

    Answer likeAnswer(Long id, HttpServletRequest request);
    Answer dislikeAnswer(Long id, HttpServletRequest request);


}
